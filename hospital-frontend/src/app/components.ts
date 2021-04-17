import { LogsComponent } from './components/log-message/logs/logs.component';
import { ToolbarComponent } from './components/common/toolbar/toolbar.component';
import { AdminAlarmsComponent } from './components/alarm/admin-alarms/admin-alarms.component';
import { AdminAlarmFormComponent } from './components/alarm/admin-alarm-form/admin-alarm-form.component';
import { ReportComponent } from './components/log-message/report/report.component';
import { LoginComponent } from './components/common/login/login.component';
import { RequestComponent } from './components/certificate/request/request.component';
import { DetailsComponent } from './components/containers/details/details.component';
import { DialogComponent } from './components/containers/dialog/dialog.component';
import { ListComponent } from './components/containers/list/list.component';
import { EmptyComponent } from './components/containers/empty/empty.component';
import { PaginatorComponent } from './components/common/paginator/paginator.component';
import { DeleteComponent } from './components/common/delete/delete.component';
import { MessagesComponent } from './components/log-message/messages/messages.component';
import { DoctorAlarmsComponent } from './components/alarm/doctor-alarms/doctor-alarms.component';
import { DoctorAlarmFormComponent } from './components/alarm/doctor-alarm-form/doctor-alarm-form.component';
import { AlarmTriggeringsComponent } from './components/alarm/alarm-triggerings/alarm-triggerings.component';
import { PatientFormComponent } from './components/patient/patient-form/patient-form.component';
import { FormComponent } from './components/containers/form/form.component';
import { SpacerComponent } from './components/containers/spacer/spacer.component';
import { PreloaderComponent } from './components/loaders/preloader/preloader.component';
import { SpinnerComponent } from './components/loaders/spinner/spinner.component';
import { PatientComponent } from './components/patient/patient/patient.component';
import { PatientsComponent } from './components/patient/patients/patients.component';
import { RevokeComponent } from './components/certificate/revoke/revoke.component';

export const COMPONENTS = [
    PatientFormComponent,
    FormComponent,
    SpacerComponent,
    PreloaderComponent,
    SpinnerComponent,
    PatientComponent,
    PatientsComponent,
    EmptyComponent,
    PaginatorComponent,
    DeleteComponent,
    MessagesComponent,
    DoctorAlarmsComponent,
    DoctorAlarmFormComponent,
    AlarmTriggeringsComponent,
    LogsComponent,
    ToolbarComponent,
    AdminAlarmsComponent,
    AdminAlarmFormComponent,
    ReportComponent,
    LoginComponent,
    RequestComponent,
    DetailsComponent,
    DialogComponent,
    ListComponent,
    RevokeComponent
];
