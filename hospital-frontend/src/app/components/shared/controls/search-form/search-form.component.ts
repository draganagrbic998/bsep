import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Search } from 'src/app/models/search';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.scss']
})
export class SearchFormComponent implements OnInit {

  constructor() { }

  @Input() fetchPending: boolean;
  @Output() searchTriggered: EventEmitter<Search> = new EventEmitter();

  searchForm: FormGroup = new FormGroup({
    insuredNumber: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    date: new FormControl('')
  });

  ngOnInit(): void {
  }

}
