import {Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from '../../../core/services/user.service';
import {User} from '../../../core/model/user';
import {ConfirmationService, LazyLoadEvent, MenuItem, MessageService} from 'primeng/api';
import {Authority} from '../../../core/model/authority';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Table} from 'primeng/table';
import {AuthService} from '../../../core/services/auth.service';

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
              private messageService: MessageService,
              private authService: AuthService,
              private confirmationService: ConfirmationService) { }

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

  openNew(): void {
    this.user = new User();
    this.userForm.reset();
    this.userForm.get('email').enable();
    this.userForm.get('authorities').enable();

    this.submitted = false;
    this.newDialog = true;
    this.mode = 'create';
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
    this.user = new User();
    this.userForm.reset();
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
      if (this.mode === 'create') {
        this.createUser(this.user);
      } else if (this.mode === 'edit') {
        this.updateUser(this.user);
      }
    }
  }

  createUser(user: User): void {
    this.userService.create(user).subscribe(() => {
        if (!!this.table) {
          this.table.reset();
        }
        this.userForm.reset();
        this.newDialog = false;
        this.messageService.add(
          {
            severity: 'success',
            summary: 'Success',
            detail: `The account for ${this.user.firstName} ${this.user.lastName} successfully created.
            The activation link was sent to ${this.user.email}`
          });
        this.user = new User();
      },
      er => {
        if (!!this.table) {
          this.table.reset();
        }
        this.userForm.reset();
        this.newDialog = false;
        this.messageService.add(
          {
            severity: 'error',
            summary: 'Error',
            detail: er.error.text
          });
        this.user = new User();
      });
  }

  updateUser(user: User): void {
    this.userService.update(user).subscribe(() => {
        if (!!this.table) {
          this.table.reset();
        }
        this.userForm.reset();
        this.newDialog = false;
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: `The account for ${this.user.firstName} ${this.user.lastName} successfully modified.`
        });
      },
      er => {
        if (!!this.table) {
          this.table.reset();
        }
        this.userForm.reset();
        this.newDialog = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: er.error.text
        });
      });
  }

  getMenuItems(user: User): MenuItem[] {
    const items = [
      {icon: 'pi pi-pencil', label: 'Edit', command: () => this.editDialog(user)},
    ];
    if (this.authService.getUser().id !== user.id && user.id !== 1) {
      items.push({icon: 'pi pi-trash', label: 'Delete', command: () => this.deleteUser(user)});
    }
    if (!user.enabled) {
      items.push({icon: 'pi pi-envelope', label: 'Send activation mail', command: () => this.sendActivation(user)});
    }
    return items;
  }

  editDialog(user: User): void {
    this.userForm.patchValue(
      {
        firstName: user.firstName,
        lastName: user.lastName,
        authorities: user.authorities,
        email: user.email
      });
    this.user = user;
    if (this.user.id === 1) {
      this.userForm.get('email').disable();
      this.userForm.get('authorities').disable();
    } else {
      this.userForm.get('email').enable();
      this.userForm.get('authorities').enable();
    }
    this.submitted = false;
    this.newDialog = true;
    this.mode = 'edit';
  }

  deleteUser(user: User): void {
    this.confirmationService.confirm({
      message: `Are you sure that you want to delete ${user.firstName} ${user.lastName}'s account?`,
      accept: () => this.confirmDeletion(user)
    });
  }

  confirmDeletion(user: User): void {
    this.userService.delete(user).subscribe(() => {
      if (!!this.table) {
        this.table.reset();
      }
      this.messageService.add({
        severity: 'success',
        summary: 'Success',
        detail: `The account for ${user.firstName} ${user.lastName} was deleted.`
      });
    });
  }

  sendActivation(user: User): void {
    this.userService.sendActivationMail(user.id).subscribe(() => {
      this.messageService.add(
        {severity: 'success', summary: 'Email sent', detail: 'The activation email has been sent successfully.'}
        );
    });
  }


  get users(): User[] {
    return this.userService.users;
  }

  get authorities(): Authority[] {
    return this.userService.authorities;
  }
}

