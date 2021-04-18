import { Component, OnInit } from '@angular/core';
import { CertificateInfo } from '../../../core/model/certificate-info';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CertificateService } from '../../../core/services/certificate.service';
import { Router } from '@angular/router';
import { keyUsages } from '../../../core/utils/key-usages';
import { extensionTemplates, getTemplate } from '../../../core/utils/templates';
import { Template } from '../../../core/model/template';
import { Extensions } from '../../../core/model/extensions';
import { keyPurposeIds } from '../../../core/utils/key-purpose-ids';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { ROOT_ALIAS } from 'src/app/core/utils/constants';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrls: ['./add-certificate.component.scss']
})
export class AddCertificateComponent implements OnInit {

  templates = Object.values(extensionTemplates);
  keyUsages = Object.values(keyUsages);
  keyPurposeIds = Object.values(keyPurposeIds);

  certificateForm: FormGroup;
  loading = false;

  constructor(
    private certificateService: CertificateService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.initForm();
  }

  get extensionOptions(): { label: string }[] {
    return Object.keys(new Extensions()).map(s => {
      return {
        label: s
      };
    });
  }

  save(): void {
    if (this.certificateForm.valid) {
      const certificate = {...this.certificateService.certificate, ...this.certificateForm.value, ...{
        issuerAlias: this.certificateService.ca.alias,
        template: (this.certificateForm.get('template').value as Template).enumValue,
        extensions: {
          basicConstraints: this.certificateForm.get('basicConstraints').value,
          // tslint:disable-next-line: no-bitwise
          keyUsage: this.certificateForm.get('keyUsage').value?.map(ku => ku.value).reduce((a, b) => a | b),
          keyPurposeIds: this.certificateForm.get('extendedKeyUsage').value?.map(eku => eku.value)
        }
      }};

      if (certificate.extensions.basicConstraints === null &&
        !certificate.extensions.keyPurposeIds &&
        !certificate.extensions.keyUsage) {
        this.confirmationService.confirm({
          header: 'Are you sure?',
          message: `There are no extensions selected for the certificate. Do you want to proceed?`,
          accept: () => this.confirm(certificate)
        });
      }
      else {
        this.confirm(certificate);
      }
    }
    else{
      // tslint:disable-next-line: forin
      for (const control in this.certificateForm.controls){
        this.certificateForm.controls[control].markAsDirty();
      }
    }
  }

  templateChanged(event: any): void {
    const template: Template = event.value;
    if (!template) {
      this.certificateForm.get('extensions').setValue(null);
      this.certificateForm.get('basicConstraints').setValue( null);
      this.certificateForm.get('keyUsage').setValue(null);
      this.certificateForm.get('extendedKeyUsage').setValue(null);
      return;
    }
    this.setExtensions(template);
  }

  extensionClicked(event: any): void {
    const extension: string = event.itemValue.label;
    if (extension === 'basicConstraints'){
      this.certificateForm.get('basicConstraints')
      .setValue(this.certificateForm.get('basicConstraints').value !== null ? null
      : getTemplate(this.certificateForm.value.template.enumValue).extensions[extension]);
    }
    else if (extension === 'keyUsage'){
      this.certificateForm.get('keyUsage')
      .setValue(this.certificateForm.get('keyUsage').value ? null
      : getTemplate(this.certificateForm.value.template.enumValue).extensions[extension]);
    }
    else if (extension === 'extendedKeyUsage'){
      this.certificateForm.get('extendedKeyUsage')
      .setValue(this.certificateForm.get('extendedKeyUsage').value ? null
      : getTemplate(this.certificateForm.value.template.enumValue).extensions[extension]);
    }
  }

  private confirm(certificate: CertificateInfo): void {
    this.loading = true;
    // tslint:disable-next-line: deprecation
    this.certificateService.create(certificate).subscribe((response: CertificateInfo) => {
      this.loading = false;
      if (response){
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: `${certificate.alias} created.`
        });
        this.router.navigate([environment.certificatesRoute]);
      }
      else{
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error occured while creating certificate!'
        });
      }
    });
  }

  private setExtensions(template: Template): void {
    const extensions = [];
    if (template.extensions.basicConstraints !== null) {
      extensions.push({label: 'basicConstraints'});
    }
    if (template.extensions.keyUsage !== null) {
      extensions.push({label: 'keyUsage'});
    }
    if (template.extensions.extendedKeyUsage !== null) {
      extensions.push({label: 'extendedKeyUsage'});
    }
    this.certificateForm.get('extensions').setValue(extensions);
    this.certificateForm.get('basicConstraints').setValue(template.extensions.basicConstraints);
    this.certificateForm.get('keyUsage').setValue(template.extensions.keyUsage);
    this.certificateForm.get('extendedKeyUsage').setValue(template.extensions.extendedKeyUsage);
  }

  private initForm(): void {
    this.certificateForm = this.formBuilder.group({
      alias: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
      commonName: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
      organization: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
      organizationUnit: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
      country: ['', [Validators.required, Validators.pattern(new RegExp('[A-Z]{2}'))]],
      email: ['', [Validators.required, Validators.email]],
      template: ['', [Validators.required]],
      extensions: [null],
      basicConstraints: [null],
      keyUsage: [null],
      extendedKeyUsage: [null]
    });
  }

  private setForm(certificate: CertificateInfo): void {
    this.certificateForm.reset(certificate);
    this.certificateForm.get('template').setValue(getTemplate(certificate.template));
    this.setExtensions(getTemplate(certificate.template));
  }

  ngOnInit(): void {
    if (!this.certificateService.ca){
      this.certificateService.findByAlias(ROOT_ALIAS)
      // tslint:disable-next-line: deprecation
      .subscribe((ca: CertificateInfo) => this.certificateService.ca = ca);
    }
    if (this.certificateService.certificate) {
      setTimeout(() => this.setForm(this.certificateService.certificate), 1);
    }
  }

}
