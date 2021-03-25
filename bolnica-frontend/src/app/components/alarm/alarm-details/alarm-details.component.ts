import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DIALOG_OPTIONS } from 'src/app/constants/dialog';
import { Alarm } from 'src/app/models/alarm';
import { AlarmService } from 'src/app/services/alarm/alarm.service';
import { DeleteConfirmationComponent } from '../../shared/controls/delete-confirmation/delete-confirmation.component';

@Component({
  selector: 'app-alarm-details',
  templateUrl: './alarm-details.component.html',
  styleUrls: ['./alarm-details.component.scss']
})
export class AlarmDetailsComponent implements OnInit {

  constructor(
    private alarmService: AlarmService,
    private dialog: MatDialog
  ) { }

  @Input() alarm: Alarm = {} as Alarm;
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
