import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DIALOG_OPTIONS } from 'src/app/constants/dialog';
import { DoctorAlarm } from 'src/app/models/doctor-alarm';
import { Pagination } from 'src/app/models/pagination';
import { DoctorAlarmDialogComponent } from '../doctor-alarm-dialog/doctor-alarm-dialog.component';
import { AlarmService } from 'src/app/services/alarm/alarm.service';
import { Page } from 'src/app/models/page';

@Component({
  selector: 'app-alarm-list',
  templateUrl: './doctor-alarm-list.component.html',
  styleUrls: ['./doctor-alarm-list.component.scss']
})
export class DoctorAlarmListComponent implements OnInit {

  constructor(
    private alarmService: AlarmService,
    private dialog: MatDialog
  ) { }

  @Input() patientId: number;
  alarms: DoctorAlarm[] = [];
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
    this.alarmService.findAllDoctor(this.patientId, this.pagination.pageNumber).subscribe(
      (page: Page<DoctorAlarm>) => {
        this.fetchPending = false;
        this.alarms = page.content;
        this.pagination.firstPage = page.first;
        this.pagination.lastPage = page.last;
      }
    );
  }

  addAlarm(): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: this.patientId}};
    this.dialog.open(DoctorAlarmDialogComponent, options);
  }

  ngOnInit(): void {
    this.changePage(0);
    // tslint:disable-next-line: deprecation
    this.alarmService.refreshDoctorData$.subscribe(() => {
      this.pagination.pageNumber = this.pagination.pageNumber ? this.pagination.pageNumber - 1 : 0;
      this.changePage(0);
    });
  }

}
