import { Component, OnInit } from '@angular/core';
import { AdminAlarm } from 'src/app/models/admin-alarm';
import { AlarmService } from 'src/app/services/alarm.service';
import { Page } from 'src/app/models/page';
import { DeleteData } from 'src/app/models/delete-data';
import { MatDialog } from '@angular/material/dialog';
import { DeleteComponent } from '../../common/delete/delete.component';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { EMPTY_PAGE } from 'src/app/utils/constants';

@Component({
  selector: 'app-admin-alarms',
  templateUrl: './admin-alarms.component.html',
  styleUrls: ['./admin-alarms.component.scss']
})
export class AdminAlarmsComponent implements OnInit {

  constructor(
    private alarmService: AlarmService,
    private dialog: MatDialog
  ) { }

  page: Page<AdminAlarm> = EMPTY_PAGE;
  pending = true;

  fetchAlarms(pageNumber: number): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.alarmService.findAllAdmin(pageNumber).subscribe(
      (page: Page<AdminAlarm>) => {
        this.pending = false;
        this.page = page;
      }
    );
  }

  delete(id: number): void{
    const deleteData: DeleteData = {
      deleteFunction: () => this.alarmService.delete(id),
      refreshFunction: () => this.alarmService.announceRefreshAdminData()
    };
    this.dialog.open(DeleteComponent, {...DIALOG_OPTIONS, ...{data: deleteData}});
  }

  ngOnInit(): void {
    this.fetchAlarms(0);
    // tslint:disable-next-line: deprecation
    this.alarmService.refreshAdminData$.subscribe(() => this.fetchAlarms(0));
  }

}
