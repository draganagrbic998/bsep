import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AlarmTriggeringListComponent } from './components/alarm-triggering/alarm-triggering-list/alarm-triggering-list.component';
import { LogListComponent } from './components/log/log-list/log-list.component';
import { MessageListComponent } from './components/message/message-list/message-list.component';
import { PatientDetailsComponent } from './components/patient/patient-details/patient-details.component';
import { PatientFormComponent } from './components/patient/patient-form/patient-form.component';
import { PatientListComponent } from './components/patient/patient-list/patient-list.component';
import { ReportComponent } from './components/report/report.component';
import { LoginFormComponent } from './components/user/login-form/login-form.component';
import { ADMIN, DOCTOR } from './constants/roles';
import { AuthGuard } from './guard/auth.guard';

const routes: Routes = [
  {
    path: environment.loginRoute,
    component: LoginFormComponent,
  },
  {
    path: `${environment.patientFormRoute}/:mode`,
    component: PatientFormComponent,
    canActivate: [AuthGuard],
    data: {roles: [DOCTOR]}
  },
  {
    path: environment.patientListRoute,
    component: PatientListComponent,
    canActivate: [AuthGuard],
    data: {roles: [DOCTOR]}
  },
  {
    path: environment.patientDetailsRoute,
    component: PatientDetailsComponent,
    canActivate: [AuthGuard],
    data: {roles: [DOCTOR]}
  },
  {
    path: environment.messageListRoute,
    component: MessageListComponent,
    canActivate: [AuthGuard],
    data: {roles: [DOCTOR]}
  },
  {
    path: environment.logListRoute,
    component: LogListComponent,
    canActivate: [AuthGuard],
    data: {roles: [ADMIN]}
  },
  {
    path: environment.alarmTriggeringListRoute,
    component: AlarmTriggeringListComponent,
    canActivate: [AuthGuard],
    data: {roles: [DOCTOR, ADMIN]}
  },
  {
    path: environment.reportRoute,
    component: ReportComponent,
    canActivate: [AuthGuard],
    data: {roles: [ADMIN]}
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