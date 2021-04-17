import { Component, OnInit } from '@angular/core';
import { Log } from 'src/app/models/log';
import { LogService } from 'src/app/services/log.service';
import { Page } from 'src/app/models/page';
import { FormBuilder } from '@angular/forms';
import { EMPTY_PAGE } from 'src/app/utils/constants';

@Component({
  selector: 'app-logs',
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.scss']
})
export class LogsComponent implements OnInit {

  constructor(
    private logService: LogService,
    private formBuilder: FormBuilder
  ) { }

  page: Page<Log> = EMPTY_PAGE;
  pending = true;
  searchForm = this.formBuilder.group({
    mode: [''],
    status: [''],
    ipAddress: [''],
    description: [''],
    date: ['']
  });

  fetchLogs(pageNumber: number): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.logService.findAll(pageNumber, this.searchForm.value).subscribe(
      (page: Page<Log>) => {
        this.pending = false;
        this.page = page;
      }
    );
  }

  ngOnInit(): void {
    this.fetchLogs(0);
  }

}
