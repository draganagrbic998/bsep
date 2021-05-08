import { Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { Table } from 'primeng/table';
import { CertificateService } from '../../../core/services/certificate.service';
import { LazyLoadEvent, MenuItem } from 'primeng/api';
import { CertificateInfo } from '../../../core/model/certificate-info';
import { Template } from '../../../core/model/template';
import { Page } from 'src/app/core/model/page';
import { extensionTemplates } from 'src/app/core/utils/templates';
import { ROOT_ALIAS } from 'src/app/core/utils/constants';

@Component({
  selector: 'app-table-view',
  templateUrl: './table-view.component.html',
  styleUrls: ['./table-view.component.scss']
})
export class TableViewComponent {

  @ViewChild('table') table: Table;
  @Output() openCertificate: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output() revokeCertificate: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output() downloadCrt: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output() downloadKey: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output() downloadJks: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output() switchCA: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();

  certificates: CertificateInfo[] = [];
  loading = true;
  rows = 10;
  totalRecords = 0;

  constructor(private certificateService: CertificateService) { }

  get caAlias(): string{
    return this.certificateService.ca.alias;
  }

  getCertificates(event: LazyLoadEvent): void {
    this.loading = true;
    const page = Math.floor(event.first / this.rows);
    const size = this.rows;
    // tslint:disable-next-line: deprecation
    this.certificateService.findAll(page, size).subscribe((response: Page<CertificateInfo>) => {
      this.loading = false;
      this.certificates = response.content;
      this.totalRecords = response.totalElements;
    });
  }

  reset(): void {
    this.table.reset();
  }

  getMenuItems(certificate: CertificateInfo): MenuItem[] {
    const items = [
      {icon: 'pi pi-info', label: 'Details', command: () => this.openCertificate.emit(certificate)},
      {icon: 'pi pi-download', label: '.crt', command: () => this.downloadCrt.emit(certificate)},
      {icon: 'pi pi-download', label: '.key', command: () => this.downloadKey.emit(certificate)},
      {icon: 'pi pi-download', label: '.jks', command: () => this.downloadJks.emit(certificate)},
    ];

    if (certificate.alias !== this.caAlias && certificate.alias !== ROOT_ALIAS && !certificate.revoked) {
      items.push({icon: 'pi pi-trash', label: 'Revoke', command: () => this.revokeCertificate.emit(certificate)});
    }
    if (certificate.alias !== this.caAlias && certificate.template === 'SUB_CA' && !certificate.revoked) {
      items.push({icon: 'pi pi-replay', label: 'Use CA', command: () => this.switchCA.emit(certificate)});
    }
    return items;
  }

  getTemplate(value: string): Template {
    return Object.values(extensionTemplates).find((template: Template) => template.enumValue === value);
  }

}
