import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdminAlarm } from 'src/app/models/admin-alarm';
import { Pagination } from 'src/app/models/pagination';
import { FIRST_PAGE_HEADER, LAST_PAGE_HEADER } from 'src/app/constants/pagination';
import { AlarmService } from 'src/app/services/alarm/alarm.service';

@Component({
  selector: 'app-admin-alarm-list',
  templateUrl: './admin-alarm-list.component.html',
  styleUrls: ['./admin-alarm-list.component.scss']
})
export class AdminAlarmListComponent implements OnInit {

  constructor(
    private alarmService: AlarmService,
    public dialogRef: MatDialogRef<AdminAlarmListComponent>
  ) { }

  alarms: AdminAlarm[] = [];
  fetchPending = true;
  pagination: Pagination = {
    pageNumber: 0,
    firstPage: true,
    lastPage: true
  };

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchAlarms();
  }

  fetchAlarms(): void{
    this.fetchPending = true;
    // tslint:disable-next-line: deprecation
    this.alarmService.findAllAdmin(this.pagination.pageNumber).subscribe(
      (data: HttpResponse<AdminAlarm[]>) => {
        this.fetchPending = false;
        if (data){
          this.alarms = data.body;
          const headers: HttpHeaders = data.headers;
          this.pagination.firstPage = headers.get(FIRST_PAGE_HEADER) === 'false' ? false : true;
          this.pagination.lastPage = headers.get(LAST_PAGE_HEADER) === 'false' ? false : true;
        }
        else{
          this.alarms = [];
          this.pagination.firstPage = true;
          this.pagination.lastPage = true;
        }
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
    // tslint:disable-next-line: deprecation
    this.alarmService.refreshAdminData$.subscribe(() => {
      this.pagination.pageNumber = 0;
      this.changePage(0);
    });
  }

}