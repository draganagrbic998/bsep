import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CertificateRequest } from 'src/app/models/certificate-request';
import { CertificateService } from 'src/app/services/certificate.service';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';

@Component({
  selector: 'app-request',
  templateUrl: './request.component.html',
  styleUrls: ['./request.component.scss']
})
export class RequestComponent {

  constructor(
    private certificateService: CertificateService,
    private dialogRef: MatDialogRef<RequestComponent>,
    private snackBar: MatSnackBar,
    private formBuilder: FormBuilder
  ) { }

  pending = false;
  certificateForm = this.formBuilder.group({
    alias: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
    commonName: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
    organization: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
    organizationUnit: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
    country: ['', [Validators.required, Validators.pattern(new RegExp('[A-Z]{2}'))]],
    email: ['', [Validators.required, Validators.email]],
    template: ['', [Validators.required]],
    type: ['', [Validators.required]]
  });

  confirm(): void {
    if (this.certificateForm.invalid){
      return;
    }
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.certificateService.request(this.certificateForm.value).subscribe(
      (certificateRequest: CertificateRequest) => {
        this.pending = false;
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

}
