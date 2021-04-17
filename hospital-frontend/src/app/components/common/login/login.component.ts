import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS } from 'src/app/utils/dialog';
import { ADMIN, DOCTOR } from 'src/app/utils/constants';
import { StorageService } from 'src/app/services/storage.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(
    private storageService: StorageService,
    private userService: UserService,
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
      (user: User) => {
        this.pending = false;
        if (user && user.authorities.includes(ADMIN)){
          this.storageService.setUser(user);
          this.router.navigate([environment.reportRoute]);
        }
        else if (user && user.authorities.includes(DOCTOR)){
          this.storageService.setUser(user);
          this.router.navigate([environment.patientsRoute]);
        }
        else{
          this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
        }
      }
    );
  }

  ngOnInit(): void {
    this.storageService.removeUser();
  }

}
