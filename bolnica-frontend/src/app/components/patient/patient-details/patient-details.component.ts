import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DIALOG_OPTIONS } from 'src/app/constants/dialog';
import { Patient } from 'src/app/models/patient';
import { PatientService } from 'src/app/services/patient/patient.service';
import { StorageService } from 'src/app/services/storage/storage.service';
import { environment } from 'src/environments/environment';
import { DeleteConfirmationComponent } from '../../shared/controls/delete-confirmation/delete-confirmation.component';

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.scss']
})
export class PatientDetailsComponent implements OnInit {

  constructor(
    private storageService: StorageService,
    private patientService: PatientService,
    private dialog: MatDialog,
    private router: Router
  ) { }

  @Input() patient: Patient = {} as Patient;

  edit(): void{
    this.storageService.set(this.storageService.PATIENT_KEY, this.patient);
    this.router.navigate([`${environment.patientFormRoute}/edit`]);
  }

  delete(): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: () => this.patientService.delete(this.patient.id)}};
    // tslint:disable-next-line: deprecation
    this.dialog.open(DeleteConfirmationComponent, options).afterClosed().subscribe(result => {
      if (result){
        this.patientService.announceRefreshData();
      }
    });
  }

  ngOnInit(): void {
  }

}
