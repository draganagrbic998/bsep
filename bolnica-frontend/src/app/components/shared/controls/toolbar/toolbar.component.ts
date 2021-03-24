import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'src/app/services/message/message.service';
import { PatientService } from 'src/app/services/patient/patient.service';
import { StorageService } from 'src/app/services/storage/storage.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  constructor(
    private storageService: StorageService,
    private patientService: PatientService,
    private messageService: MessageService,
    private router: Router
  ) { }

  search: FormControl = new FormControl('');

  onRoute(param: string): boolean{
    return this.router.url.substr(1).includes(param);
  }

  signOut(): void{
  }

  createPatient(): void{
    this.storageService.remove(this.storageService.PATIENT_KEY);
    this.router.navigate([`${environment.patientFormRoute}/create`]);
  }

  announceSearchData(): void{
    this.patientService.announceSearchData(this.search.value.trim());
  }

  ngOnInit(): void {
  }

}
