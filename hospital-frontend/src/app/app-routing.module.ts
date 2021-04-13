import { NgModule } from '@angular/core';

import { Routes, RouterModule } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AuthGuard } from './utils/auth.guard';
import { ADMIN, DOCTOR } from './utils/constants';

import { AlarmTriggeringListComponent } from './components/alarm/alarm-triggering-list/alarm-triggering-list.component';
import { LogListComponent } from './components/log/log-list/log-list.component';
import { MessageListComponent } from './components/message/message-list/message-list.component';
import { PatientListComponent } from './components/patient/patient-list/patient-list.component';
import { ReportComponent } from './components/common/report/report.component';
import { LoginFormComponent } from './components/common/login-form/login-form.component';

const routes: Routes = [
  {
    path: environment.loginFormRoute,
    component: LoginFormComponent,
  },
  {
    path: environment.patientListRoute,
    component: PatientListComponent,
    canActivate: [AuthGuard],
    data: {authorities: [DOCTOR]}
  },
  {
    path: environment.messageListRoute,
    component: MessageListComponent,
    canActivate: [AuthGuard],
    data: {authorities: [DOCTOR]}
  },
  {
    path: environment.alarmListRoute,
    component: AlarmTriggeringListComponent,
    canActivate: [AuthGuard],
    data: {authorities: [DOCTOR, ADMIN]}
  },
  {
    path: environment.logListRoute,
    component: LogListComponent,
    canActivate: [AuthGuard],
    data: {authorities: [ADMIN]}
  },
  {
    path: environment.reportRoute,
    component: ReportComponent,
    canActivate: [AuthGuard],
    data: {authorities: [ADMIN]}
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: environment.loginFormRoute
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
