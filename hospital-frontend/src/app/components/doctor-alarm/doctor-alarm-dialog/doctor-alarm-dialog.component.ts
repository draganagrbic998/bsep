import { Component, Inject, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/dialog';
import { DoctorAlarm } from 'src/app/models/doctor-alarm';
import { AlarmService } from 'src/app/services/alarm/alarm.service';

@Component({
  selector: 'app-doctor-alarm-dialog',
  templateUrl: './doctor-alarm-dialog.component.html',
  styleUrls: ['./doctor-alarm-dialog.component.scss']
})
export class DoctorAlarmDialogComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) private patientId: number,
    private alarmService: AlarmService,
    public dialogRef: MatDialogRef<DoctorAlarmDialogComponent>,
    private snackBar: MatSnackBar
  ) { }

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
  }, {
    validators: [this.minMaxValidator()]
  });

  confirm(): void{
    this.savePending = true;
    // tslint:disable-next-line: deprecation
    this.alarmService.saveDoctor(this.patientId, this.alarmForm.value).subscribe(
      (alarm: DoctorAlarm) => {
        this.savePending = false;
        if (alarm){
          this.snackBar.open('Alarm saved!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.dialogRef.close();
          this.alarmService.announceRefreshDoctorData();
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

  minMaxValidator(): ValidatorFn{
    return (control: AbstractControl): ValidationErrors => {
      const params = ['Pulse', 'Pressure', 'Temperature', 'OxygenLevel'];
      for (const param of params){
        if (parseInt(control.get('min' + param).value, 10) >= parseInt(control.get('max' + param).value, 10)){
          return {error: true};
        }
      }
      return null;
    };
  }

  get emptyForm(): boolean{
    for (const control in this.alarmForm.controls){
      if (this.alarmForm.controls[control].value.trim() !== ''){
        return false;
      }
    }
    return true;
  }

  ngOnInit(): void {
  }

}
