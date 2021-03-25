import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DIALOG_OPTIONS } from 'src/app/constants/dialog';
import { FIRST_PAGE_HEADER, LAST_PAGE_HEADER } from 'src/app/constants/pagination';
import { Alarm } from 'src/app/models/alarm';
import { Pagination } from 'src/app/models/pagination';
import { AlarmService } from 'src/app/services/alarm/alarm.service';
import { AlarmDialogComponent } from '../alarm-dialog/alarm-dialog.component';

@Component({
  selector: 'app-alarm-list',
  templateUrl: './alarm-list.component.html',
  styleUrls: ['./alarm-list.component.scss']
})
export class AlarmListComponent implements OnInit {

  constructor(
    private alarmService: AlarmService,
    private dialog: MatDialog
  ) { }

  @Input() patientId: number;
  alarms: Alarm[] = [];
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
    this.alarmService.findAll(this.patientId, this.pagination.pageNumber).subscribe(
      (data: HttpResponse<Alarm[]>) => {
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

  addAlarm(): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: this.patientId}};
    this.dialog.open(AlarmDialogComponent, options);
  }

  ngOnInit(): void {
    this.changePage(0);
    // tslint:disable-next-line: deprecation
    this.alarmService.refreshData$.subscribe(() => {
      this.changePage(0);
    });
  }

}
