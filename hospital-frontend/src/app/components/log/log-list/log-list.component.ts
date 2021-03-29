import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FIRST_PAGE_HEADER, LAST_PAGE_HEADER } from 'src/app/constants/pagination';
import { Log } from 'src/app/models/log';
import { Pagination } from 'src/app/models/pagination';
import { LogService } from 'src/app/services/log/log.service';
import { LogSearch } from 'src/app/models/log-search';

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
      (data: HttpResponse<Log[]>) => {
        this.fetchPending = false;
        if (data){
          this.logs = data.body;
          const headers: HttpHeaders = data.headers;
          this.pagination.firstPage = headers.get(FIRST_PAGE_HEADER) === 'false' ? false : true;
          this.pagination.lastPage = headers.get(LAST_PAGE_HEADER) === 'false' ? false : true;
        }
        else{
          this.logs = [];
          this.pagination.firstPage = true;
          this.pagination.lastPage = true;
        }
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
  }

}
