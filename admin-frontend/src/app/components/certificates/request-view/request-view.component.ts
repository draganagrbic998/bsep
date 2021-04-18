import { Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { Table } from 'primeng/table';
import { CertificateService } from '../../../core/services/certificate.service';
import { LazyLoadEvent, MenuItem } from 'primeng/api';
import { CertificateRequest } from 'src/app/core/model/certificate-request';
import { Page } from 'src/app/core/model/page';

@Component({
  selector: 'app-request-view',
  templateUrl: './request-view.component.html',
  styleUrls: ['./request-view.component.scss']
})
export class RequestViewComponent {

  @ViewChild('table') table: Table;
  @Output() openRequest: EventEmitter<CertificateRequest> = new EventEmitter<CertificateRequest>();

  requests: CertificateRequest[] = [];
  loading = true;
  rows = 10;
  totalRecords = 0;

  constructor(private certificateService: CertificateService) { }

  getRequests(event: LazyLoadEvent): void {
    this.loading = true;
    const page = Math.floor(event.first / this.rows);
    const size = this.rows;
    // tslint:disable-next-line: deprecation
    this.certificateService.findAllRequests(page, size).subscribe((response: Page<CertificateRequest>) => {
      this.loading = false;
      this.requests = response.content;
      this.totalRecords = response.totalElements;
    });
  }

  reset(): void {
    this.table.reset();
  }

  getMenuItems(certificateRequest: CertificateRequest): MenuItem[] {
    return [
      {icon: 'pi pi-info', label: 'Details', command: () => this.openRequest.emit(certificateRequest)},
    ];
  }

}
