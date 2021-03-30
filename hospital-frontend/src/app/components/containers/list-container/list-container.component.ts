import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Pagination } from 'src/app/models/pagination';

@Component({
  selector: 'app-list-container',
  templateUrl: './list-container.component.html',
  styleUrls: ['./list-container.component.scss']
})
export class ListContainerComponent implements OnInit {

  constructor() { }

  @Input() alarm: boolean;
  @Input() refresh: boolean;
  @Input() pending: boolean;
  @Input() empty: boolean;
  @Input() title: string;
  @Input() pagination: Pagination;
  @Output() changedPage: EventEmitter<number> = new EventEmitter();
  @Output() alarmWanted: EventEmitter<number> = new EventEmitter();

  ngOnInit(): void {
  }

}
