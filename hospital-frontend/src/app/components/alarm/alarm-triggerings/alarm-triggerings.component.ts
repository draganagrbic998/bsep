import { Component, OnInit } from '@angular/core';
import { AlarmTriggering } from 'src/app/models/alarm-triggering';
import { Page } from 'src/app/models/page';
import { AlarmTriggeringService } from 'src/app/services/alarm-triggering.service';
import { EMPTY_PAGE } from 'src/app/utils/constants';

@Component({
  selector: 'app-alarm-triggerings',
  templateUrl: './alarm-triggerings.component.html',
  styleUrls: ['./alarm-triggerings.component.scss']
})
export class AlarmTriggeringsComponent implements OnInit {

  constructor(
    private alarmTriggeringService: AlarmTriggeringService
  ) { }

  page: Page<AlarmTriggering> = EMPTY_PAGE;
  pending = true;

  fetchAlarmTriggerings(pageNumber: number): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.alarmTriggeringService.findAll(pageNumber).subscribe(
      (page: Page<AlarmTriggering>) => {
        this.pending = false;
        this.page = page;
      }
    );
  }

  ngOnInit(): void {
    this.fetchAlarmTriggerings(0);
  }

}
