import { Component, OnInit } from '@angular/core';
import {CertificateInfo} from '../../../core/model/certificate-info';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  user: CertificateInfo = new CertificateInfo();
  submitted = false;
  newDialog = false;
  detailsDialog = false;

  constructor() { }

  ngOnInit(): void {
  }

  openNew(): void {
    this.user = new CertificateInfo();
    this.submitted = false;
    this.newDialog = true;
  }

}

