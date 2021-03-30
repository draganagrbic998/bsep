import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog-container',
  templateUrl: './dialog-container.component.html',
  styleUrls: ['./dialog-container.component.scss']
})
export class DialogContainerComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<DialogContainerComponent>
  ) { }

  @Input() title: string;
  @Input() warn: boolean;
  @Input() pending: boolean;
  @Input() disabled: boolean;
  @Output() confirmed: EventEmitter<null> = new EventEmitter();

  ngOnInit(): void {
  }

}
