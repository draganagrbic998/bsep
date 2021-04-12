import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Pagination } from 'src/app/models/pagination';

@Component({
  selector: 'app-list-container',
  templateUrl: './list-container.component.html',
  styleUrls: ['./list-container.component.scss']
})
export class ListContainerComponent implements OnInit {

  constructor() { }

  @Input() title: string;
  @Input() pending: boolean;
  @Input() empty: boolean;
  @Input() refresh: boolean;
  @Input() alarm: boolean;
  @Input() pagination: Pagination;
  @Output() changedPage: EventEmitter<number> = new EventEmitter();
  @Output() alarmWanted: EventEmitter<number> = new EventEmitter();

  ngOnInit(): void {
  }

}
