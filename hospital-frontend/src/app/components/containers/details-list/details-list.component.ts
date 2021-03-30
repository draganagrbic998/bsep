import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-details-list',
  templateUrl: './details-list.component.html',
  styleUrls: ['./details-list.component.scss']
})
export class DetailsListComponent implements OnInit {

  constructor() { }

  @Input() padding: boolean;
  @Input() private details: Detail[] = [];
  @Input() private mainDetails: Detail[] = [];

  get filteredDetails(): Detail[]{
    return this.details.filter(x => x.value);
  }

  get filteredMainDetails(): Detail[]{
    return this.mainDetails.filter(x => x.value);
  }

  ngOnInit(): void {
  }

}

interface Detail{
  key: string;
  value: string | number;
}

