import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {CertificateInfo} from '../../../core/model/certificate-info';
import {ConfirmationService, MessageService} from 'primeng/api';
import {CertificateService} from '../../../core/services/certificate.service';
import {ActivatedRoute, Router} from '@angular/router';
import {keyUsages} from '../../../core/utils/key-usages';
import {extensionTemplates, getTemplate} from '../../../core/utils/templates';
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
  caAlias = 'root';
  keyUsages = Object.values(keyUsages);
  keyPurposeIds = Object.values(keyPurposeIds);
  certificateForm: FormGroup;


  templates = Object.values(extensionTemplates);

  constructor(private messageService: MessageService,
              private certificateService: CertificateService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private confirmationService: ConfirmationService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.caAlias = this.certificateService.ca.getValue().alias;
    this.certificate = this.certificateService.requestCertificate.getValue();
    if (!this.caAlias) {
      this.router.navigate(['/certificates']);
    }

    this.setNewCertificateForm();

    if (!!this.certificate) {
      setTimeout(() => this.setPredefinedCertificateForm(this.certificate), 1);
    }
  }

  setNewCertificateForm(): void {
    this.certificateForm = this.formBuilder.group({
      commonName: [null, Validators.required],
      alias: [null, Validators.required],
      organization: [null, Validators.required],
      organizationUnit: [null, Validators.required],
      countryCode: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
      template: [null],
      extensions: [null],
      keyUsage: [null],
      basicConstraints: [null],
      extendedKeyUsage: [null]
    });
  }

  setPredefinedCertificateForm(certificate: CertificateInfo): void {
    this.certificateForm.get('commonName').setValue(certificate.commonName);
    this.certificateForm.get('alias').setValue(certificate.alias);
    this.certificateForm.get('organization').setValue(certificate.organization);
    this.certificateForm.get('organizationUnit').setValue(certificate.organizationUnit);
    this.certificateForm.get('countryCode').setValue(certificate.country);
    this.certificateForm.get('email').setValue(certificate.email);

    const template = getTemplate(certificate.template);

    this.certificateForm.get('template').setValue(template);

    this.setExtensions(template);
  }

  checkExtensions(): void {
    if (this.certificateForm.valid) {
      this.certificate.commonName = this.certificateForm.get('commonName').value;
      this.certificate.alias = this.certificateForm.get('alias').value;
      this.certificate.organization = this.certificateForm.get('organization').value;
      this.certificate.organizationUnit = this.certificateForm.get('organizationUnit').value;
      this.certificate.country = this.certificateForm.get('countryCode').value;
      this.certificate.email = this.certificateForm.get('email').value;
      this.certificate.template = (this.certificateForm.get('template').value as Template)?.enumValue;
      this.certificate.issuerAlias = this.caAlias;
      // tslint:disable-next-line: deprecation
      this.certificate.extensions.basicConstraints = this.certificateForm.get('basicConstraints').value;
      this.certificate.extensions.keyPurposeIds = this.certificateForm.get('extendedKeyUsage').value?.map(ex => ex.value);
      // tslint:disable-next-line:no-bitwise
      this.certificate.extensions.keyUsage = this.certificateForm.get('keyUsage').value?.map(ku => ku.value).reduce((e, d) => e | d);
      if (this.certificate.extensions.basicConstraints === null &&
        !this.certificate.extensions.keyPurposeIds &&
        !this.certificate.extensions.keyUsage) {
        this.confirmationService.confirm({
          header: 'Are you sure?',
          message: `There are no extensions selected for the certificate. Do you want to proceed?`,
          accept: () => this.saveCertificate()
        });
      } else {
        this.saveCertificate();
      }
    } else {
      this.certificateForm.get('commonName').markAsDirty();
      this.certificateForm.get('alias').markAsDirty();
      this.certificateForm.get('organization').markAsDirty();
      this.certificateForm.get('organizationUnit').markAsDirty();
      this.certificateForm.get('countryCode').markAsDirty();
      this.certificateForm.get('email').markAsDirty();
    }
  }

  saveCertificate(): void {
    this.certificateService.createCertificate(this.certificate).subscribe(() => {

      this.messageService.add({
        severity: 'success',
        summary: 'Success',
        detail: `${this.certificate.commonName} successfully created.`
      });

      this.router.navigate(['..']);

    }, () => {
      this.messageService.add({
        severity: 'error',
        summary: 'Failure', detail: `Error occured while creating ${this.certificate.alias}.`
      });
    });
  }

  itemClicked(event: any): void {
    this.certificateForm.get('template').setValue(null);
    switch (event.itemValue.label) {
      case 'extendedKeyUsage':
        this.certificateForm.get('extendedKeyUsage')
          .setValue(!!this.certificateForm.get('extendedKeyUsage').value ? null : []);
        break;
      case 'keyUsage':
        this.certificateForm.get('keyUsage')
          .setValue(!!this.certificateForm.get('keyUsage').value ? null : []);
        break;
      case 'basicConstraints':
        this.certificateForm.get('basicConstraints')
          .setValue(this.certificateForm.get('basicConstraints').value !== null ? null : []);
        break;
    }
  }

  templateChanged(event: any): void {
    const changedTemplate: Template = event.value;
    if (!changedTemplate) {
      this.certificateForm.get('extensions').setValue(null);
      this.certificateForm.get('basicConstraints').setValue( null);
      this.certificateForm.get('extendedKeyUsage').setValue(null);
      this.certificateForm.get('keyUsage').setValue(null);
      return;
    }
    this.setExtensions(changedTemplate);
  }

  setExtensions(template: Template): void {
    this.certificateForm.get('basicConstraints').setValue(template.extensions.basicConstraints);
    this.certificateForm.get('keyUsage').setValue(template.extensions.keyUsage);
    this.certificateForm.get('extendedKeyUsage').setValue(template.extensions.extendedKeyUsage);
    const extensions = [];
    if (this.certificateForm.get('keyUsage').value !== null) {
      extensions.push({label: 'keyUsage'});
    }
    if (this.certificateForm.get('extendedKeyUsage').value !== null) {
      extensions.push({label: 'extendedKeyUsage'});
    }
    if (this.certificateForm.get('basicConstraints').value !== null) {
      extensions.push({label: 'basicConstraints'});
    }
    this.certificateForm.get('extensions').setValue(extensions);

  }

  ngOnDestroy(): void {
    this.certificate = new CertificateInfo();
    this.caAlias = 'root';
  }

  get extensionOptions(): { label: string }[] {
    return Object.keys(new Extensions()).map(s => {
      return {
        label: s
      };
    });
  }
}
