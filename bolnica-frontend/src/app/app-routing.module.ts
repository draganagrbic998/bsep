import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { environment } from 'src/environments/environment';
import { PatientFormComponent } from './components/patient/patient-form/patient-form.component';

const routes: Routes = [
  {
    path: environment.patientFormRoute,
    component: PatientFormComponent,
    // dodaj guard
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
