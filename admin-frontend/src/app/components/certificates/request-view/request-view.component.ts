import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import {Table} from 'primeng/table';
import {CertificateService} from '../../../core/services/certificate.service';
import {LazyLoadEvent, MenuItem} from 'primeng/api';
import {CertificateRequest} from 'src/app/core/model/certificate-request';

@Component({
  selector: 'app-request-view',
  templateUrl: './request-view.component.html',
  styleUrls: ['./request-view.component.scss']
})
export class RequestViewComponent {

  rows = 10;
  totalRecords = 0;
  first = 0;
  loading = true;
  @ViewChild('requestsTable')
  requestsTable: Table;

  @Input()
  templates!: any[];

  @Output()
  openRequest: EventEmitter<CertificateRequest> = new EventEmitter<CertificateRequest>();

  constructor(
    private certificateService: CertificateService
  ) { }

  getCertificateRequests(event: LazyLoadEvent): void {
    this.loading = true;
    const page = Math.floor(event.first / this.rows);
    const size = this.rows;
    // tslint:disable-next-line: deprecation
    this.certificateService.getCertificateRequests(page, size).subscribe(val => {
      this.certificateService.certificateRequests = val.content;
      this.totalRecords = val.totalElements;
      this.loading = false;
    });
  }

  // Neka ga ovako mozda budemo dodavali jos opcija
  getMenuItems(certificateRequest: CertificateRequest): MenuItem[] {
    const items = [
      {icon: 'pi pi-info', label: 'Details', command: () => this.openRequest.emit(certificateRequest)},
    ];
    return items;
  }

  getTemplate(value: string): any {
    return this.templates.find(t => t.value === value);
  }

  reset(): any {
    this.requestsTable.reset();
  }

  get certificateRequests(): CertificateRequest[] {
    return this.certificateService.certificateRequests;
  }

}
