import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from '../../core/services/storage.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { AuthToken } from 'src/app/core/model/auth-token';
import { SUPER_ADMIN } from 'src/app/core/utils/constants';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  loginForm: FormGroup;
  loading = false;

  constructor(
    private storageService: StorageService,
    private authService: AuthService,
    private messageService: MessageService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
      password: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]]
    });
  }

  login(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    // tslint:disable-next-line: deprecation
    this.authService.login(this.loginForm.value).subscribe(
      (token: AuthToken) => {
        this.loading = false;
        if (token) {
          if (token.authorities.includes(SUPER_ADMIN)) {
            this.storageService.setToken(token);
            this.router.navigate(['']);
          }
          else {
            this.messageService.add({
              severity: 'error',
              summary: 'Invalid role',
              detail: 'Only super admins permitted.'
            });
          }
        }
      }, er => {
        this.loading = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: er.error.text ?? 'Please check your credentials'
        });
      });
  }

}
