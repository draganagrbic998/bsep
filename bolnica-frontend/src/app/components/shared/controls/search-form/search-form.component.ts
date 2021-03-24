import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MessageSearch } from 'src/app/models/message-search';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.scss']
})
export class SearchFormComponent implements OnInit {

  constructor() { }

  @Input() fetchPending: boolean;
  @Output() searchTriggered: EventEmitter<MessageSearch> = new EventEmitter();

  searchForm: FormGroup = new FormGroup({
    insuredNumber: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    startDate: new FormControl(''),
    endDate: new FormControl('')
  });

  ngOnInit(): void {
  }

}
