import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { PatientService } from 'src/app/services/patient.service';
import { StorageService } from 'src/app/services/storage.service';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { environment } from 'src/environments/environment';
import { PatientFormComponent } from '../../patient/patient-form/patient-form.component';

@Component({
  selector: 'app-doctor-toolbar',
  templateUrl: './doctor-toolbar.component.html',
  styleUrls: ['./doctor-toolbar.component.scss']
})
export class DoctorToolbarComponent implements OnInit {

  constructor(
    private storageService: StorageService,
    private patientService: PatientService,
    private router: Router,
    private dialog: MatDialog
  ) { }

  search: FormControl = new FormControl('');

  onRoute(param: string): boolean{
    return this.router.url.substr(1).includes(param);
  }

  signOut(): void{
    this.storageService.removeUser();
    this.router.navigate([environment.loginFormRoute]);
  }

  openPatientForm(): void{
    this.dialog.open(PatientFormComponent, {...DIALOG_OPTIONS, ...{data: {}}});
  }

  announceSearchData(): void{
    this.patientService.announceSearchData(this.search.value.trim());
  }

  ngOnInit(): void {
  }

}
