import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { LogSearch } from 'src/app/models/log-search';

@Component({
  selector: 'app-log-search',
  templateUrl: './log-search.component.html',
  styleUrls: ['./log-search.component.scss']
})
export class LogSearchComponent implements OnInit {

  constructor() { }

  @Input() fetchPending: boolean;
  @Output() searchTriggered: EventEmitter<LogSearch> = new EventEmitter();

  searchForm: FormGroup = new FormGroup({
    mode: new FormControl(''),
    status: new FormControl(''),
    ipAddress: new FormControl(''),
    description: new FormControl(''),
    date: new FormControl('')
  });

  ngOnInit(): void {
  }

}
