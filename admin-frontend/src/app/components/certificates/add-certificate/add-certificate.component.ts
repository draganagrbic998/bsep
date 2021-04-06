import {Component, OnDestroy, OnInit} from '@angular/core';
import {CertificateInfo} from '../../../core/model/certificate-info';
import {ConfirmationService, MessageService} from 'primeng/api';
import {CertificateService} from '../../../core/services/certificate.service';
import {ActivatedRoute, Router} from '@angular/router';
import {keyUsages} from '../../../core/utils/key-usages';
import {extensionTemplates} from '../../../core/utils/templates';
import {Template} from '../../../core/model/template';
import {Extensions} from '../../../core/model/extensions';
import {keyPurposeIds} from '../../../core/utils/key-purpose-ids';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrls: ['./add-certificate.component.scss']
})
export class AddCertificateComponent implements OnInit, OnDestroy {

  certificate: CertificateInfo = new CertificateInfo();
  submitted = false;
  caAlias = 'root';
  keyUsages = keyUsages;
  keyPurposeIds = keyPurposeIds;
  certificateForm: FormGroup;


  extensions: Extensions = new Extensions();
  templates = extensionTemplates;

  constructor(private messageService: MessageService,
              private certificateService: CertificateService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private confirmationService: ConfirmationService,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(val => {
      if (!val.caAlias) {
        this.hideNew();
      }
      this.caAlias = val.caAlias;
    });

    this.certificateForm = this.formBuilder.group({
      commonName: ['', Validators.required],
      alias: ['', Validators.required],
      organization: ['', Validators.required],
      organizationUnit: ['', Validators.required],
      countryCode: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      template: [{}],
      extensions: [[]],
    });
  }

  checkExtensions(): void {
    this.certificate.extensions.basicConstraints = this.extensions.basicConstraints;
    this.certificate.extensions.extendedKeyUsage = this.extensions.extendedKeyUsage?.map(ex => ex.value);
    // tslint:disable-next-line:no-bitwise
    this.certificate.extensions.keyUsage = this.extensions.keyUsage?.map(ku => ku.value).reduce((e, d) => e | d);
    console.log(this.certificate.extensions.keyUsage);
    if (this.certificate.extensions.basicConstraints === null &&
      !this.certificate.extensions.extendedKeyUsage &&
      !this.certificate.extensions.keyUsage) {
      this.confirmationService.confirm({
        header: 'Are you sure?',
        message: `There are no extensions selected for the certificate. Do you want to proceed?`,
        accept: () => this.saveCertificate()
      });
    } else {
      this.saveCertificate();
    }
  }

  saveCertificate(): void {
    this.submitted = true;

    if (this.certificate.commonName?.trim()) {
      this.certificate.issuerAlias = this.caAlias;
      // tslint:disable-next-line: deprecation
      this.certificateService.createCertificate(this.certificate).subscribe(() => {

        this.messageService.add({severity: 'success', summary: 'Success', detail: `${this.certificate.commonName} successfully created.`});

        this.router.navigate(['..']);

      }, () => {
        this.messageService.add({severity: 'error',
          summary: 'Failure', detail: `Error occured while creating ${this.certificate.alias}.`});
      });
    }
  }


  hideNew(): void {
    this.router.navigate(['certificates']);
  }

  itemClicked(event: any): void {
    this.certificateForm.get('template').setValue(null);
    switch (event.itemValue.label) {
      case 'extendedKeyUsage':
        this.extensions.extendedKeyUsage = !!this.extensions.extendedKeyUsage ? null : [];
        break;
      case 'keyUsage':
        this.extensions.keyUsage = !!this.extensions.keyUsage ? null : [];
        break;
      case 'basicConstraints':
        this.extensions.basicConstraints = this.extensions.basicConstraints !== null ? null : false;
    }
  }

  templateChanged(changedTemplate: Template): void {
    if (!changedTemplate) {
      this.certificateForm.get('extensions').setValue([]);
      this.extensions.basicConstraints = null;
      this.extensions.extendedKeyUsage = null;
      this.extensions.keyUsage = null;
      return;
    }
    this.extensions.basicConstraints = changedTemplate.extensions.basicConstraints;
    this.extensions.keyUsage = changedTemplate.extensions.keyUsage;
    this.extensions.extendedKeyUsage = changedTemplate.extensions.extendedKeyUsage;
    this.certificateForm.get('extensions').setValue([]);
    if (this.extensions.keyUsage !== null) {
      this.certificateForm.get('selectedExtensions').value.push({label: 'keyUsage'});
    }
    if (this.extensions.extendedKeyUsage !== null) {
      this.certificateForm.get('selectedExtensions').value.push({label: 'extendedKeyUsage'});
    }
    if (this.extensions.basicConstraints !== null) {
      this.certificateForm.get('selectedExtensions').value.push({label: 'basicConstraints'});
    }


  }

  ngOnDestroy(): void {
    this.certificate = new CertificateInfo();
    this.submitted = false;
    this.caAlias = 'root';
  }

  get extensionOptions(): { label: string }[] {
    return Object.keys(this.extensions).map(s => {
      return {
        label: s
      };
    });
  }
}
