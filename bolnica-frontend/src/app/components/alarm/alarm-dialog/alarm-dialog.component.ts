import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/dialog';
import { Alarm } from 'src/app/models/alarm';
import { Patient } from 'src/app/models/patient';
import { AlarmService } from 'src/app/services/alarm/alarm.service';

@Component({
  selector: 'app-alarm-dialog',
  templateUrl: './alarm-dialog.component.html',
  styleUrls: ['./alarm-dialog.component.scss']
})
export class AlarmDialogComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) private patient: Patient,
    private alarmService: AlarmService,
    public dialogRef: MatDialogRef<AlarmDialogComponent>,
    private snackBar: MatSnackBar
  ) { }

  // dodaj da se forma validira tako da ne mogu da posaljem ako su mi bas svi prazni
  savePending = false;
  alarmForm: FormGroup = new FormGroup({
    minPulse: new FormControl(''),
    maxPulse: new FormControl(''),
    minPressure: new FormControl(''),
    maxPressure: new FormControl(''),
    minTemperature: new FormControl(''),
    maxTemperature: new FormControl(''),
    minOxygenLevel: new FormControl(''),
    maxOxygenLevel: new FormControl('')
  });

  confirm(): void{
    this.savePending = true;
    // tslint:disable-next-line: deprecation
    this.alarmService.save(this.patient.id, this.alarmForm.value).subscribe(
      (alarm: Alarm) => {
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
