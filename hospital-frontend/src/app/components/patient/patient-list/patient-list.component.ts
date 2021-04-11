import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { DeleteConfirmationComponent } from 'src/app/components/common/delete-confirmation/delete-confirmation.component';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { Page } from 'src/app/models/page';
import { Pagination } from 'src/app/models/pagination';
import { Patient } from 'src/app/models/patient';
import { PatientService } from 'src/app/services/patient.service';
import { PatientFormComponent } from '../patient-form/patient-form.component';
import { PatientDetailsComponent } from '../patient-details/patient-details.component';
import { DeleteData } from 'src/app/models/delete-data';

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
  pending = true;
  pagination: Pagination = {
    pageNumber: 0,
    firstPage: true,
    lastPage: true
  };
  search = '';

  edit(patient: Patient): void{
    this.dialog.open(PatientFormComponent, {...DIALOG_OPTIONS, ...{data: patient}});
  }

  delete(id: number): void{
    const deleteData: DeleteData = {
      deleteFunction: () => this.patientService.delete(id),
      refreshFunction: () => this.patientService.announceRefreshData()
    };
    this.dialog.open(DeleteConfirmationComponent, {...DIALOG_OPTIONS, ...{data: deleteData}});
  }

  details(patient: Patient): void{
    this.dialog.open(PatientDetailsComponent, {...DIALOG_OPTIONS, ...{data: patient}, ...{disableClose: false}});
  }

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchPatients();
  }

  fetchPatients(): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.patientService.findAll(this.pagination.pageNumber, this.search).subscribe(
      (page: Page<Patient>) => {
        this.pending = false;
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
