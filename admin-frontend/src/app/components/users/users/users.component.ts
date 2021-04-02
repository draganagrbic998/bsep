import {Component, OnInit, ViewChild} from '@angular/core';
import { CertificateInfo } from '../../../core/model/certificate-info';
import {UserService} from '../../../core/services/user.service';
import {User} from '../../../core/model/user';
import {LazyLoadEvent, MessageService} from 'primeng/api';
import {Authority} from '../../../core/model/authority';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Table} from 'primeng/table';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  user: User = new User();
  submitted = false;
  newDialog = false;
  detailsDialog = false;
  rows = 10;
  totalRecords = 0;
  first = 0;
  loading = true;
  mode = 'create';
  userForm: FormGroup;

  @ViewChild('table')
  table: Table;

  constructor(private userService: UserService,
              private formBuilder: FormBuilder,
              private messageService: MessageService) { }

  ngOnInit(): void {
    this.userForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      authorities: [[], Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
    this.userService.getAuthorities().subscribe(val => {
      this.userService.authorities = val;
    });
  }

  openNew(mode: string = 'create'): void {
    this.user = new User();
    this.submitted = false;
    this.newDialog = true;
    this.mode = mode;
  }

  getUsers(event: LazyLoadEvent): void {
    this.loading = true;
    const page = Math.floor(event.first / this.rows);
    const size = this.rows;
    // tslint:disable-next-line: deprecation
    this.userService.get(page, size).subscribe(val => {
      this.userService.users = val.content;
      this.totalRecords = val.totalElements;
      this.loading = false;
    });
  }

  hideNew(): void {
    this.newDialog = false;
    this.submitted = false;
  }

  saveUser(): void {
    this.submitted = true;

    if (this.userForm.valid) {
      this.user.firstName = this.userForm.get('firstName').value;
      this.user.lastName = this.userForm.get('lastName').value;
      this.user.email = this.userForm.get('email').value;
      this.user.authorities = this.userForm.get('authorities').value;
      this.userService.create(this.user).subscribe(() => {
        if (!!this.table) {
          this.table.reset();
        }
        this.newDialog = false;
        this.messageService.add(
          {
            severity: 'success',
            summary: 'Success',
            detail: `The account for ${this.user.firstName} ${this.user.lastName} successfully created.
            An activation link was sent to ${this.user.email}.`
          });

      });
    }
  }
  get users(): User[] {
    return this.userService.users;
  }

  get authorities(): Authority[] {
    return this.userService.authorities;
  }
}

