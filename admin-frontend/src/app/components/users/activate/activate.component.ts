import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {UserService} from '../../../core/services/user.service';
import {User} from '../../../core/model/user';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.scss']
})
export class ActivateComponent implements OnInit {

  user: User;
  activateForm: FormGroup;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private userService: UserService,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(8)]],
      repeat: ['', [Validators.required]]
    });

    this.activatedRoute.queryParams.subscribe((params: Params) => {
      if (!params.q) {
        this.router.navigate(['login']);
        return;
      }

      this.userService.getDisabled(params.q).subscribe(val => {
          this.user = val;
        },
        () => {
          this.router.navigate(['login']);
        });
    });
  }

}
