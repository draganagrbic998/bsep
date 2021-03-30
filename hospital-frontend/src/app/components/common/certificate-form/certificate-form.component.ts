import { Location } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CertificateRequest } from 'src/app/models/certificate-request';
import { CertificateServiceService } from 'src/app/services/certificate-service.service';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';

@Component({
  selector: 'app-certificate-form',
  templateUrl: './certificate-form.component.html',
  styleUrls: ['./certificate-form.component.scss']
})
export class CertificateFormComponent implements OnInit {

  constructor(
    private certificateService: CertificateServiceService,
    private snackBar: MatSnackBar,
    public location: Location,
  ) { }

  @Input() savePending: boolean;

  certificateRequestForm: FormGroup = new FormGroup({
    alias: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    commonName: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    organization: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    organizationUnit: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    country: new FormControl('', [Validators.required, Validators.pattern(new RegExp('[A-Z]{2}'))]),
    email: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    template: new FormControl('', [Validators.required])
  });

  sendRequest(): void {
    if (this.certificateRequestForm.invalid){
      return;
    }
    this.savePending = true;
    // tslint:disable-next-line: deprecation
    this.certificateService.sendRequest(this.certificateRequestForm.value).subscribe(
      (certificateRequest: CertificateRequest) => {
        this.savePending = false;
        if (certificateRequest){
          this.snackBar.open('Request sent!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.location.back();
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

  ngOnInit(): void {
  }

}
