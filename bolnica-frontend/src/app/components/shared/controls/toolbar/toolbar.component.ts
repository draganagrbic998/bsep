import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { PatientService } from 'src/app/services/patient/patient.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  constructor(
    private router: Router,
    private patientService: PatientService
  ) { }

  search: FormControl = new FormControl('');

  onRoute(param: string): boolean{
    return this.router.url.substr(1).includes(param);
  }

  signOut(): void{
  }

  createPatient(): void{

  }

  announceSearchData(): void{
    this.patientService.announceSearchData(this.search.value.trim());
  }

  ngOnInit(): void {
  }

}
