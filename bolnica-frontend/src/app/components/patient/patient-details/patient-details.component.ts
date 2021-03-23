import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DIALOG_OPTIONS } from 'src/app/constants/dialog';
import { Patient } from 'src/app/models/patient';
import { PatientService } from 'src/app/services/patient/patient.service';
import { DeleteConfirmationComponent } from '../../shared/controls/delete-confirmation/delete-confirmation.component';

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.scss']
})
export class PatientDetailsComponent implements OnInit {

  constructor(
    private patientService: PatientService,
    private dialog: MatDialog
  ) { }

  @Input() patient: Patient = {} as Patient;

  edit(): void{

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
