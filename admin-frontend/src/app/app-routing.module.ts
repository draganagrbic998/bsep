import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { environment } from 'src/environments/environment';

import { AuthGuard } from './core/guards/auth.guard';
import { LoginComponent } from './components/login/login.component';
import { ActivateComponent } from './components/users/activate/activate.component';
import { MainViewComponent } from './components/main-views/main-view/main-view.component';
import { DashboardComponent } from './components/main-views/dashboard/dashboard.component';
import { CertificatesComponent } from './components/certificates/certificates/certificates.component';
import { UsersComponent } from './components/users/users/users.component';
import { ConfigurationComponent } from './components/configuration/configuration/configuration.component';
import { AddCertificateComponent } from './components/certificates/add-certificate/add-certificate.component';
import { SUPER_ADMIN } from './core/utils/constants';

const routes: Routes = [
  {
    path: environment.loginRoute,
    component: LoginComponent,
    data: {unauthorized: true},
    canActivate: [AuthGuard]
  },
  {
    path: environment.activateRoute,
    component: ActivateComponent,
    data: {unauthorized: true},
    canActivate: [AuthGuard]
  },
  {
    path: '',
    component: MainViewComponent,
    data: {authorities: [SUPER_ADMIN]},
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: DashboardComponent,
      },
      {
        path: environment.certificatesRoute,
        component: CertificatesComponent
      },
      {
        path: environment.usersRoute,
        component: UsersComponent
      },
      {
        path: environment.configurationRoute,
        component: ConfigurationComponent
      },
      {
        path: environment.addCertificateRoute,
        component: AddCertificateComponent
      }
    ]
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: environment.loginRoute
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
