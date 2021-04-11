import { Component, OnInit } from '@angular/core';
import { Message } from 'src/app/models/message';
import { MessageSearch } from 'src/app/models/message-search';
import { Page } from 'src/app/models/page';
import { Pagination } from 'src/app/models/pagination';
import { MessageService } from 'src/app/services/message.service';

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
  pending = true;
  pagination: Pagination = {
    pageNumber: 0,
    firstPage: true,
    lastPage: true
  };
  search: MessageSearch = {insuredNumber: '', firstName: '', lastName: '', date: null};

  changePage(value: number): void{
    this.pagination.pageNumber += value;
    this.fetchMessages();
  }

  fetchMessages(): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.messageService.findAll(this.pagination.pageNumber, this.search).subscribe(
      (page: Page<Message>) => {
        this.pending = false;
        this.messages = page.content;
        this.pagination.firstPage = page.first;
        this.pagination.lastPage = page.last;
      }
    );
  }

  ngOnInit(): void {
    this.changePage(0);
  }

}
