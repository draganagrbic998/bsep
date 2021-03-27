import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../core/services/auth.service';
import {MessageService} from 'primeng/api';
import {Router} from '@angular/router';
import {Login} from '../../core/model/login';
import {log} from 'util';
import {TokenResponse} from '../../core/model/token-response';

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
      username: ['', [Validators.required, Validators.email]],
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
    login.username = this.loginForm.get('username').value;
    login.password = this.loginForm.get('password').value;


    this.loading = true;
    this.authService.login(login).subscribe(
      (val: TokenResponse) => {
        this.authService.loggedIn(val.token, val.admin);
        this.loading = false;
        this.router.navigate(['']);
      },
      () => {
        this.loading = false;
        this.messageService.add({severity: 'error', summary: 'Invalid credentials', detail: 'Please check your username and password.'});
      });
  }


}
