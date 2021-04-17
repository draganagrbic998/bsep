import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';
import { AdminAlarm } from 'src/app/models/admin-alarm';
import { AlarmService } from 'src/app/services/alarm.service';

@Component({
  selector: 'app-admin-alarm-form',
  templateUrl: './admin-alarm-form.component.html',
  styleUrls: ['./admin-alarm-form.component.scss']
})
export class AdminAlarmFormComponent {

  constructor(
    private alarmService: AlarmService,
    private dialogRef: MatDialogRef<AdminAlarmFormComponent>,
    private snackBar: MatSnackBar,
    private formBuilder: FormBuilder
  ) { }

  pending = false;
  alarmForm = this.formBuilder.group({
    status: [true, Validators.required],
    param: ['', Validators.required],
    counts: ['', Validators.required]
  });

  confirm(): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.alarmService.saveAdmin(this.alarmForm.value).subscribe(
      (alarm: AdminAlarm) => {
        this.pending = false;
        if (alarm){
          this.alarmService.announceRefreshAdminData();
          this.snackBar.open('Alarm saved!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.dialogRef.close();
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

}
