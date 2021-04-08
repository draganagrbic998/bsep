import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-details-container',
  templateUrl: './details-container.component.html',
  styleUrls: ['./details-container.component.scss']
})
export class DetailsContainerComponent implements OnInit {

  constructor() { }

  @Input() warn: boolean;
  @Input() delete: string;
  @Input() date: Date;
  @Input() private details: Detail[] = [];
  @Input() private mainDetails: Detail[] = [];
  @Output() deleted: EventEmitter<null> = new EventEmitter();

  get filteredDetails(): Detail[]{
    return this.details.filter(x => x.value !== undefined && x.value !== null);
  }

  get filteredMainDetails(): Detail[]{
    return this.mainDetails.filter(x => x.value !== undefined && x.value !== null);
  }

  ngOnInit(): void {
  }

}

interface Detail{
  key: string;
  value: string | number;
}
