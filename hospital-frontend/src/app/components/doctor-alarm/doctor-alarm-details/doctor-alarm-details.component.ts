import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DeleteConfirmationComponent } from 'src/app/components/common/delete-confirmation/delete-confirmation.component';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { DoctorAlarm } from 'src/app/models/doctor-alarm';
import { AlarmService } from 'src/app/services/alarm.service';
import { DeleteData } from 'src/app/models/delete-data';

@Component({
  selector: 'app-doctor-alarm-details',
  templateUrl: './doctor-alarm-details.component.html',
  styleUrls: ['./doctor-alarm-details.component.scss']
})
export class DoctorAlarmDetailsComponent implements OnInit {

  constructor(
    private alarmService: AlarmService,
    private dialog: MatDialog
  ) { }

  @Input() alarm: DoctorAlarm = {} as DoctorAlarm;
  @Input() index = 0;

  delete(): void{
    const deleteData: DeleteData = {
      deleteFunction: () => this.alarmService.delete(this.alarm.id),
      refreshFunction: () => this.alarmService.announceRefreshDoctorData()
    };
    this.dialog.open(DeleteConfirmationComponent, {...DIALOG_OPTIONS, ...{data: deleteData}});
  }

  ngOnInit(): void {
  }

}
