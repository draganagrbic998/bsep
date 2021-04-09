import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Patient } from 'src/app/models/patient';

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.scss']
})
export class PatientDetailsComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public patient: Patient
  ) { }

  ngOnInit(): void {
  }

}
