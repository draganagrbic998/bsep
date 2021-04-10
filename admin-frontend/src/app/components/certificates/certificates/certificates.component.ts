import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CertificateService } from '../../../core/services/certificate.service';
import { CertificateInfo } from '../../../core/model/certificate-info';
import { MenuItem, MessageService } from 'primeng/api';
import { BehaviorSubject } from 'rxjs';
import { TableViewComponent } from '../table-view/table-view.component';
import { TreeViewComponent } from '../tree-view/tree-view.component';
import { CertificateRequest } from '../../../core/model/certificate-request';
import { RequestViewComponent } from '../request-view/request-view.component';
import { Revoke } from 'src/app/core/model/revoke';
import {Route, Router} from '@angular/router';
import {CUSTOM, extensionTemplates} from '../../../core/utils/templates';
import {KeyUsageType} from '../../../core/model/key-usage-type';
import {getKeyUsages} from '../../../core/utils/key-usages';
import {KeyPurposeId} from '../../../core/model/key-purpose-id';
import {getExtendedKeyUsages} from '../../../core/utils/key-purpose-ids';
import {Template} from '../../../core/model/template';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.scss']
})
export class CertificatesComponent implements OnInit {

  certificate: CertificateInfo = new CertificateInfo();
  keyUsages: KeyUsageType[] = [];
  extendedKeyUsages: KeyPurposeId[] = [];
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
    private router: Router
  ) { }

  ngOnInit(): void {

    this.templates = Object.values(extensionTemplates);

    this.getCA();
  }

  getCA(): void {
    // tslint:disable-next-line: deprecation
    this.certificateService.findByAlias(this.caAlias.getValue()).subscribe(val => {
      this.certificateService.ca.next(val);
    });
  }

  openNew(certificate?: CertificateInfo): void {
    this.certificateService.requestCertificate.next(certificate);
    this.router.navigate(['add-certificate']);
  }

  openDetails(cert: CertificateInfo): void {
    this.certificate = cert;
    this.keyUsages = getKeyUsages(this.certificate.extensions.keyUsage);
    this.extendedKeyUsages = getExtendedKeyUsages(this.certificate.extensions.keyPurposeIds);
    this.detailsDialog = true;
  }

  hideDetails(): void {
    this.detailsDialog = false;
    this.certificate = new CertificateInfo();
    this.keyUsages = [];
    this.extendedKeyUsages = [];
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

    this.openNew(this.certificate);
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
      this.certificateService.revoke(this.revoke).subscribe(() => {
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
    }, () => {
        this.messageService.add({severity: 'error', summary: 'Failure', detail: `Error occured while revoking ${this.certificate.commonName}.`});
      });
    }
  }

  getTemplate(value: string): any {
    if (!value) {
      return CUSTOM;
    }
    return this.templates.find((t: Template) => t.enumValue === value);
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
    this.certificateService.findByAlias(this.caAlias.getValue()).subscribe(val => {
      this.certificateService.ca.next(val);
      this.activeIndex = 0;
      this.loadingTab = false;
    });
  }

  openTree(): void {
    this.loadingTab = true;
    this.caAlias.next('root');
    // tslint:disable-next-line: deprecation
    this.certificateService.findByAlias(this.caAlias.getValue()).subscribe(val => {
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
