import {Component, OnInit, ViewChild} from '@angular/core';
import {CertificateService} from '../../../core/services/certificate.service';
import {CertificateInfo} from '../../../core/model/certificate-info';
import {LazyLoadEvent, MessageService} from 'primeng/api';
import {BehaviorSubject} from 'rxjs';
import {Table} from 'primeng/table';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.scss']
})
export class CertificatesComponent implements OnInit {

  certificate: CertificateInfo = new CertificateInfo();
  submitted = false;
  newDialog = false;
  detailsDialog = false;
  templates: any[];
  caAlias: BehaviorSubject<string> = new BehaviorSubject('root');

  // table
  rows = 10;
  totalRecords = 0;
  first = 0;
  loading = true;
  @ViewChild('table')
  table: Table;

  constructor(private certificateService: CertificateService,
              private messageService: MessageService) { }

  ngOnInit(): void {

    this.templates = [
      {label: 'Subordinate CA', value: 'SUB_CA', icon: 'pi pi-globe'},
      {label: 'TLS Server', value: 'TLS', icon: 'pi pi-cloud'},
      {label: 'User', value: 'USER', icon: 'pi pi-user'}
    ];

    this.getCA();
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

  getCA(): void {
    this.certificateService.getByAlias(this.caAlias.getValue()).subscribe(val => {
      this.certificateService.ca.next(val);
    });
  }

  openNew(): void {
    this.certificate = new CertificateInfo();
    this.submitted = false;
    this.newDialog = true;
  }

  openDetails(): void {
    this.certificate = this.ca;
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

  saveCertificate(): void {
    this.submitted = true;

    if (this.certificate.commonName.trim()) {
      this.certificate.issuerAlias = this.caAlias.getValue();
      this.certificateService.createCertificate(this.certificate).subscribe(val => {
        this.table.reset();
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

  get certificates(): CertificateInfo[] {
    return this.certificateService.certificates;
  }

  get ca(): CertificateInfo {
    return this.certificateService.ca.getValue();
  }

  get currentPage(): number {
    return Math.floor(this.first / this.rows);
  }
}
