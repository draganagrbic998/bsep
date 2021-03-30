import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {JwtInterceptor} from './core/interceptors/jwt.interceptor';
import {ConfirmationService, MessageService} from 'primeng/api';
import { MainViewComponent } from './components/main-views/main-view/main-view.component';
import {MenuModule} from 'primeng/menu';
import {MenubarModule} from 'primeng/menubar';
import {ButtonModule} from 'primeng/button';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastModule} from 'primeng/toast';
import { LoginComponent } from './components/login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {PasswordModule} from 'primeng/password';
import {CardModule} from 'primeng/card';
import {ProgressSpinnerModule} from 'primeng/progressspinner';
import { DashboardComponent } from './components/main-views/dashboard/dashboard.component';
import { CertificatesComponent } from './components/certificates/certificates.component';
import {Dialog, DialogModule} from 'primeng/dialog';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ToolbarModule} from 'primeng/toolbar';
import {TableModule} from 'primeng/table';
import {CheckboxModule} from 'primeng/checkbox';
import {DropdownModule} from 'primeng/dropdown';
import {RadioButtonModule} from 'primeng/radiobutton';
import {InputNumberModule} from 'primeng/inputnumber';
import {DialogService} from 'primeng/dynamicdialog';
import {BadgeModule} from 'primeng/badge';
import {ChipModule} from 'primeng/chip';
import {SplitButtonModule} from 'primeng/splitbutton';
import {TabViewModule} from 'primeng/tabview';
import { TableViewComponent } from './components/table-view/table-view.component';
import { TreeViewComponent } from './components/tree-view/tree-view.component';
import {BlockUIModule} from 'primeng/blockui';
import {TabMenu, TabMenuModule} from 'primeng/tabmenu';
import {PanelModule} from 'primeng/panel';
import {ContextMenuModule} from 'primeng/contextmenu';
import { RequestViewComponent } from './components/request-view/request-view.component';

@NgModule({
  declarations: [
    AppComponent,
    MainViewComponent,
    LoginComponent,
    DashboardComponent,
    CertificatesComponent,
    TableViewComponent,
    TreeViewComponent,
    RequestViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    DialogModule,
    ConfirmDialogModule,
    ToolbarModule,
    TableModule,
    CheckboxModule,
    DropdownModule,
    RadioButtonModule,
    InputNumberModule,
    InputTextModule,
    PasswordModule,
    CardModule,
    ProgressSpinnerModule,
    HttpClientModule,
    ToastModule,
    MenuModule,
    MenubarModule,
    ButtonModule,
    BadgeModule,
    ChipModule,
    TabViewModule,
    BlockUIModule,
    TabMenuModule,
    PanelModule,
    ContextMenuModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    MessageService,
    DialogService,
    ConfirmationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
