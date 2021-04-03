import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {UserService} from '../../../core/services/user.service';
import {User} from '../../../core/model/user';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Activation} from '../../../core/model/activation';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.scss']
})
export class ActivateComponent implements OnInit {

  user: User;
  activateForm: FormGroup;
  loading = false;
  expired = true;
  activated = false;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private userService: UserService,
              private formBuilder: FormBuilder,
              private messageService: MessageService) { }

  ngOnInit(): void {

    this.activateForm = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(8)]],
      repeat: ['', Validators.required]
    });

    this.activatedRoute.queryParams.subscribe((params: Params) => {
      if (!params.q) {
        this.router.navigate(['login']);
        return;
      }

      this.userService.getDisabled(params.q).subscribe(val => {
          this.user = val;
          this.expired = new Date(this.user.activationExpiration).getTime() <= new Date().getTime();
        },
        () => {
          this.router.navigate(['login']);
        });
    });
  }

  activate(): void {
    this.loading = true;

    if (!this.activateForm.valid) {
      this.loading = false;
      return;
    }

    const password = this.activateForm.get('password').value;
    const repeat = this.activateForm.get('repeat').value;

    if (password !== repeat) {
      this.loading = false;
      this.activateForm.get('repeat').setErrors(['Passwords don\'t match']);
      return;
    }

    const activate = new Activation();
    activate.password = this.activateForm.get('password').value;
    activate.uuid = this.user.activationLink;

    this.userService.activate(activate).subscribe(val => {
      this.activated = true;
      this.messageService.add({severity: 'success', summary: 'Successful activation', detail: 'You can now log into our services.'});
    });
  }

}
