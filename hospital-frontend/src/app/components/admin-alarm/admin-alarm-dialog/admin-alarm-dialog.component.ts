import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/core/utils/dialog';
import { AdminAlarm } from 'src/app/core/models/admin-alarm';
import { AlarmService } from 'src/app/services/alarm/alarm.service';

@Component({
  selector: 'app-admin-alarm-dialog',
  templateUrl: './admin-alarm-dialog.component.html',
  styleUrls: ['./admin-alarm-dialog.component.scss']
})
export class AdminAlarmDialogComponent implements OnInit {

  constructor(
    private alarmService: AlarmService,
    public dialogRef: MatDialogRef<AdminAlarmDialogComponent>,
    private snackBar: MatSnackBar
  ) { }

  savePending = false;
  alarmForm: FormGroup = new FormGroup({
    status: new FormControl(true, [Validators.required]),
    param: new FormControl('', [Validators.required]),
    counts: new FormControl('', [Validators.required, Validators.pattern(/^[0-9]\d*$/)])
  });

  confirm(): void{
    this.savePending = true;
    // tslint:disable-next-line: deprecation
    this.alarmService.saveAdmin(this.alarmForm.value).subscribe(
      (alarm: AdminAlarm) => {
        this.savePending = false;
        if (alarm){
          this.snackBar.open('Alarm saved!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
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
