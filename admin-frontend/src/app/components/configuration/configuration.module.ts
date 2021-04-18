import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule} from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { BadgeModule } from 'primeng/badge';
import { ChipModule } from 'primeng/chip';
import { ContextMenuModule } from 'primeng/contextmenu';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToolbarModule } from 'primeng/toolbar';
import { TableModule } from 'primeng/table';
import { CheckboxModule } from 'primeng/checkbox';
import { DropdownModule } from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { CardModule } from 'primeng/card';
import { InputMaskModule } from 'primeng/inputmask';

import { ConfigurationComponent } from './configuration/configuration.component';


@NgModule({
  declarations: [
    ConfigurationComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    InputTextModule,
    BadgeModule,
    ChipModule,
    ContextMenuModule,
    DialogModule,
    ConfirmDialogModule,
    ToolbarModule,
    TableModule,
    CheckboxModule,
    DropdownModule,
    InputNumberModule,
    ProgressSpinnerModule,
    MenuModule,
    MenubarModule,
    CardModule,
    InputMaskModule,
  ]
})
export class ConfigurationModule { }
