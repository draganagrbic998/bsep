import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BadgeModule } from 'primeng/badge';
import { ChipModule } from 'primeng/chip';
import { TabViewModule } from 'primeng/tabview';
import { BlockUIModule } from 'primeng/blockui';
import { TabMenuModule } from 'primeng/tabmenu';
import { PanelModule } from 'primeng/panel';
import { ContextMenuModule } from 'primeng/contextmenu';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToolbarModule } from 'primeng/toolbar';
import { TableModule } from 'primeng/table';
import { CheckboxModule } from 'primeng/checkbox';
import { DropdownModule } from 'primeng/dropdown';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { BrowserModule } from '@angular/platform-browser';
import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { MultiSelectModule } from 'primeng/multiselect';

import { CertificatesComponent } from './certificates/certificates.component';
import { TableViewComponent } from './table-view/table-view.component';
import { TreeViewComponent } from './tree-view/tree-view.component';
import { RequestViewComponent } from './request-view/request-view.component';
import { AddCertificateComponent } from './add-certificate/add-certificate.component';


@NgModule({
  declarations: [
    CertificatesComponent,
    TableViewComponent,
    TreeViewComponent,
    RequestViewComponent,
    AddCertificateComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    BadgeModule,
    ChipModule,
    InputTextModule,
    TabViewModule,
    BlockUIModule,
    TabMenuModule,
    PanelModule,
    ContextMenuModule,
    DialogModule,
    ConfirmDialogModule,
    ToolbarModule,
    TableModule,
    CheckboxModule,
    DropdownModule,
    RadioButtonModule,
    InputNumberModule,
    ProgressSpinnerModule,
    MenuModule,
    MenubarModule,
    CardModule,
    MultiSelectModule
  ]
})
export class CertificatesModule { }
