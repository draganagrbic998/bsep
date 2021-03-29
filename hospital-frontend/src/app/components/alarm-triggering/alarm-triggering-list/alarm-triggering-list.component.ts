import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FIRST_PAGE, LAST_PAGE } from 'src/app/constants/pagination';
import { AlarmTriggering } from 'src/app/models/alarm-triggering';
import { Pagination } from 'src/app/models/pagination';
import { AlarmTriggeringService } from 'src/app/services/alarm-triggering/alarm-triggering.service';

@Component({
  selector: 'app-alarm-triggering-list',
  templateUrl: './alarm-triggering-list.component.html',
  styleUrls: ['./alarm-triggering-list.component.scss']
})
export class AlarmTriggeringListComponent implements OnInit {

  constructor(
    private alarmTriggeringService: AlarmTriggeringService
  ) { }

  alarmTriggerings: AlarmTriggering[] = [];
  fetchPending = true;
  pagination: Pagination = {
    pageNumber: 0,
    firstPage: true,
    lastPage: true
  };

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchAlarmTriggerings();
  }

  fetchAlarmTriggerings(): void{
    this.fetchPending = true;
    // tslint:disable-next-line: deprecation
    this.alarmTriggeringService.findAll(this.pagination.pageNumber).subscribe(
      (data: HttpResponse<AlarmTriggering[]>) => {
        this.fetchPending = false;
        if (data){
          this.alarmTriggerings = data.body;
          const headers: HttpHeaders = data.headers;
          this.pagination.firstPage = headers.get(FIRST_PAGE) === 'false' ? false : true;
          this.pagination.lastPage = headers.get(LAST_PAGE) === 'false' ? false : true;
        }
        else{
          this.alarmTriggerings = [];
          this.pagination.firstPage = true;
          this.pagination.lastPage = true;
        }
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
  }

}
