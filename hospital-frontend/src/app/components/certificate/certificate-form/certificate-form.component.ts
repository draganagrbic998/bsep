import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CertificateRequest } from 'src/app/models/certificate-request';
import { CertificateService } from 'src/app/services/certificate.service';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';

@Component({
  selector: 'app-certificate-form',
  templateUrl: './certificate-form.component.html',
  styleUrls: ['./certificate-form.component.scss']
})
export class CertificateFormComponent implements OnInit {

  constructor(
    private certificateService: CertificateService,
    private dialogRef: MatDialogRef<CertificateFormComponent>,
    private snackBar: MatSnackBar
  ) { }

  savePending = false;
  certificateForm: FormGroup = new FormGroup({
    alias: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    commonName: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    organization: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    organizationUnit: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    country: new FormControl('', [Validators.required, Validators.pattern(new RegExp('[A-Z]{2}'))]),
    email: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$')]),
    template: new FormControl('', [Validators.required]),
    type: new FormControl('', [Validators.required])
  });

  sendRequest(): void {
    if (this.certificateForm.invalid){
      return;
    }
    this.savePending = true;
    // tslint:disable-next-line: deprecation
    this.certificateService.sendRequest(this.certificateForm.value).subscribe(
      (certificateRequest: CertificateRequest) => {
        this.savePending = false;
        if (certificateRequest){
          this.snackBar.open('Request sent!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.dialogRef.close();
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
