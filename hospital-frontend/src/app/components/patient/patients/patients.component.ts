import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DeleteComponent } from 'src/app/components/common/delete/delete.component';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { Page } from 'src/app/models/page';
import { Patient } from 'src/app/models/patient';
import { PatientService } from 'src/app/services/patient.service';
import { PatientFormComponent } from '../patient-form/patient-form.component';
import { PatientComponent } from '../patient/patient.component';
import { DeleteData } from 'src/app/models/delete-data';
import { MatTableDataSource } from '@angular/material/table';
import { EMPTY_PAGE } from 'src/app/utils/constants';

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.scss']
})
export class PatientsComponent implements OnInit {

  constructor(
    private patientService: PatientService,
    private dialog: MatDialog
  ) { }

  private search = '';
  page: Page<Patient> = EMPTY_PAGE;
  pending = true;
  columns: string[] = ['firstName', 'lastName', 'birthDate', 'address', 'city', 'actions'];

  get dataSource(): MatTableDataSource<Patient>{
    return new MatTableDataSource(this.page.content);
  }

  fetchPatients(pageNumber: number): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.patientService.findAll(pageNumber, this.search).subscribe(
      (page: Page<Patient>) => {
        this.pending = false;
        this.page = page;
      }
    );
  }

  details(patient: Patient): void{
    this.dialog.open(PatientComponent, {...DIALOG_OPTIONS, ...{data: patient}, ...{disableClose: false}});
  }

  edit(patient: Patient): void{
    this.dialog.open(PatientFormComponent, {...DIALOG_OPTIONS, ...{data: patient}});
  }

  delete(id: number): void{
    const deleteData: DeleteData = {
      deleteFunction: () => this.patientService.delete(id),
      refreshFunction: () => this.patientService.announceRefreshData()
    };
    this.dialog.open(DeleteComponent, {...DIALOG_OPTIONS, ...{data: deleteData}});
  }

  ngOnInit(): void {
    this.fetchPatients(0);
    // tslint:disable-next-line: deprecation
    this.patientService.refreshData$.subscribe(() => this.fetchPatients(0));
    // tslint:disable-next-line: deprecation
    this.patientService.searchData$.subscribe((search: string) => {
      this.search = search;
      this.fetchPatients(0);
    });
  }

}
