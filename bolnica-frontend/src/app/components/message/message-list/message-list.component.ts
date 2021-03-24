import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { FIRST_PAGE_HEADER, LAST_PAGE_HEADER } from 'src/app/constants/pagination';
import { Message } from 'src/app/models/message';
import { Pagination } from 'src/app/models/pagination';
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

  searchForm: FormGroup = new FormGroup({
    insuredNumber: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    startDate: new FormControl(''),
    endDate: new FormControl('')
  });
  searchPending = false;

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchMessages();
  }

  fetchMessages(): void{
    this.fetchPending = true;
    // tslint:disable-next-line: deprecation
    this.messageService.findAll(this.pagination.pageNumber, this.searchForm.value).subscribe(
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
    // tslint:disable-next-line: deprecation
    this.messageService.refreshData$.subscribe(() => {
      this.changePage(0);
    });
  }

}
