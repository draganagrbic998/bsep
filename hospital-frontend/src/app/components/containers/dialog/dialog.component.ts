import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss']
})
export class DialogComponent {

  constructor(
    public dialogRef: MatDialogRef<DialogComponent>
  ) { }

  @Input() title: string;
  @Input() pending: boolean;
  @Input() disabled: boolean;
  @Input() warn: boolean;
  @Output() confirmed: EventEmitter<null> = new EventEmitter();

}
