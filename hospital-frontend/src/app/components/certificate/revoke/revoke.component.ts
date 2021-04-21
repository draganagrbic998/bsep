import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CertificateService } from 'src/app/services/certificate.service';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';

@Component({
  selector: 'app-revoke',
  templateUrl: './revoke.component.html',
  styleUrls: ['./revoke.component.scss']
})
export class RevokeComponent {

  constructor(
    private certificateService: CertificateService,
    private dialogRef: MatDialogRef<RevokeComponent>,
    private snackBar: MatSnackBar
  ) { }

  pending = false;
  fileName = new FormControl('', [Validators.required, Validators.pattern(new RegExp('.+_.+_.+\.jks'))]);

  confirm(): void {
    if (this.fileName.invalid){
      return;
    }
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.certificateService.revoke(this.fileName.value.trim()).subscribe(
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

}

