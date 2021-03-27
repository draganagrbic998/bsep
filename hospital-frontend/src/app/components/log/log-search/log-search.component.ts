import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Log } from 'src/app/core/models/log';

@Component({
  selector: 'app-log-search',
  templateUrl: './log-search.component.html',
  styleUrls: ['./log-search.component.scss']
})
export class LogSearchComponent implements OnInit {

  constructor() { }

  @Input() fetchPending: boolean;
  @Output() searchTriggered: EventEmitter<Log> = new EventEmitter();

  searchForm: FormGroup = new FormGroup({
    status: new FormControl(''),
    description: new FormControl(''),
    userName: new FormControl(''),
    computerName: new FormControl(''),
    serviceName: new FormControl('')
  });

  ngOnInit(): void {
  }

}
