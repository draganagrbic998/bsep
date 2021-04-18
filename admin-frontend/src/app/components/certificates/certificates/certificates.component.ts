import { Component, OnInit, ViewChild } from '@angular/core';
import { CertificateService } from '../../../core/services/certificate.service';
import { CertificateInfo } from '../../../core/model/certificate-info';
import { MenuItem, MessageService } from 'primeng/api';
import { TableViewComponent } from '../table-view/table-view.component';
import { TreeViewComponent } from '../tree-view/tree-view.component';
import { RequestViewComponent } from '../request-view/request-view.component';
import { Revoke } from 'src/app/core/model/revoke';
import { Router } from '@angular/router';
import { extensionTemplates } from '../../../core/utils/templates';
import { KeyUsageType } from '../../../core/model/key-usage-type';
import { getKeyUsages } from '../../../core/utils/key-usages';
import { KeyPurposeId } from '../../../core/model/key-purpose-id';
import { getExtendedKeyUsages } from '../../../core/utils/key-purpose-ids';
import { Template } from '../../../core/model/template';
import { environment } from 'src/environments/environment';
import { ROOT_ALIAS } from 'src/app/core/utils/constants';
import { CertificateRequest } from 'src/app/core/model/certificate-request';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.scss']
})
export class CertificatesComponent implements OnInit {

  @ViewChild('table') table: TableViewComponent;
  @ViewChild('tree') tree: TreeViewComponent;
  @ViewChild('requestsTable') requestsTable: RequestViewComponent;

  keyUsages: KeyUsageType[] = [];
  extendedKeyUsages: KeyPurposeId[] = [];
  tabItems: MenuItem[] = [
    {label: 'Table', icon: 'pi pi-table', command: () => this.activeIndex = 0},
    {label: 'Tree', icon: 'pi pi-sitemap', command: () => this.activeIndex = 1},
    {label: 'Requests Table', icon: 'pi pi-table', command: () => this.activeIndex = 2},
  ];

  certificate: CertificateInfo;
  revoke: Revoke;
  detailsDialog = false;
  revokeDialog = false;
  submitted = false;
  activeIndex = 0;

  constructor(
    private certificateService: CertificateService,
    private messageService: MessageService,
    private router: Router
  ) { }

  get caAlias(): string {
    return this.certificateService.ca?.alias;
  }

  openForm(certificate?: CertificateInfo): void {
    this.certificateService.certificate = certificate;
    this.router.navigate([environment.addCertificateRoute]);
  }

  openCertificate(certificate?: CertificateInfo): void {
    this.certificate = certificate || this.certificateService.ca;
    this.keyUsages = getKeyUsages(this.certificate.extensions.keyUsage);
    this.extendedKeyUsages = getExtendedKeyUsages(this.certificate.extensions.keyPurposeIds);
    this.detailsDialog = true;
  }

  openRequest(request: CertificateRequest): void{
    this.openForm(request as CertificateInfo);
  }

  openRevoke(certificate: CertificateInfo): void {
    this.certificate = certificate;
    this.revoke = {id: certificate.id, reason: ''};
    this.revokeDialog = true;
  }

  revokeCertificate(): void {
    if (this.revoke.reason.trim()) {
      // tslint:disable-next-line: deprecation
      this.certificateService.revoke(this.revoke).subscribe((response: CertificateInfo) => {
        if (this.table) {
          this.table.reset();
        }
        if (this.tree) {
          this.tree.reset();
        }
        if (response){
          this.certificate.revoked = true;
          this.revokeDialog = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: `${this.certificate.alias} revoked.`
          });
        }
        else{
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Error occured while revoking!.'
          });
        }
      });
    }
  }

  switchCA(certificate?: CertificateInfo): void {
    if (!certificate){
      this.getRootCA();
    }
    else{
      this.certificateService.ca = certificate;
    }
    this.messageService.add({
      severity: 'success',
      summary: 'CA changed',
      detail: `Certificate ${certificate?.alias || ROOT_ALIAS} is currenct CA.`
    });
  }

  downloadCrt(certificate: CertificateInfo): void {
    this.certificateService.downloadCrt(certificate.alias)
    // tslint:disable-next-line: deprecation
    .subscribe((response: Blob) => this.download(response, 'certificate.crt'));
  }

  downloadKey(certificate: CertificateInfo): void {
    this.certificateService.downloadKey(certificate.alias)
    // tslint:disable-next-line: deprecation
    .subscribe((response: Blob) => this.download(response, 'certificate.key'));
  }

  getTemplate(value: string): Template {
    return Object.values(extensionTemplates).find((template: Template) => template.enumValue === value);
  }

  private download(file: Blob, fileName: string): void {
    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
      navigator.msSaveOrOpenBlob(file);
      return;
    }

    const data = URL.createObjectURL(file);
    const link = document.createElement('a');
    link.href = data;
    link.download = fileName;
    link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

    setTimeout(() => {
      URL.revokeObjectURL(data);
      link.remove();
    }, 100);
  }

  private getRootCA(): void{
    this.certificateService.findByAlias(ROOT_ALIAS)
    // tslint:disable-next-line: deprecation
    .subscribe((ca: CertificateInfo) => this.certificateService.ca = ca);
  }

  ngOnInit(): void {
    this.getRootCA();
  }

}
