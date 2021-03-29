import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DIALOG_OPTIONS } from 'src/app/constants/dialog';
import { StorageService } from 'src/app/services/storage/storage.service';
import { environment } from 'src/environments/environment';
import { AdminAlarmDialogComponent } from '../../admin-alarm/admin-alarm-dialog/admin-alarm-dialog.component';
import { AdminAlarmListComponent } from '../../admin-alarm/admin-alarm-list/admin-alarm-list.component';

@Component({
  selector: 'app-admin-toolbar',
  templateUrl: './admin-toolbar.component.html',
  styleUrls: ['./admin-toolbar.component.scss']
})
export class AdminToolbarComponent implements OnInit {

  constructor(
    private storageService: StorageService,
    private router: Router,
    private dialog: MatDialog
  ) { }

  onRoute(param: string): boolean{
    return this.router.url.substr(1).includes(param);
  }

  signOut(): void{
    this.storageService.removeUser();
    this.router.navigate([environment.loginRoute]);
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
