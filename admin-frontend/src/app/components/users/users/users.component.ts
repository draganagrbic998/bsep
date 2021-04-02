import { Component, OnInit } from '@angular/core';
import { CertificateInfo } from '../../../core/model/certificate-info';
import {UserService} from '../../../core/services/user.service';
import {User} from '../../../core/model/user';
import {LazyLoadEvent} from 'primeng/api';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  user: CertificateInfo = new CertificateInfo();
  submitted = false;
  newDialog = false;
  detailsDialog = false;
  rows = 10;
  totalRecords = 0;
  first = 0;
  loading = true;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  openNew(): void {
    this.user = new CertificateInfo();
    this.submitted = false;
    this.newDialog = true;
  }

  getUsers(event: LazyLoadEvent): void {
    this.loading = true;
    const page = Math.floor(event.first / this.rows);
    const size = this.rows;
    // tslint:disable-next-line: deprecation
    this.userService.get(page, size).subscribe(val => {
      console.log(val);
      this.userService.users = val.content;
      this.totalRecords = val.totalElements;
      this.loading = false;
    });
  }

  get users(): User[] {
    return this.userService.users;
  }
}

