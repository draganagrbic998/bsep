import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DeleteConfirmationComponent } from 'src/app/core/controls/delete-confirmation/delete-confirmation.component';
import { DIALOG_OPTIONS } from 'src/app/core/utils/dialog';
import { DoctorAlarm } from 'src/app/core/models/doctor-alarm';
import { DoctorAlarmService } from 'src/app/services/doctor-alarm/doctor-alarm.service';

@Component({
  selector: 'app-doctor-alarm-details',
  templateUrl: './doctor-alarm-details.component.html',
  styleUrls: ['./doctor-alarm-details.component.scss']
})
export class DoctorAlarmDetailsComponent implements OnInit {

  constructor(
    private alarmService: DoctorAlarmService,
    private dialog: MatDialog
  ) { }

  @Input() alarm: DoctorAlarm = {} as DoctorAlarm;
  @Input() index = 0;

  notEmpty(field: number): boolean{
    return field !== undefined && field !== null;
  }

  delete(): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: () => this.alarmService.delete(this.alarm.id)}};
    // tslint:disable-next-line: deprecation
    this.dialog.open(DeleteConfirmationComponent, options).afterClosed().subscribe(result => {
      if (result){
        this.alarmService.announceRefreshData();
      }
    });
  }

  ngOnInit(): void {
  }

}
