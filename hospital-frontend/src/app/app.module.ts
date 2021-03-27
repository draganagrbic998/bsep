import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PatientFormComponent } from './components/patient/patient-form/patient-form.component';
import { FormContainerComponent } from './components/shared/containers/form-container/form-container.component';
import { SpacerContainerComponent } from './components/shared/containers/spacer-container/spacer-container.component';
import { PreloaderComponent } from './components/shared/loaders/preloader/preloader.component';
import { SpinnerButtonComponent } from './components/shared/loaders/spinner-button/spinner-button.component';
import { PatientDetailsComponent } from './components/patient/patient-details/patient-details.component';
import { PatientListComponent } from './components/patient/patient-list/patient-list.component';
import { BoldTextComponent } from './components/shared/containers/bold-text/bold-text.component';
import { BlodTypePipe } from './components/patient/patient-details/blod-type.pipe';
import { EmptyContainerComponent } from './components/shared/containers/empty-container/empty-container.component';
import { PaginatorComponent } from './components/shared/controls/paginator/paginator.component';
import { ToolbarComponent } from './components/shared/controls/toolbar/toolbar.component';
import { DeleteConfirmationComponent } from './components/shared/controls/delete-confirmation/delete-confirmation.component';
import { MessageDetailsComponent } from './components/message/message-details/message-details.component';
import { MessageListComponent } from './components/message/message-list/message-list.component';
import { AlarmDetailsComponent } from './components/alarm/alarm-details/alarm-details.component';
import { AlarmListComponent } from './components/alarm/alarm-list/alarm-list.component';
import { SearchFormComponent } from './components/shared/controls/search-form/search-form.component';
import { AlarmDialogComponent } from './components/alarm/alarm-dialog/alarm-dialog.component';
import { AlarmTriggeringDetailsComponent } from './components/alarm-triggering/alarm-triggering-details/alarm-triggering-details.component';
import { AlarmTriggeringListComponent } from './components/alarm-triggering/alarm-triggering-list/alarm-triggering-list.component';
import { OnlyNumbersDirective } from './components/alarm/alarm-dialog/only-numbers.directive';
import { CommonModule } from './common.module';
import { MaterialModule } from './material.module';

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
    ToolbarComponent,
    DeleteConfirmationComponent,
    MessageDetailsComponent,
    MessageListComponent,
    AlarmDetailsComponent,
    AlarmListComponent,
    SearchFormComponent,
    AlarmDialogComponent,
    AlarmTriggeringDetailsComponent,
    AlarmTriggeringListComponent,
    OnlyNumbersDirective
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
