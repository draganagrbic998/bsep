import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-details-container',
  templateUrl: './details-container.component.html',
  styleUrls: ['./details-container.component.scss']
})
export class DetailsContainerComponent implements OnInit {

  constructor() { }

  @Input() delete: string;
  @Input() warn: boolean;
  @Input() padding = true;
  @Input() date: Date;
  @Input() private details: Detail[] = [];
  @Input() private mainDetails: Detail[] = [];
  @Output() deleted: EventEmitter<null> = new EventEmitter();

  get filteredDetails(): Detail[]{
    return this.details.filter(x => x.value);
  }

  get filteredMainDetails(): Detail[]{
    return this.mainDetails.filter(x => x.value);
  }

  isNumber(value: string | number): boolean{
    return typeof value === 'number';
  }

  ngOnInit(): void {
  }

}

interface Detail{
  key: string;
  value: string | number;
}
