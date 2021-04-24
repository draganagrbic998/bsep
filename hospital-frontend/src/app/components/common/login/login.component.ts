import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthToken } from 'src/app/models/auth-token';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS } from 'src/app/utils/dialog';
import { ADMIN, DOCTOR } from 'src/app/utils/constants';
import { StorageService } from 'src/app/services/storage.service';
import { AuthService } from 'src/app/services/auth.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  constructor(
    private storageService: StorageService,
    private userService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar,
    private formBuilder: FormBuilder
  ) { }

  pending = false;
  loginForm = this.formBuilder.group({
    email: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]],
    password: ['', [Validators.required, Validators.pattern(new RegExp('\\S'))]]
  });

  login(): void{
    if (this.loginForm.invalid){
      return;
    }
    this.pending = true;
    // tslint:disable-next-line: deprecation
    this.userService.login(this.loginForm.value).subscribe(
      (token: AuthToken) => {
        this.pending = false;
        if (token && token.authorities.includes(ADMIN)){
          this.storageService.setUser(token);
          this.router.navigate([environment.reportRoute]);
        }
        else if (token && token.authorities.includes(DOCTOR)){
          this.storageService.setUser(token);
          this.router.navigate([environment.patientsRoute]);
        }
      }, er => {
        this.pending = false;
        this.snackBar.open(er.error.text ?? SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

}
