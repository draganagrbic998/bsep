import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersComponent } from './users/users.component';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BadgeModule } from 'primeng/badge';
import { ChipModule } from 'primeng/chip';
import { ContextMenuModule } from 'primeng/contextmenu';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToolbarModule } from 'primeng/toolbar';
import { TableModule } from 'primeng/table';
import { CheckboxModule } from 'primeng/checkbox';
import { DropdownModule } from 'primeng/dropdown';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';


@NgModule({
  declarations: [UsersComponent],
  imports: [
    CommonModule,
    CommonModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    BadgeModule,
    ChipModule,
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
    MenubarModule
  ]
})
export class UsersModule { }
