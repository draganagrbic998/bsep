import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { DeleteConfirmationComponent } from 'src/app/components/controls/delete-confirmation/delete-confirmation.component';
import { DIALOG_OPTIONS } from 'src/app/constants/dialog';
import { Page } from 'src/app/models/page';
import { Pagination } from 'src/app/models/pagination';
import { Patient } from 'src/app/models/patient';
import { PatientService } from 'src/app/services/patient/patient.service';
import { StorageService } from 'src/app/services/storage/storage.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent implements OnInit {

  constructor(
    private storageService: StorageService,
    private patientService: PatientService,
    private dialog: MatDialog,
    private router: Router
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

  edit(patient: Patient): void{
    this.storageService.set(this.storageService.PATIENT_KEY, patient);
    this.router.navigate([`${environment.patientFormRoute}/edit`]);
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

  openDetails(patient: Patient): void{
    this.storageService.set(this.storageService.PATIENT_KEY, patient);
    this.router.navigate([environment.patientDetailsRoute]);
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
