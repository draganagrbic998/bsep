import { NgModule } from '@angular/core';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { MaterialModule } from './material.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthInterceptor } from './utils/auth.interceptor';
import { BlodTypePipe } from './utils/blod-type.pipe';
import { OnlyNumbersDirective } from './utils/only-numbers.directive';
import { COMPONENTS } from './components';

@NgModule({
  declarations: [...COMPONENTS, ...[
    AppComponent,
    BlodTypePipe,
    OnlyNumbersDirective,
  ]],
  imports: [
    AppRoutingModule,
    NgbModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    ScrollingModule,
    MaterialModule
  ],
  providers: [
    {
        provide: HTTP_INTERCEPTORS,
        useClass: AuthInterceptor,
        multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
