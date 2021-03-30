import { Component, OnInit } from '@angular/core';
import { AlarmTriggering } from 'src/app/models/alarm-triggering';
import { Page } from 'src/app/models/page';
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
      (page: Page<AlarmTriggering>) => {
        this.fetchPending = false;
        this.alarmTriggerings = page.content;
        this.pagination.firstPage = page.first;
        this.pagination.lastPage = page.last;
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
  }

}
