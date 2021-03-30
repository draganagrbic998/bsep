import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS } from 'src/app/utils/dialog';
import { ADMIN, DOCTOR } from 'src/app/utils/constants';
import { StorageService } from 'src/app/services/storage.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  constructor(
    private storageService: StorageService,
    private userService: UserService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  loginPending = false;
  loginForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    password: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))])
  });

  login(): void{
    if (this.loginForm.invalid){
      return;
    }
    this.loginPending = true;
    // tslint:disable-next-line: deprecation
    this.userService.login(this.loginForm.value).subscribe(
      (user: User) => {
        this.loginPending = false;
        if (user){
          this.storageService.setUser(user);
          if (user.authorities[0] === ADMIN){
            this.router.navigate([environment.logListRoute]);
          }
          else if (user.authorities[0] === DOCTOR){
            this.router.navigate([environment.patientListRoute]);
          }
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

  ngOnInit(): void {
  }

}
