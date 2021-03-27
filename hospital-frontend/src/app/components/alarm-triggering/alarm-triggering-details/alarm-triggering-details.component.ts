import { Component, Input, OnInit } from '@angular/core';
import { AlarmTriggering } from 'src/app/models/alarm-triggering';

@Component({
  selector: 'app-alarm-triggering-details',
  templateUrl: './alarm-triggering-details.component.html',
  styleUrls: ['./alarm-triggering-details.component.scss']
})
export class AlarmTriggeringDetailsComponent implements OnInit {

  constructor() { }

  @Input() alarmTriggering: AlarmTriggering = {} as AlarmTriggering;

  ngOnInit(): void {
  }

}
