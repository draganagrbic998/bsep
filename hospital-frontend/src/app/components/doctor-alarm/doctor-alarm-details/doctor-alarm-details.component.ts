import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DeleteConfirmationComponent } from 'src/app/components/controls/delete-confirmation/delete-confirmation.component';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { DoctorAlarm } from 'src/app/models/doctor-alarm';
import { AlarmService } from 'src/app/services/alarm.service';

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

  notEmpty(field: number): boolean{
    return field !== undefined && field !== null;
  }

  delete(): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{data: () => this.alarmService.delete(this.alarm.id)}};
    // tslint:disable-next-line: deprecation
    this.dialog.open(DeleteConfirmationComponent, options).afterClosed().subscribe(result => {
      if (result){
        this.alarmService.announceRefreshDoctorData();
      }
    });
  }

  ngOnInit(): void {
  }

}
