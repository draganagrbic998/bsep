import { Component, Inject, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/utils/dialog';
import { Patient } from 'src/app/models/patient';
import { PatientService } from 'src/app/services/patient.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.component.scss']
})
export class PatientFormComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public patient: Patient,
    private patientService: PatientService,
    private dialogRef: MatDialogRef<PatientFormComponent>,
    private snackBar: MatSnackBar,
    private formBuilder: FormBuilder
  ) { }

  pending = false;
  patientForm = this.formBuilder.group({
    firstName: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
    lastName: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
    gender: ['', [Validators.required]],
    blodType: ['', [Validators.required]],
    height: ['', [Validators.required, Validators.pattern(/^[0-9]\d*$/)]],
    weight: ['', [Validators.required, Validators.pattern(/^[0-9]\d*$/)]],
    address: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
    city: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
    birthDate: ['', [Validators.required, this.birthDateValidator()]]
  });

  confirm(): void{
    if (this.patientForm.invalid){
      this.patientForm.updateValueAndValidity();
      return;
    }
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.patientService.save({...this.patient, ...this.patientForm.value}).subscribe(
      (patient: Patient) => {
        this.pending = false;
        if (patient){
          this.patientService.announceRefreshData();
          this.snackBar.open('Patient saved!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.dialogRef.close();
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

  private birthDateValidator(): ValidatorFn{
    return (control: AbstractControl): ValidationErrors => {
      let dateValid = true;
      if (control.value >= new Date()){
        dateValid = false;
      }
      return dateValid ? null : {dateError: true};
    };
  }

  ngOnInit(): void {
    if (this.patient.id){
      this.patientForm.reset(this.patient);
      this.patientForm.controls.birthDate.reset(new Date(this.patient.birthDate));
    }
  }

}
