import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../core/services/auth.service';
import {MessageService} from 'primeng/api';
import {Router} from '@angular/router';
import {Login} from '../../core/model/login';
import {log} from 'util';
import { Admin } from 'src/app/core/model/admin';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loading = false;

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder,
              private messageService: MessageService,
              private router: Router) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(4)]],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

  onSubmitLogin(): void {
    if (this.loginForm.invalid) {
      this.messageService.add({severity: 'error', summary: 'Unsuccessful login', detail: 'Username and password are required'});
      return;
    }

    const login = new Login();
    login.email = this.loginForm.get('username').value;
    login.password = this.loginForm.get('password').value;


    this.loading = true;
    // tslint:disable-next-line: deprecation
    this.authService.login(login).subscribe(
      (user: Admin) => {
        this.loading = false;
        if (user) {
          if (user.authorities[0] === 'SUPER_ADMIN') {
            this.authService.loggedIn(user);
            this.router.navigate(['']);
          }
          else {
            this.messageService.add({severity: 'error', summary: 'Invalid role', detail: 'Only superadmins permitted.'});
          }
        }
        else {
          this.messageService.add({severity: 'error', summary: 'Invalid credentials', detail: 'Please check your username and password.'});
        }
      });
  }


}
