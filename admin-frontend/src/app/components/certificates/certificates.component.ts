import {Component, OnInit, ViewChild} from '@angular/core';
import {CertificateService} from '../../core/services/certificate.service';
import {CertificateInfo} from '../../core/model/certificate-info';
import {LazyLoadEvent, MenuItem, MessageService} from 'primeng/api';
import {BehaviorSubject} from 'rxjs';
import {Table} from 'primeng/table';
import {TableViewComponent} from '../table-view/table-view.component';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ConfirmationService} from 'primeng/api';

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
  @ViewChild('table')
  table: TableViewComponent;

  constructor(private certificateService: CertificateService,
              private messageService: MessageService,
              private confirmationService: ConfirmationService) { }

  ngOnInit(): void {

    this.templates = [
      {label: 'CA', value: 'SUB_CA', icon: 'pi pi-globe'},
      {label: 'TLS Server', value: 'TLS', icon: 'pi pi-cloud'},
      {label: 'User', value: 'USER', icon: 'pi pi-user'}
    ];

    this.getCA();
  }

  getCA(): void {
    this.certificateService.getByAlias(this.caAlias.getValue()).subscribe(val => {
      console.log(val);
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

  revokeCertificate(certificate: CertificateInfo): void {
    this.confirmationService.confirm({
      icon: 'pi pi-exclamation-triangle',
      header: 'Confirm Revocation',
      message: `Are you sure that you want to revoke ${certificate.commonName}?`,
      defaultFocus: 'reject',
      accept: () => {
        this.certificateService.revokeCertificate(certificate.id).subscribe(val => {
          if (val) {
            certificate.revoked = true;
            this.table.reset();
            this.messageService.add({severity: 'success', summary: 'Success', detail: `${certificate.commonName} successfully revoked.`});
          }
          else {
            this.messageService.add({severity: 'error', summary: 'Failure', detail: `Error occured while revoking ${this.certificate.commonName}.`});
          }
        });
       }
    });
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

}
