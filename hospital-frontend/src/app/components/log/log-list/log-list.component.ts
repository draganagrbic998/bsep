import { Component, OnInit } from '@angular/core';
import { Log } from 'src/app/models/log';
import { Pagination } from 'src/app/models/pagination';
import { LogService } from 'src/app/services/log.service';
import { LogSearch } from 'src/app/models/log-search';
import { Page } from 'src/app/models/page';

@Component({
  selector: 'app-log-list',
  templateUrl: './log-list.component.html',
  styleUrls: ['./log-list.component.scss']
})
export class LogListComponent implements OnInit {

  constructor(
    private logService: LogService
  ) { }

  logs: Log[] = [];
  fetchPending = true;
  pagination: Pagination = {
    pageNumber: 0,
    firstPage: true,
    lastPage: true
  };
  search: LogSearch = {status: '', description: '', userName: '', computerName: '', serviceName: ''};

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchLogs();
  }

  fetchLogs(): void{
    this.fetchPending = true;
    // tslint:disable-next-line: deprecation
    this.logService.findAll(this.pagination.pageNumber, this.search).subscribe(
      (page: Page<Log>) => {
        this.fetchPending = false;
        this.logs = page.content;
        this.pagination.firstPage = page.first;
        this.pagination.lastPage = page.last;
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
  }

}
