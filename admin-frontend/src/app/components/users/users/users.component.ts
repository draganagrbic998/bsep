import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../../../core/services/user.service';
import { User } from '../../../core/model/user';
import { ConfirmationService, LazyLoadEvent, MenuItem, MessageService } from 'primeng/api';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Table } from 'primeng/table';
import { AuthService } from '../../../core/services/auth.service';
import { Page } from 'src/app/core/model/page';
import { Role } from 'src/app/core/model/role';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  @ViewChild('table') table: Table;
  userForm: FormGroup;
  user: User;
  users: User[] = [];
  roles: Role[];

  loading = true;
  formDialog = false;
  rows = 10;
  totalRecords = 0;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private formBuilder: FormBuilder
  ) { }

  getUsers(event: LazyLoadEvent): void {
    this.loading = true;
    const page = Math.floor(event.first / this.rows);
    const size = this.rows;
    // tslint:disable-next-line: deprecation
    this.userService.findAll(page, size).subscribe((response: Page<User>) => {
      this.loading = false;
      this.users = response.content;
      this.totalRecords = response.totalElements;
    });
  }

  saveUser(): void {
    if (this.userForm.invalid){
      return;
    }

    // tslint:disable-next-line: deprecation
    this.userService.save({...this.user, ...this.userForm.value}).subscribe((response: User) => {
      if (this.table) {
        this.table.reset();
      }
      if (response){
        this.formDialog = false;
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: `The account for ${this.user.firstName} ${this.user.lastName} saved.`
        });
      }
      else{
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error occured while saving!'
        });
      }
    });

  }

  deleteUser(user: User): void {
    // tslint:disable-next-line: deprecation
    this.userService.delete(user.id).subscribe((response: boolean) => {
      if (this.table) {
        this.table.reset();
      }
      if (response){
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: `The account for ${user.firstName} ${user.lastName} deleted.`
        });
      }
      else{
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error occured while deleting!'
        });
      }
    });
  }

  sendActivationMail(user: User): void {
    // tslint:disable-next-line: deprecation
    this.userService.sendActivationMail(user.id).subscribe((response: boolean) => {
      if (response){
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: `The activation email send to ${user.email}.`}
        );
      }
      else{
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error occured while sending activation mail!'
        });
      }
    });
  }

  saveDialog(user?: User): void {
    this.user = user || new User();
    this.userForm.reset(user);
    this.userForm.enable();
    if (this.user.id === this.authService.getToken().id) {
      this.userForm.get('email').disable();
      this.userForm.get('roles').disable();
    }
    this.formDialog = true;
  }

  getMenuItems(user: User): MenuItem[] {
    const items = [
      {icon: 'pi pi-pencil', label: 'Edit', command: () => this.saveDialog(user)},
    ];
    if (user.id !== this.authService.getToken().id) {
      items.push({icon: 'pi pi-trash', label: 'Delete', command: () => this.deleteDialog(user)});
    }
    if (!user.enabled) {
      items.push({icon: 'pi pi-envelope', label: 'Send activation mail', command: () => this.sendActivationMail(user)});
    }
    return items;
  }

  private deleteDialog(user: User): void {
    this.confirmationService.confirm({
      message: `Are you sure that you want to delete ${user.firstName} ${user.lastName}'s account?`,
      accept: () => this.deleteUser(user)
    });
  }

  ngOnInit(): void {
    // tslint:disable-next-line: deprecation
    this.userService.findAllRoles().subscribe((response: Role[]) => this.roles = response);

    this.userForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      roles: [[], Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
  }

}
