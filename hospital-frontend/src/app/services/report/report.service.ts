import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Report } from 'src/app/models/report';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(
    private http: HttpClient
  ) { }

  report(): Observable<Report>{
    return this.http.get<Report[]>(environment.reportApi).pipe(
      catchError(() => of(null))
    );
  }

}
