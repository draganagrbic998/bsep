import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Page } from 'src/app/models/page';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {

  @Input() title: string;
  @Input() page!: Page<any>;
  @Input() pending: boolean;
  @Input() empty: boolean;
  @Input() refresh: boolean;
  @Input() alarm: boolean;
  @Output() changedPage: EventEmitter<number> = new EventEmitter();
  @Output() alarmWanted: EventEmitter<number> = new EventEmitter();

}
