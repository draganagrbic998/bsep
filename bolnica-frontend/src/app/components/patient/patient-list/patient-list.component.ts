import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FIRST_PAGE_HEADER, LAST_PAGE_HEADER } from 'src/app/constants/pagination';
import { Pagination } from 'src/app/models/pagination';
import { Patient } from 'src/app/models/patient';
import { PatientService } from 'src/app/services/patient/patient.service';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent implements OnInit {

  constructor(
    private patientService: PatientService
  ) { }

  patients: Patient[] = [];
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
      (data: HttpResponse<Patient[]>) => {
        this.fetchPending = false;
        if (data){
          this.patients = data.body;
          const headers: HttpHeaders = data.headers;
          this.pagination.firstPage = headers.get(FIRST_PAGE_HEADER) === 'false' ? false : true;
          this.pagination.lastPage = headers.get(LAST_PAGE_HEADER) === 'false' ? false : true;
        }
        else{
          this.patients = [];
          this.pagination.firstPage = true;
          this.pagination.lastPage = true;
        }
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
    // tslint:disable-next-line: deprecation
    this.patientService.refreshData$.subscribe(() => {
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
