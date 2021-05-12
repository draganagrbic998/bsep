import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Message } from 'src/app/models/message';
import { Page } from 'src/app/models/page';
import { MessageService } from 'src/app/services/message.service';
import { EMPTY_PAGE } from 'src/app/utils/constants';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.scss']
})
export class MessagesComponent implements OnInit {

  constructor(
    private messageService: MessageService,
    private formBuilder: FormBuilder
  ) { }

  page: Page<Message> = EMPTY_PAGE;
  pending = true;
  searchForm = this.formBuilder.group({
    firstName: [''],
    lastName: [''],
    date: ['']
  });

  fetchMessages(pageNumber: number): void{
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.messageService.findAll(pageNumber, this.searchForm.value).subscribe(
      (page: Page<Message>) => {
        this.pending = false;
        this.page = page;
      }
    );
  }

  ngOnInit(): void {
    this.fetchMessages(0);
  }

}
