import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DeleteConfirmationComponent } from 'src/app/components/common/delete-confirmation/delete-confirmation.component';
import { AdminAlarm } from 'src/app/models/admin-alarm';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { AlarmService } from 'src/app/services/alarm.service';
import { DeleteData } from 'src/app/models/delete-data';

@Component({
  selector: 'app-admin-alarm-details',
  templateUrl: './admin-alarm-details.component.html',
  styleUrls: ['./admin-alarm-details.component.scss']
})
export class AdminAlarmDetailsComponent implements OnInit {

  constructor(
    private alarmService: AlarmService,
    private dialog: MatDialog
  ) { }

  @Input() alarm: AdminAlarm = {} as AdminAlarm;
  @Input() index = 0;

  delete(): void{
    const deleteData: DeleteData = {
      deleteFunction: () => this.alarmService.delete(this.alarm.id),
      refreshFunction: () => this.alarmService.announceRefreshAdminData()
    };
    this.dialog.open(DeleteConfirmationComponent, {...DIALOG_OPTIONS, ...{data: deleteData}});
  }

  ngOnInit(): void {
  }

}
