import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { DeleteConfirmationComponent } from 'src/app/components/common/delete-confirmation/delete-confirmation.component';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { Page } from 'src/app/models/page';
import { Pagination } from 'src/app/models/pagination';
import { Patient } from 'src/app/models/patient';
import { PatientService } from 'src/app/services/patient.service';
import { PatientFormComponent } from '../patient-form/patient-form.component';
import { PatientDetailsComponent } from '../patient-details/patient-details.component';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent implements OnInit {

  constructor(
    private patientService: PatientService,
    private dialog: MatDialog
  ) { }

  columns: string[] = ['firstName', 'lastName', 'birthDate', 'address', 'city', 'actions'];
  patients: MatTableDataSource<Patient> = new MatTableDataSource([]);
  fetchPending = true;
  pagination: Pagination = {
    pageNumber: 0,
    firstPage: true,
    lastPage: true
  };
  search = '';

  edit(patient: Patient): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: patient}};
    this.dialog.open(PatientFormComponent, options);
  }

  delete(id: number): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: () => this.patientService.delete(id)}};
    // tslint:disable-next-line: deprecation
    this.dialog.open(DeleteConfirmationComponent, options).afterClosed().subscribe(result => {
      if (result){
        this.patientService.announceRefreshData();
      }
    });
  }

  details(patient: Patient): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: patient}, ...{disableClose: false}};
    this.dialog.open(PatientDetailsComponent, options);
  }

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchPatients();
  }

  fetchPatients(): void{
    this.fetchPending = true;
    // tslint:disable-next-line: deprecation
    this.patientService.findAll(this.pagination.pageNumber, this.search).subscribe(
      (page: Page<Patient>) => {
        this.fetchPending = false;
        this.patients = new MatTableDataSource(page.content);
        this.pagination.firstPage = page.first;
        this.pagination.lastPage = page.last;
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
    // tslint:disable-next-line: deprecation
    this.patientService.refreshData$.subscribe(() => {
      this.pagination.pageNumber = this.pagination.pageNumber ? this.pagination.pageNumber - 1 : 0;
      this.changePage(0);
    });
    // tslint:disable-next-line: deprecation
    this.patientService.searchData$.subscribe((search: string) => {
      this.pagination.pageNumber = 0;
      this.search = search;
      this.changePage(0);
    });
  }

}
