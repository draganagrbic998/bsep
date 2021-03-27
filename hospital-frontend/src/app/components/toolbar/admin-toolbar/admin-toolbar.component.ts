import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DIALOG_OPTIONS } from 'src/app/core/utils/dialog';
import { AdminAlarmDialogComponent } from '../../admin-alarm/admin-alarm-dialog/admin-alarm-dialog.component';
import { AdminAlarmListComponent } from '../../admin-alarm/admin-alarm-list/admin-alarm-list.component';

@Component({
  selector: 'app-admin-toolbar',
  templateUrl: './admin-toolbar.component.html',
  styleUrls: ['./admin-toolbar.component.scss']
})
export class AdminToolbarComponent implements OnInit {

  constructor(
    private router: Router,
    private dialog: MatDialog
  ) { }

  // dodaj ngIf kod toolbar buttona

  onRoute(param: string): boolean{
    return this.router.url.substr(1).includes(param);
  }

  signOut(): void{

  }

  addAlarm(): void{
    this.dialog.open(AdminAlarmDialogComponent, DIALOG_OPTIONS);
  }

  listAlarms(): void{
    const options: MatDialogConfig = {...DIALOG_OPTIONS, ...{width: '500px', height: '550px'}};
    this.dialog.open(AdminAlarmListComponent, options);
  }

  ngOnInit(): void {
  }

}
