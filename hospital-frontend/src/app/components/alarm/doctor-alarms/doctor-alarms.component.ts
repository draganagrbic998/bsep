import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { DoctorAlarm } from 'src/app/models/doctor-alarm';
import { DoctorAlarmFormComponent } from '../doctor-alarm-form/doctor-alarm-form.component';
import { AlarmService } from 'src/app/services/alarm.service';
import { Page } from 'src/app/models/page';
import { DeleteData } from 'src/app/models/delete-data';
import { DeleteComponent } from '../../common/delete/delete.component';
import { EMPTY_PAGE } from 'src/app/utils/constants';

@Component({
  selector: 'app-alarms',
  templateUrl: './doctor-alarms.component.html',
  styleUrls: ['./doctor-alarms.component.scss']
})
export class DoctorAlarmsComponent implements OnInit {

  constructor(
    private alarmService: AlarmService,
    private dialog: MatDialog
  ) { }

  @Input() private patientId!: number;
  page: Page<DoctorAlarm> = EMPTY_PAGE;
  pending = true;

  fetchAlarms(pageNumber: number): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.alarmService.findAllDoctor(this.patientId, pageNumber).subscribe(
      (page: Page<DoctorAlarm>) => {
        this.pending = false;
        this.page = page;
      }
    );
  }

  addAlarm(): void{
    this.dialog.open(DoctorAlarmFormComponent, {...DIALOG_OPTIONS, ...{data: this.patientId}});
  }

  delete(id: number): void{
    const deleteData: DeleteData = {
      deleteFunction: () => this.alarmService.delete(id),
      refreshFunction: () => this.alarmService.announceRefreshDoctorData()
    };
    this.dialog.open(DeleteComponent, {...DIALOG_OPTIONS, ...{data: deleteData}});
  }

  ngOnInit(): void {
    this.fetchAlarms(0);
    // tslint:disable-next-line: deprecation
    this.alarmService.refreshDoctorData$.subscribe(() => this.fetchAlarms(0));
  }

}
