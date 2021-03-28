import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PatientFormComponent } from './components/patient/patient-form/patient-form.component';
import { FormContainerComponent } from './core/containers/form-container/form-container.component';
import { SpacerContainerComponent } from './core/containers/spacer-container/spacer-container.component';
import { PreloaderComponent } from './core/loaders/preloader/preloader.component';
import { SpinnerButtonComponent } from './core/loaders/spinner-button/spinner-button.component';
import { PatientDetailsComponent } from './components/patient/patient-details/patient-details.component';
import { PatientListComponent } from './components/patient/patient-list/patient-list.component';
import { BoldTextComponent } from './core/containers/bold-text/bold-text.component';
import { BlodTypePipe } from './core/utils/blod-type.pipe';
import { EmptyContainerComponent } from './core/containers/empty-container/empty-container.component';
import { PaginatorComponent } from './core/controls/paginator/paginator.component';
import { DoctorToolbarComponent } from './components/toolbar/doctor-toolbar/doctor-toolbar.component';
import { DeleteConfirmationComponent } from './core/controls/delete-confirmation/delete-confirmation.component';
import { MessageDetailsComponent } from './components/message/message-details/message-details.component';
import { MessageListComponent } from './components/message/message-list/message-list.component';
import { DoctorAlarmDetailsComponent } from './components/doctor-alarm/doctor-alarm-details/doctor-alarm-details.component';
import { DoctorAlarmListComponent } from './components/doctor-alarm/doctor-alarm-list/doctor-alarm-list.component';
import { SearchFormComponent } from './core/controls/search-form/search-form.component';
import { DoctorAlarmDialogComponent } from './components/doctor-alarm/doctor-alarm-dialog/doctor-alarm-dialog.component';
import { AlarmTriggeringDetailsComponent } from './components/alarm-triggering/alarm-triggering-details/alarm-triggering-details.component';
import { AlarmTriggeringListComponent } from './components/alarm-triggering/alarm-triggering-list/alarm-triggering-list.component';
import { OnlyNumbersDirective } from './core/utils/only-numbers.directive';
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
    SearchFormComponent,
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
    ReportComponent
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
