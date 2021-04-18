import { NgModule } from '@angular/core';

import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { AppRoutingModule } from './app-routing.module';
import { CertificatesModule } from './components/certificates/certificates.module';
import { UsersModule } from './components/users/users.module';
import { ConfigurationModule } from './components/configuration/configuration.module';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { MainViewComponent } from './components/main-views/main-view/main-view.component';
import { DashboardComponent } from './components/main-views/dashboard/dashboard.component';

import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { JwtInterceptor } from './core/interceptors/jwt.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainViewComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    CardModule,
    MenuModule,
    MenubarModule,
    ProgressSpinnerModule,
    ToastModule,
    ConfirmDialogModule,
    AppRoutingModule,
    CertificatesModule,
    UsersModule,
    ConfigurationModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    },
    ConfirmationService,
    MessageService,
    DialogService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
