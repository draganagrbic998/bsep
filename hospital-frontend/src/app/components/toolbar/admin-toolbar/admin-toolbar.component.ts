import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { StorageService } from 'src/app/services/storage.service';
import { environment } from 'src/environments/environment';
import { AdminAlarmFormComponent } from '../../alarm/admin-alarm-form/admin-alarm-form.component';
import { AdminAlarmListComponent } from '../../alarm/admin-alarm-list/admin-alarm-list.component';
import { CertificateFormComponent } from '../../certificate/certificate-form/certificate-form.component';
import { RevokeFormComponent } from '../../certificate/revoke-form/revoke-form.component';

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
    this.router.navigate([environment.loginFormRoute]);
  }

  openAlarmForm(): void{
    this.dialog.open(AdminAlarmFormComponent, DIALOG_OPTIONS);
  }

  openAlarmList(): void{
    this.dialog.open(AdminAlarmListComponent, {...DIALOG_OPTIONS,
      ...{width: '500px', height: '550px', disableClose: false}});
  }

  openCertificateForm(): void{
    this.dialog.open(CertificateFormComponent, DIALOG_OPTIONS);
  }

  openRevokeForm(): void{
    this.dialog.open(RevokeFormComponent, DIALOG_OPTIONS);
  }

  ngOnInit(): void {
  }

}
