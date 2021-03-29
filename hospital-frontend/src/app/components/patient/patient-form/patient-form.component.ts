import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/dialog';
import { Patient } from 'src/app/models/patient';
import { PatientService } from 'src/app/services/patient/patient.service';
import { StorageService } from 'src/app/services/storage/storage.service';

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.component.scss']
})
export class PatientFormComponent implements OnInit {

  constructor(
    private storageService: StorageService,
    private patientService: PatientService,
    private snackBar: MatSnackBar,
    public location: Location,
    private route: ActivatedRoute
  ) { }

  patient: Patient = {} as Patient;
  patientForm: FormGroup = new FormGroup({
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
    this.patientService.save({...this.patient, ...this.patientForm.value}).subscribe(
      (patient: Patient) => {
        this.savePending = false;
        if (patient){
          this.snackBar.open('Patient saved!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
          this.location.back();
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
    if (this.route.snapshot.params.mode === 'edit'){
      this.patient = this.storageService.get(this.storageService.PATIENT_KEY) as Patient;
      this.patientForm.reset(this.patient);
      this.patientForm.controls.birthDate.reset(new Date(this.patient.birthDate));
    }
  }

}
