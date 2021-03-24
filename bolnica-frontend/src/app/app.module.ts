import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PatientFormComponent } from './components/patient/patient-form/patient-form.component';
import { FormContainerComponent } from './components/shared/containers/form-container/form-container.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatCardModule } from '@angular/material/card';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SpacerContainerComponent } from './components/shared/containers/spacer-container/spacer-container.component';
import { PreloaderComponent } from './components/shared/loaders/preloader/preloader.component';
import { SpinnerButtonComponent } from './components/shared/loaders/spinner-button/spinner-button.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { PatientDetailsComponent } from './components/patient/patient-details/patient-details.component';
import { PatientListComponent } from './components/patient/patient-list/patient-list.component';
import { BoldTextComponent } from './components/shared/containers/bold-text/bold-text.component';
import { BlodTypePipe } from './components/patient/patient-details/blod-type.pipe';
import { MatIconModule } from '@angular/material/icon';
import { EmptyContainerComponent } from './components/shared/containers/empty-container/empty-container.component';
import { PaginatorComponent } from './components/shared/controls/paginator/paginator.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ToolbarComponent } from './components/shared/controls/toolbar/toolbar.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatMenuModule } from '@angular/material/menu';
import { DeleteConfirmationComponent } from './components/shared/controls/delete-confirmation/delete-confirmation.component';
import { MessageDetailsComponent } from './components/message/message-details/message-details.component';
import { MessageListComponent } from './components/message/message-list/message-list.component';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { AlarmDetailsComponent } from './components/alarm/alarm-details/alarm-details.component';
import { AlarmListComponent } from './components/alarm/alarm-list/alarm-list.component';
import { SearchFormComponent } from './components/shared/controls/search-form/search-form.component';
import { AlarmDialogComponent } from './components/alarm/alarm-dialog/alarm-dialog.component';

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
    AlarmDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    NgbModule,
    MatCardModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatToolbarModule,
    MatTooltipModule,
    MatMenuModule,
    ScrollingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
