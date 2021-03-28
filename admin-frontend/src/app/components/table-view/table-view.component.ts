import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Table} from 'primeng/table';
import {CertificateService} from '../../core/services/certificate.service';
import {LazyLoadEvent, MenuItem} from 'primeng/api';
import {CertificateInfo} from '../../core/model/certificate-info';

@Component({
  selector: 'app-table-view',
  templateUrl: './table-view.component.html',
  styleUrls: ['./table-view.component.scss']
})
export class TableViewComponent implements OnInit {

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
  openDetails: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();

  @Output()
  switchCA: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();

  constructor(private certificateService: CertificateService) { }

  ngOnInit(): void {
  }


  getCertificates(event: LazyLoadEvent): void {
    this.loading = true;
    const page = Math.floor(event.first / this.rows);
    const size = this.rows;
    this.certificateService.getCertificates(page, size).subscribe(val => {
      this.certificateService.certificates = val.content;
      this.totalRecords = val.totalElements;
      this.loading = false;
    });
  }

  getMenuItems(certificate: CertificateInfo): MenuItem[] {
    const items = [
      {icon: 'pi pi-info', label: 'Details', command: () => this.openDetails.emit(certificate)},
      {icon: 'pi pi-trash', label: 'Revoke', command: () => this.revokeCertificate.emit(certificate)}
    ];
    if (!certificate.revoked && certificate.template === 'SUB_CA' && certificate.alias !== this.caAlias) {
      items.push({icon: 'pi pi-replay', label: 'Use CA', command: () => this.switchCA.emit(certificate)});
    }
    if (certificate.revoked) {
      items.splice(1, 1);
    }
    return items;
  }

  getTemplate(value: string): any {
    return this.templates.find(t => t.value === value);
  }

  reset(): any {
    this.table.reset();
  }

  get certificates(): CertificateInfo[] {
    return this.certificateService.certificates;
  }

}
