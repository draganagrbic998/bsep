import { Component, Input, OnInit } from '@angular/core';
import { Log } from 'src/app/core/models/log';

@Component({
  selector: 'app-log-details',
  templateUrl: './log-details.component.html',
  styleUrls: ['./log-details.component.scss']
})
export class LogDetailsComponent implements OnInit {

  constructor() { }

  @Input() log: Log = {} as Log;

  isStatus(status: string): boolean{
    return this.log.status.toLowerCase().trim() === status.toLowerCase().trim();
  }

  ngOnInit(): void {
  }

}
