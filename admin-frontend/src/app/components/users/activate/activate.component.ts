import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { UserService } from '../../../core/services/user.service';
import { User } from '../../../core/model/user';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.scss']
})
export class ActivateComponent implements OnInit {

  activateForm: FormGroup;
  user: User;
  loading = false;
  expired = true;
  activated = false;

  constructor(
    private userService: UserService,
    private messageService: MessageService,
    private formBuilder: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {

    this.activateForm = this.formBuilder.group({
      password: ['', [Validators.required, Validators.pattern(
        new RegExp('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$'))]],
      repeat: ['', [this.passwordConfirmed()]]
    });

    this.activateForm.controls.password.valueChanges
    // tslint:disable-next-line: deprecation
    .subscribe(() => this.activateForm.controls.repeat.updateValueAndValidity());

    // tslint:disable-next-line: deprecation
    this.activatedRoute.queryParams.subscribe((params: Params) => {
      if (!params.q) {
        this.router.navigate([environment.loginRoute]);
        return;
      }

      // tslint:disable-next-line: deprecation
      this.userService.getDisabled(params.q).subscribe((user: User) => {
        if (user){
          this.user = user;
          this.expired = new Date(this.user.activationExpiration).getTime() <= new Date().getTime();
        }
        else{
          this.router.navigate([environment.loginRoute]);
        }
      });
    });
  }

  activate(): void {
    if (!this.activateForm.valid) {
      return;
    }

    this.loading = true;
    // tslint:disable-next-line: deprecation
    this.userService.activate({...this.activateForm.value, ...{uuid: this.user.activationLink}}).subscribe((response: User) => {
      if (response){
        this.activated = true;
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'You can now log into our services.'
        });
      }
      else{
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: `Error occured while activating!`
        });
      }
    });
  }

  private passwordConfirmed(): ValidatorFn{
    return (control: AbstractControl): ValidationErrors => {
      const passwordConfirmed = control.parent ?
      control.value === control.parent.get('password').value : true;
      return passwordConfirmed ? null : {passwordError: true};
    };
  }

}
