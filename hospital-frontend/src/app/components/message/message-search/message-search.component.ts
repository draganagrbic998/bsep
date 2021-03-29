import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MessageSearch } from 'src/app/models/message-search';

@Component({
  selector: 'app-message-search',
  templateUrl: './message-search.component.html',
  styleUrls: ['./message-search.component.scss']
})
export class MessageSearchComponent implements OnInit {

  constructor() { }

  @Input() fetchPending: boolean;
  @Output() searchTriggered: EventEmitter<MessageSearch> = new EventEmitter();

  searchForm: FormGroup = new FormGroup({
    insuredNumber: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    date: new FormControl('')
  });

  ngOnInit(): void {
  }

}
