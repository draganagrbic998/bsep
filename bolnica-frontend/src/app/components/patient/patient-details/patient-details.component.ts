import { Component, OnInit } from '@angular/core';
import { Patient } from 'src/app/models/patient';
import { StorageService } from 'src/app/services/storage/storage.service';

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.scss']
})
export class PatientDetailsComponent implements OnInit {

  constructor(
    private storageService: StorageService
  ) { }

  patient: Patient;

  ngOnInit(): void {
    this.patient = this.storageService.get(this.storageService.PATIENT_KEY) as Patient;
  }

}
