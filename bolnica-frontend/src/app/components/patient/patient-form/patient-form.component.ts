import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/dialog';
import { Patient } from 'src/app/models/patient';
import { PatientService } from 'src/app/services/patient/patient.service';

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.component.scss']
})
export class PatientFormComponent implements OnInit {

  constructor(
    private patientService: PatientService,
    private snackBar: MatSnackBar,
    public location: Location
  ) { }

  patientForm: FormGroup = new FormGroup({
    insuredNumber: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    firstName: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    lastName: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    birthDate: new FormControl('', [Validators.required, this.birthDateValidator()]),
    gender: new FormControl('', [Validators.required]),
    blodType: new FormControl('', [Validators.required]),
    height: new FormControl('', [Validators.required, Validators.pattern(/^[0-9]\d*$/)]),
    weight: new FormControl('', [Validators.required, Validators.pattern(/^[0-9]\d*$/)]),
    address: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    city: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))])
  });

  savePending = false;

  save(): void{
    if (this.patientForm.invalid){
      return;
    }
    this.savePending = true;
    // tslint:disable-next-line: deprecation
    this.patientService.save(this.patientForm.value).subscribe(
      (patient: Patient) => {
        this.savePending = false;
        if (patient){
          this.snackBar.open('Patient saved!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

  birthDateValidator(): ValidatorFn{
    return (control: AbstractControl): ValidationErrors => {
      let dateValid = true;
      if (control.value >= new Date()){
        dateValid = false;
      }
      return dateValid ? null : {dateError: true};
    };
  }

  ngOnInit(): void {
  }

}
