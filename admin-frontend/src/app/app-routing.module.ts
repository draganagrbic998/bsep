import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthGuard} from './core/guards/auth.guard';
import {LoginComponent} from './components/login/login.component';
import {MainViewComponent} from './components/main-views/main-view/main-view.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent, data: {roles: ['UNREGISTERED']}, canActivate: [AuthGuard]},
  {path: '', component: MainViewComponent, data: {roles: ['ROLE_ADMIN']}, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
