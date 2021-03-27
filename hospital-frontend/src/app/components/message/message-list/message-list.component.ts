import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FIRST_PAGE_HEADER, LAST_PAGE_HEADER } from 'src/app/core/utils/pagination';
import { Message } from 'src/app/core/models/message';
import { Search } from 'src/app/core/models/search';
import { Pagination } from 'src/app/core/models/pagination';
import { MessageService } from 'src/app/services/message/message.service';

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.scss']
})
export class MessageListComponent implements OnInit {

  constructor(
    private messageService: MessageService
  ) { }

  messages: Message[] = [];
  fetchPending = true;
  pagination: Pagination = {
    pageNumber: 0,
    firstPage: true,
    lastPage: true
  };
  search: Search = {insuredNumber: '', firstName: '', lastName: '', date: null};

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchMessages();
  }

  fetchMessages(): void{
    this.fetchPending = true;
    // tslint:disable-next-line: deprecation
    this.messageService.findAll(this.pagination.pageNumber, this.search).subscribe(
      (data: HttpResponse<Message[]>) => {
        this.fetchPending = false;
        if (data){
          this.messages = data.body;
          const headers: HttpHeaders = data.headers;
          this.pagination.firstPage = headers.get(FIRST_PAGE_HEADER) === 'false' ? false : true;
          this.pagination.lastPage = headers.get(LAST_PAGE_HEADER) === 'false' ? false : true;
        }
        else{
          this.messages = [];
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
