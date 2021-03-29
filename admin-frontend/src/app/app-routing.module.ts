import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthGuard} from './core/guards/auth.guard';
import {LoginComponent} from './components/login/login.component';
import {MainViewComponent} from './components/main-views/main-view/main-view.component';
import {DashboardComponent} from './components/main-views/dashboard/dashboard.component';
import {CertificatesComponent} from './components/certificates/certificates.component';
import {TableViewComponent} from './components/table-view/table-view.component';
import {TreeViewComponent} from './components/tree-view/tree-view.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent, data: {roles: ['UNREGISTERED']}, canActivate: [AuthGuard]},
  {path: '', component: MainViewComponent, children: [
      {path: '', component: DashboardComponent, data: {roles: ['ROLE_ADMIN']}, canActivate: [AuthGuard]},
      {path: 'certificates', component: CertificatesComponent, data: {roles: ['ROLE_ADMIN']}, canActivate: [AuthGuard], children: [
          {path: '', component: TableViewComponent},
          {path: 'tree', component: TreeViewComponent}
        ]}

    ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
