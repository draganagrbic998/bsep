import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
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
    private dialogRef: MatDialogRef<RevokeFormComponent>,
    private snackBar: MatSnackBar
  ) { }

  pending = false;
  revokeForm: FormGroup = new FormGroup({
    certFileName: new FormControl('', [Validators.required, Validators.pattern(new RegExp('.+_.+_.+\.jks'))]),
  });

  confirm(): void {
    if (this.revokeForm.invalid){
      return;
    }
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.certificateService.revoke(this.revokeForm.value.certFileName).subscribe(
      (response: boolean) => {
        this.pending = false;
        if (response){
          this.snackBar.open('Certificate revoked!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
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

