import { Component, Input, OnInit } from '@angular/core';
import { Log } from 'src/app/models/log';

@Component({
  selector: 'app-log-details',
  templateUrl: './log-details.component.html',
  styleUrls: ['./log-details.component.scss']
})
export class LogDetailsComponent implements OnInit {

  constructor() { }

  @Input() log: Log = {} as Log;

  ngOnInit(): void {
  }

}
