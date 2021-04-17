import { Component, Inject } from '@angular/core';
import { AbstractControl, FormBuilder, ValidationErrors, ValidatorFn } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';
import { DoctorAlarm } from 'src/app/models/doctor-alarm';
import { AlarmService } from 'src/app/services/alarm.service';

@Component({
  selector: 'app-doctor-alarm-form',
  templateUrl: './doctor-alarm-form.component.html',
  styleUrls: ['./doctor-alarm-form.component.scss']
})
export class DoctorAlarmFormComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) private patientId: number,
    private alarmService: AlarmService,
    private dialogRef: MatDialogRef<DoctorAlarmFormComponent>,
    private snackBar: MatSnackBar,
    private formBuilder: FormBuilder
  ) { }

  pending = false;
  alarmForm = this.formBuilder.group({
    minPulse: [''],
    maxPulse: [''],
    minPressure: [''],
    maxPressure: [''],
    minTemperature: [''],
    maxTemperature: [''],
    minOxygenLevel: [''],
    maxOxygenLevel: ['']
  }, {
    validators: this.minMaxValidator()
  });

  confirm(): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.alarmService.saveDoctor(this.patientId, this.alarmForm.value).subscribe(
      (alarm: DoctorAlarm) => {
        this.pending = false;
        if (alarm){
          this.alarmService.announceRefreshDoctorData();
          this.snackBar.open('Alarm saved!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.dialogRef.close();
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

  minMaxValidator(): ValidatorFn{
    return (control: AbstractControl): ValidationErrors => {
      const params: string[] = ['Pulse', 'Pressure', 'Temperature', 'OxygenLevel'];
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
      if (this.alarmForm.controls[control].value.trim().length !== 0){
        return false;
      }
    }
    return true;
  }

}
