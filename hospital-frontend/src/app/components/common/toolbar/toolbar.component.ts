import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DIALOG_OPTIONS } from 'src/app/utils/dialog';
import { StorageService } from 'src/app/services/storage.service';
import { environment } from 'src/environments/environment';
import { AdminAlarmComponent } from '../../alarm/admin-alarm/admin-alarm.component';
import { AdminAlarmsComponent } from '../../alarm/admin-alarms/admin-alarms.component';
import { RequestComponent } from '../../certificate/request/request.component';
import { RevokeComponent } from '../../certificate/revoke/revoke.component';
import { USER_ROLE } from 'src/app/utils/constants';
import { PatientFormComponent } from '../../patient/patient-form/patient-form.component';
import { FormControl } from '@angular/forms';
import { PatientService } from 'src/app/services/patient.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent {

  constructor(
    private storageService: StorageService,
    private patientService: PatientService,
    private router: Router,
    private dialog: MatDialog
  ) { }

  routes = environment;
  search: FormControl = new FormControl('');

  get isAdmin(): boolean{
    return this.storageService.getToken()?.roles.includes(USER_ROLE.ADMIN);
  }

  get isDoctor(): boolean{
    return this.storageService.getToken()?.roles.includes(USER_ROLE.DOCTOR);
  }

  onRoute(param: string): boolean{
    return this.router.url.substr(1) === param;
  }

  signOut(): void{
    this.storageService.removeToken();
    this.router.navigate([environment.loginRoute]);
  }

  openPatientForm(): void{
    this.dialog.open(PatientFormComponent, {...DIALOG_OPTIONS, ...{data: {}}});
  }

  openAlarmForm(): void{
    this.dialog.open(AdminAlarmComponent, DIALOG_OPTIONS);
  }

  openAlarmList(): void{
    this.dialog.open(AdminAlarmsComponent, {...DIALOG_OPTIONS,
      ...{width: '500px', height: '520px', disableClose: false}});
  }

  openRequestForm(): void{
    this.dialog.open(RequestComponent, DIALOG_OPTIONS);
  }

  openRevokeForm(): void{
    this.dialog.open(RevokeComponent, DIALOG_OPTIONS);
  }

  announceSearchData(): void{
    this.patientService.announceSearchData(this.search.value.trim());
  }

}
