import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DeleteConfirmationComponent } from 'src/app/components/controls/delete-confirmation/delete-confirmation.component';
import { AdminAlarm } from 'src/app/models/admin-alarm';
import { DIALOG_OPTIONS } from 'src/app/constants/dialog';
import { AlarmService } from 'src/app/services/alarm/alarm.service';

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
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: () => this.alarmService.delete(this.alarm.id)}};
    // tslint:disable-next-line: deprecation
    this.dialog.open(DeleteConfirmationComponent, options).afterClosed().subscribe(result => {
      if (result){
        this.alarmService.announceRefreshAdminData();
      }
    });
  }

  ngOnInit(): void {
  }

}
