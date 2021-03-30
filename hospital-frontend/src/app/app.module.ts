import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PatientFormComponent } from './components/patient/patient-form/patient-form.component';
import { FormContainerComponent } from './components/containers/form-container/form-container.component';
import { SpacerContainerComponent } from './components/containers/spacer-container/spacer-container.component';
import { PreloaderComponent } from './components/loaders/preloader/preloader.component';
import { SpinnerButtonComponent } from './components/loaders/spinner-button/spinner-button.component';
import { PatientDetailsComponent } from './components/patient/patient-details/patient-details.component';
import { PatientListComponent } from './components/patient/patient-list/patient-list.component';
import { BoldTextComponent } from './components/containers/bold-text/bold-text.component';
import { BlodTypePipe } from './utils/blod-type.pipe';
import { EmptyContainerComponent } from './components/containers/empty-container/empty-container.component';
import { PaginatorComponent } from './components/controls/paginator/paginator.component';
import { DoctorToolbarComponent } from './components/toolbar/doctor-toolbar/doctor-toolbar.component';
import { DeleteConfirmationComponent } from './components/controls/delete-confirmation/delete-confirmation.component';
import { MessageDetailsComponent } from './components/message/message-details/message-details.component';
import { MessageListComponent } from './components/message/message-list/message-list.component';
import { DoctorAlarmDetailsComponent } from './components/doctor-alarm/doctor-alarm-details/doctor-alarm-details.component';
import { DoctorAlarmListComponent } from './components/doctor-alarm/doctor-alarm-list/doctor-alarm-list.component';
import { MessageSearchComponent } from './components/message/message-search/message-search.component';
import { DoctorAlarmDialogComponent } from './components/doctor-alarm/doctor-alarm-dialog/doctor-alarm-dialog.component';
import { AlarmTriggeringDetailsComponent } from './components/alarm-triggering/alarm-triggering-details/alarm-triggering-details.component';
import { AlarmTriggeringListComponent } from './components/alarm-triggering/alarm-triggering-list/alarm-triggering-list.component';
import { OnlyNumbersDirective } from './utils/only-numbers.directive';
import { CommonModule } from './common.module';
import { MaterialModule } from './material.module';
import { LogDetailsComponent } from './components/log/log-details/log-details.component';
import { LogListComponent } from './components/log/log-list/log-list.component';
import { AdminToolbarComponent } from './components/toolbar/admin-toolbar/admin-toolbar.component';
import { LogSearchComponent } from './components/log/log-search/log-search.component';
import { AdminAlarmDetailsComponent } from './components/admin-alarm/admin-alarm-details/admin-alarm-details.component';
import { AdminAlarmListComponent } from './components/admin-alarm/admin-alarm-list/admin-alarm-list.component';
import { AdminAlarmDialogComponent } from './components/admin-alarm/admin-alarm-dialog/admin-alarm-dialog.component';
import { ReportComponent } from './components/report/report.component';
import { LoginFormComponent } from './components/user/login-form/login-form.component';
import { CertificateFormComponent } from './components/certificate-form/certificate-form.component';

@NgModule({
  declarations: [
    AppComponent,
    PatientFormComponent,
    FormContainerComponent,
    SpacerContainerComponent,
    PreloaderComponent,
    SpinnerButtonComponent,
    PatientDetailsComponent,
    PatientListComponent,
    BoldTextComponent,
    BlodTypePipe,
    EmptyContainerComponent,
    PaginatorComponent,
    DoctorToolbarComponent,
    DeleteConfirmationComponent,
    MessageDetailsComponent,
    MessageListComponent,
    DoctorAlarmDetailsComponent,
    DoctorAlarmListComponent,
    MessageSearchComponent,
    DoctorAlarmDialogComponent,
    AlarmTriggeringDetailsComponent,
    AlarmTriggeringListComponent,
    OnlyNumbersDirective,
    LogDetailsComponent,
    LogListComponent,
    AdminToolbarComponent,
    LogSearchComponent,
    AdminAlarmDetailsComponent,
    AdminAlarmListComponent,
    AdminAlarmDialogComponent,
    ReportComponent,
    LoginFormComponent,
    CertificateFormComponent
  ],
  imports: [
    AppRoutingModule,
    CommonModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
