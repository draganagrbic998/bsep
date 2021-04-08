import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CertificateService } from 'src/app/services/certificate.service';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';

@Component({
  selector: 'app-revoke-form',
  templateUrl: './revoke-form.component.html',
  styleUrls: ['./revoke-form.component.scss']
})
export class RevokeFormComponent implements OnInit {

  constructor(
    private certificateService: CertificateService,
    private snackBar: MatSnackBar,
    private location: Location
  ) { }

  savePending = false;
  revokeForm: FormGroup = new FormGroup({
    certFileName: new FormControl('', [Validators.required, Validators.pattern(new RegExp('.+_.+_.+\.jks'))]),
  });

  sendRequest(): void {
    if (this.revokeForm.invalid){
      return;
    }
    this.savePending = true;
    // tslint:disable-next-line: deprecation
    this.certificateService.sendRevokeRequest(this.revokeForm.value.certFileName).subscribe(
      (response: boolean) => {
        this.savePending = false;
        if (response){
          this.snackBar.open('Certificate revoked!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
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

