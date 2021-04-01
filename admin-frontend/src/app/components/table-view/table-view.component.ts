import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { Table } from 'primeng/table';
import { CertificateService } from '../../core/services/certificate.service';
import { LazyLoadEvent, MenuItem } from 'primeng/api';
import { CertificateInfo } from '../../core/model/certificate-info';

@Component({
  selector: 'app-table-view',
  templateUrl: './table-view.component.html',
  styleUrls: ['./table-view.component.scss']
})
export class TableViewComponent {

  rows = 10;
  totalRecords = 0;
  first = 0;
  loading = true;

  @ViewChild('table')
  table: Table;

  @Input()
  caAlias!: string;
  @Input()
  templates!: any[];

  @Output()
  revokeCertificate: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output()
  downloadCrt: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output()
  downloadKey: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output()
  openDetails: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output()
  switchCA: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();

  constructor(
    private certificateService: CertificateService
  ) { }

  getCertificates(event: LazyLoadEvent): void {
    this.loading = true;
    const page = Math.floor(event.first / this.rows);
    const size = this.rows;
    // tslint:disable-next-line: deprecation
    this.certificateService.getCertificates(page, size).subscribe(val => {
      this.certificateService.certificates = val.content;
      this.totalRecords = val.totalElements;
      this.loading = false;
    });
  }

  getMenuItems(certificate: CertificateInfo): MenuItem[] {
    const items = [
      {icon: 'pi pi-info', label: 'Details', command: () => this.openDetails.emit(certificate)},
      {icon: 'pi pi-download', label: '.crt', command: () => this.downloadCrt.emit(certificate)},
      {icon: 'pi pi-download', label: '.key', command: () => this.downloadKey.emit(certificate)},
      {icon: 'pi pi-trash', label: 'Revoke', command: () => this.revokeCertificate.emit(certificate)}
    ];
    if ((certificate.template === 'SUB_CA' && certificate.alias === this.caAlias) || certificate.revoked) {
      items.splice(3, 1);
    }
    if (!certificate.revoked && certificate.template === 'SUB_CA' && certificate.alias !== this.caAlias) {
      items.push({icon: 'pi pi-replay', label: 'Use CA', command: () => this.switchCA.emit(certificate)});
    }
    return items;
  }

  getTemplate(value: string): any {
    return this.templates.find(t => t.value === value);
  }

  reset(): void {
    this.table.reset();
  }

  get certificates(): CertificateInfo[] {
    return this.certificateService.certificates;
  }

}
