import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Pagination } from 'src/app/core/models/pagination';

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.scss']
})
export class PaginatorComponent implements OnInit {

  constructor() { }

  @Input() small: boolean;
  @Input() refresh: boolean;
  @Input() alarm: boolean;
  @Input() pending: boolean;
  @Input() pagination: Pagination = {firstPage: true, lastPage: true, pageNumber: 0};
  @Output() changedPage: EventEmitter<number> = new EventEmitter();
  @Output() alarmWanted: EventEmitter<null> = new EventEmitter();

  ngOnInit(): void {
  }

}
