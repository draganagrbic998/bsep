import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AlarmTriggeringListComponent } from './components/alarm-triggering/alarm-triggering-list/alarm-triggering-list.component';
import { LogListComponent } from './components/log/log-list/log-list.component';
import { MessageListComponent } from './components/message/message-list/message-list.component';
import { PatientDetailsComponent } from './components/patient/patient-details/patient-details.component';
import { PatientFormComponent } from './components/patient/patient-form/patient-form.component';
import { PatientListComponent } from './components/patient/patient-list/patient-list.component';

const routes: Routes = [
  {
    path: `${environment.patientFormRoute}/:mode`,
    component: PatientFormComponent,
    // dodaj guard
  },
  {
    path: environment.patientListRoute,
    component: PatientListComponent,
    // dodaj guard
  },
  {
    path: environment.messageListRoute,
    component: MessageListComponent,
    // dodaj guard
  },
  {
    path: environment.alarmTriggeringListRoute,
    component: AlarmTriggeringListComponent,
    // dodaj guard
  },
  {
    path: environment.patientDetailsRoute,
    component: PatientDetailsComponent,
    // dodaj guard
  },
  {
    path: environment.logListRoute,
    component: LogListComponent,
    // dodaj guard
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
