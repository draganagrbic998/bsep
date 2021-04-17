import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent {

  @Input() private details: Detail[] = [];
  @Input() private mainDetails: Detail[] = [];
  @Input() date: Date;
  @Input() delete: string;
  @Input() warn: boolean;
  @Output() deleted: EventEmitter<null> = new EventEmitter();

  get filteredDetails(): Detail[]{
    return this.details.filter(x => x.value !== undefined && x.value !== null);
  }

  get filteredMainDetails(): Detail[]{
    return this.mainDetails.filter(x => x.value !== undefined && x.value !== null);
  }

}

interface Detail{
  key: string;
  value: string | number;
}
