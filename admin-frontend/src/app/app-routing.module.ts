import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { LoginComponent } from './components/login/login.component';
import { MainViewComponent } from './components/main-views/main-view/main-view.component';
import { DashboardComponent } from './components/main-views/dashboard/dashboard.component';
import { CertificatesComponent } from './components/certificates/certificates/certificates.component';
import { TableViewComponent } from './components/certificates/table-view/table-view.component';
import { TreeViewComponent } from './components/certificates/tree-view/tree-view.component';
import { RequestViewComponent } from './components/certificates/request-view/request-view.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '',
    component: MainViewComponent,
    children: [
      {
        path: '',
        component: DashboardComponent,
        data: {authorities: ['SUPER_ADMIN']},
        canActivate: [AuthGuard]},
      {
        path: 'certificates',
        component: CertificatesComponent,
        data: {authorities: ['SUPER_ADMIN']},
        canActivate: [AuthGuard],
        children: [
            {path: '', component: TableViewComponent},
            {path: 'tree', component: TreeViewComponent},
            {path: 'requests', component: RequestViewComponent}
          ]
        }
    ]},
    {
      path: '**',
      pathMatch: 'full',
      redirectTo: 'login'
    }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
