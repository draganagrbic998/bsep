import { Component, Input, OnInit } from '@angular/core';
import { Patient } from 'src/app/models/patient';

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.scss']
})
export class PatientDetailsComponent implements OnInit {

  constructor() { }

  @Input() patient: Patient = {} as Patient;

  edit(): void{

  }

  delete(): void{

  }

  ngOnInit(): void {
  }

}
