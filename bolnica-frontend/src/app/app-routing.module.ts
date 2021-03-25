import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AlarmTriggeringListComponent } from './components/alarm-triggering/alarm-triggering-list/alarm-triggering-list.component';
import { MessageListComponent } from './components/message/message-list/message-list.component';
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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
