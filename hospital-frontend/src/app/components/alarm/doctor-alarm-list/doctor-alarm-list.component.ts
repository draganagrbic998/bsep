import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { DoctorAlarm } from 'src/app/models/doctor-alarm';
import { Pagination } from 'src/app/models/pagination';
import { DoctorAlarmFormComponent } from '../doctor-alarm-form/doctor-alarm-form.component';
import { AlarmService } from 'src/app/services/alarm.service';
import { Page } from 'src/app/models/page';
import { DeleteData } from 'src/app/models/delete-data';
import { DeleteConfirmationComponent } from '../../common/delete-confirmation/delete-confirmation.component';

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
  pending = true;
  pagination: Pagination = {
    pageNumber: 0,
    firstPage: true,
    lastPage: true
  };

  addAlarm(): void{
    this.dialog.open(DoctorAlarmFormComponent, {...DIALOG_OPTIONS, ...{data: this.patientId}});
  }

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchAlarms();
  }

  fetchAlarms(): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.alarmService.findAllDoctor(this.patientId, this.pagination.pageNumber).subscribe(
      (page: Page<DoctorAlarm>) => {
        this.pending = false;
        this.alarms = page.content;
        this.pagination.firstPage = page.first;
        this.pagination.lastPage = page.last;
      }
    );
  }

  delete(id: number): void{
    const deleteData: DeleteData = {
      deleteFunction: () => this.alarmService.delete(id),
      refreshFunction: () => this.alarmService.announceRefreshDoctorData()
    };
    this.dialog.open(DeleteConfirmationComponent, {...DIALOG_OPTIONS, ...{data: deleteData}});
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
