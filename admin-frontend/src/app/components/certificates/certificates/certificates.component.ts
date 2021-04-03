import { Component, OnInit, ViewChild } from '@angular/core';
import { CertificateService } from '../../../core/services/certificate.service';
import { CertificateInfo } from '../../../core/model/certificate-info';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { BehaviorSubject } from 'rxjs';
import { TableViewComponent } from '../table-view/table-view.component';
import { TreeViewComponent } from '../tree-view/tree-view.component';
import { CertificateRequest } from '../../../core/model/certificate-request';
import { RequestViewComponent } from '../request-view/request-view.component';
import { Revoke } from 'src/app/core/model/revoke';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.scss']
})
export class CertificatesComponent implements OnInit {

  certificate: CertificateInfo = new CertificateInfo();
  revoke: Revoke = new Revoke();
  submitted = false;
  newDialog = false;
  detailsDialog = false;
  revokeDialog = false;
  templates: any[];
  caAlias: BehaviorSubject<string> = new BehaviorSubject('root');

  loadingTab = false;

  tabItems: MenuItem[] = [
    {label: 'Table', icon: 'pi pi-table', command: () => this.openTable()},
    {label: 'Tree', icon: 'pi pi-sitemap', command: () => this.openTree()},
    {label: 'Requests Table', icon: 'pi pi-table', command: () => this.openRequestsTable()},
  ];
  activeIndex = 0;

  @ViewChild('table')
  table: TableViewComponent;
  @ViewChild('tree')
  tree: TreeViewComponent;
  @ViewChild('requestsTable')
  requestsTable: RequestViewComponent;

  constructor(
    private certificateService: CertificateService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) { }

  ngOnInit(): void {

    this.templates = [
      {label: 'CA', value: 'SUB_CA', icon: 'pi pi-globe'},
      {label: 'TLS Server', value: 'TLS', icon: 'pi pi-cloud'},
      {label: 'User', value: 'USER', icon: 'pi pi-user'}
    ];

    this.getCA();
  }

  getCA(): void {
    // tslint:disable-next-line: deprecation
    this.certificateService.getByAlias(this.caAlias.getValue()).subscribe(val => {
      this.certificateService.ca.next(val);
    });
  }

  openNew(): void {
    this.certificate = new CertificateInfo();
    this.submitted = false;
    this.newDialog = true;
  }

  openDetails(cert: CertificateInfo): void {
    this.certificate = cert;
    this.detailsDialog = true;
  }

  hideNew(): void {
    this.newDialog = false;
    this.submitted = false;
  }

  hideDetails(): void {
    this.detailsDialog = false;
    this.certificate = new CertificateInfo();
  }

  openRevoke(cert: CertificateInfo): void {
    this.certificate = cert;
    this.revoke.id = cert.id;
    this.revokeDialog = true;
  }

  hideRevoke(): void {
    this.revokeDialog = false;
    this.revoke = new Revoke();
  }

  openRequest(cert: CertificateRequest): void {
    this.certificate = new CertificateInfo();
    this.certificate.id = cert.id;
    this.certificate.alias = cert.alias;
    this.certificate.commonName = cert.commonName;
    this.certificate.country = cert.country;
    this.certificate.email = cert.email;
    this.certificate.template = cert.template;
    this.certificate.organization = cert.organization;
    this.certificate.organizationUnit = cert.organizationUnit;
    this.certificate.path = cert.path;

    this.submitted = false;
    this.newDialog = true;
  }

  downloadCrt(certificate: CertificateInfo): void {
    // tslint:disable-next-line: deprecation
    this.certificateService.downloadCrt(certificate.alias).subscribe(val => {
      this.download(val, 'certificate.crt');
    });
  }

  downloadKey(certificate: CertificateInfo): void {
    // tslint:disable-next-line: deprecation
    this.certificateService.downloadKey(certificate.alias).subscribe(val => {
      this.download(val, 'certificate.key');
    });
  }


  download(val: Blob, fileName: string): void {
    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
      navigator.msSaveOrOpenBlob(val);
      return;
    }

    // For other browsers:
    // Create a link pointing to the ObjectURL containing the blob.
    const data = URL.createObjectURL(val);

    const link = document.createElement('a');
    link.href = data;
    link.download = fileName;
    // this is necessary as link.click() does not work on the latest firefox
    link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

    setTimeout(() => {
      // For Firefox it is necessary to delay revoking the ObjectURL
      URL.revokeObjectURL(data);
      link.remove();
    }, 100);
  }

  revokeCertificate(): void {
    this.submitted = true;

    if (this.revoke.reason.trim()) {
      // tslint:disable-next-line: deprecation
      this.certificateService.revokeCertificate(this.revoke).subscribe(val => {
        if (val) {
          this.certificate.revoked = true;
          if (!!this.table) {
            this.table.reset();
          }
          if (!!this.tree) {
            this.tree.reset();
          }
          this.revokeDialog = false;
          this.messageService.add({
            severity: 'success', summary: 'Success', detail: `${this.certificate.commonName} successfully revoked.`});
        }
        else {
          this.messageService.add({severity: 'error', summary: 'Failure', detail: `Error occured while revoking ${this.certificate.commonName}.`});
        }
      });
    }
  }

  saveCertificate(): void {
    this.submitted = true;

    if (this.certificate.commonName.trim()) {
      this.certificate.issuerAlias = this.caAlias.getValue();
      // tslint:disable-next-line: deprecation
      this.certificateService.createCertificate(this.certificate).subscribe((response: CertificateInfo) => {
        if (!response){
          this.messageService.add({severity: 'error',
          summary: 'Failure', detail: `Error occured while creating ${this.certificate.alias}.`});
          return;
        }
        if (!!this.table) {
          this.table.reset();
        }
        if (!!this.tree) {
          this.tree.reset();
        }
        if (!!this.requestsTable) {
          this.requestsTable.reset();
        }
        this.newDialog = false;
        this.messageService.add({severity: 'success', summary: 'Success', detail: `${this.certificate.commonName} successfully created.`});
        this.certificate = new CertificateInfo();

      });
    }
  }

  getTemplate(value: string): any {
    return this.templates.find(t => t.value === value);
  }

  switchCA(cert: CertificateInfo): void {
    this.certificateService.ca.next(cert);
    this.caAlias.next(cert.alias);
    this.messageService.add({severity: 'success', summary: 'New CA set', detail: 'The CA has been changed successfully.'});
  }

  resetCA(): void {
    this.caAlias.next('root');
    this.getCA();
    this.messageService.add({severity: 'success', summary: 'CA set back to root', detail: 'The CA has been changed back to root.'});
  }

  openTable(): void {
    this.loadingTab = true;
    this.caAlias.next('root');
    // tslint:disable-next-line: deprecation
    this.certificateService.getByAlias(this.caAlias.getValue()).subscribe(val => {
      this.certificateService.ca.next(val);
      this.activeIndex = 0;
      this.loadingTab = false;
    });
  }

  openTree(): void {
    this.loadingTab = true;
    this.caAlias.next('root');
    // tslint:disable-next-line: deprecation
    this.certificateService.getByAlias(this.caAlias.getValue()).subscribe(val => {
      this.certificateService.ca.next(val);
      this.activeIndex = 1;
      this.loadingTab = false;
    });
  }

  openRequestsTable(): void {
    this.activeIndex = 2;
  }

  get certificates(): CertificateInfo[] {
    return this.certificateService.certificates;
  }

  get ca(): CertificateInfo {
    return this.certificateService.ca.getValue();
  }

}
