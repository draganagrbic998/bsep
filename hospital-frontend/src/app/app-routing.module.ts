import { NgModule } from '@angular/core';

import { Routes, RouterModule } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AuthGuard } from './utils/auth.guard';
import { USER_ROLE } from './utils/constants';

import { AlarmTriggeringsComponent } from './components/alarm/alarm-triggerings/alarm-triggerings.component';
import { LogsComponent } from './components/log-message/logs/logs.component';
import { MessagesComponent } from './components/log-message/messages/messages.component';
import { PatientsComponent } from './components/patient/patients/patients.component';
import { ReportComponent } from './components/log-message/report/report.component';
import { LoginComponent } from './components/common/login/login.component';
import { FrameComponent } from './components/common/frame/frame.component';

const routes: Routes = [
  {
    path: environment.loginRoute,
    component: LoginComponent,
    canActivate: [AuthGuard],
    data: {unauthorized: true}
  },
  {
    path: environment.reportRoute,
    component: ReportComponent,
    canActivate: [AuthGuard],
    data: {roles: [USER_ROLE.ADMIN]}
  },
  {
    path: environment.patientsRoute,
    component: PatientsComponent,
    canActivate: [AuthGuard],
    data: {roles: [USER_ROLE.DOCTOR]}
  },
  {
    path: environment.messagesRoute,
    component: MessagesComponent,
    canActivate: [AuthGuard],
    data: {roles: [USER_ROLE.DOCTOR]}
  },
  {
    path: environment.logsRoute,
    component: LogsComponent,
    canActivate: [AuthGuard],
    data: {roles: [USER_ROLE.ADMIN]}
  },
  {
    path: environment.alarmsRoute,
    component: AlarmTriggeringsComponent,
    canActivate: [AuthGuard],
    data: {roles: [USER_ROLE.DOCTOR, USER_ROLE.ADMIN]}
  },
  {
    path: environment.frameRoute,
    component: FrameComponent
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: environment.loginRoute
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
