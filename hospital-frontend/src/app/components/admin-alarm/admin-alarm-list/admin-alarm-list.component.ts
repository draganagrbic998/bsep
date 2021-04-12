import { Component, OnInit } from '@angular/core';
import { AdminAlarm } from 'src/app/models/admin-alarm';
import { Pagination } from 'src/app/models/pagination';
import { AlarmService } from 'src/app/services/alarm.service';
import { Page } from 'src/app/models/page';

@Component({
  selector: 'app-admin-alarm-list',
  templateUrl: './admin-alarm-list.component.html',
  styleUrls: ['./admin-alarm-list.component.scss']
})
export class AdminAlarmListComponent implements OnInit {

  constructor(
    private alarmService: AlarmService
  ) { }

  alarms: AdminAlarm[] = [];
  pending = true;
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
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.alarmService.findAllAdmin(this.pagination.pageNumber).subscribe(
      (page: Page<AdminAlarm>) => {
        this.pending = false;
        this.alarms = page.content;
        this.pagination.firstPage = page.first;
        this.pagination.lastPage = page.last;
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
    // tslint:disable-next-line: deprecation
    this.alarmService.refreshAdminData$.subscribe(() => {
      this.pagination.pageNumber = this.pagination.pageNumber ? this.pagination.pageNumber - 1 : 0;
      this.changePage(0);
    });
  }

}
