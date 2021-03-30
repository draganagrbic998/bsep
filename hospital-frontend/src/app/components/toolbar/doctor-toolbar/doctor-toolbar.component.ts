import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { PatientService } from 'src/app/services/patient.service';
import { StorageService } from 'src/app/services/storage.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-doctor-toolbar',
  templateUrl: './doctor-toolbar.component.html',
  styleUrls: ['./doctor-toolbar.component.scss']
})
export class DoctorToolbarComponent implements OnInit {

  constructor(
    private storageService: StorageService,
    private patientService: PatientService,
    private router: Router
  ) { }

  search: FormControl = new FormControl('');

  onRoute(param: string): boolean{
    return this.router.url.substr(1).includes(param);
  }

  signOut(): void{
    this.storageService.removeUser();
    this.router.navigate([environment.loginRoute]);
  }

  createPatient(): void{
    this.storageService.remove(this.storageService.PATIENT_KEY);
    this.router.navigate([`${environment.patientFormRoute}/create`]);
  }

  announceSearchData(): void{
    this.patientService.announceSearchData(this.search.value.trim());
  }

  ngOnInit(): void {
  }

}
