import { Component, OnInit } from '@angular/core';
import {CertificateService} from '../../../core/services/certificate.service';
import {CertificateInfo} from '../../../core/model/certificate-info';
import {MessageService} from 'primeng/api';

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
  caAlias = 'root';

  constructor(private certificateService: CertificateService,
              private messageService: MessageService) { }

  ngOnInit(): void {

    this.templates = [
      {label: 'Subordinate CA', value: 'SUB_CA'},
      {label: 'TLS Server', value: 'TLS'},
      {label: 'User', value: 'USER'}
    ];

    this.getCertificates();
    this.getCA();
  }

  getCertificates(): void {
    this.certificateService.getCertificates().subscribe(val => {
      this.certificateService.certificates = val;
    });
  }

  getCA(): void {
    this.certificateService.getByAlias(this.caAlias).subscribe(val => {
      this.certificateService.ca = val;
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
      this.certificate.issuerAlias = this.caAlias;
      this.certificateService.createCertificate(this.certificate).subscribe(val => {
        this.getCertificates();
        this.newDialog = false;
        this.messageService.add({severity: 'success', summary: 'Success', detail: `${this.certificate.commonName} successfully created.`});
        this.certificate = new CertificateInfo();

      });

    }
  }

  get certificates(): CertificateInfo[] {
    return this.certificateService.certificates;
  }

  get ca(): CertificateInfo {
    return this.certificateService.ca;
  }

}
