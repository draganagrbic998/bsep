import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Report } from 'src/app/models/report';
import { ReportSearch } from 'src/app/models/report-search';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(
    private http: HttpClient
  ) { }

  report(search: ReportSearch): Observable<Report>{
    return this.http.post<Report[]>(environment.reportApi, search).pipe(
      catchError(() => of(null))
    );
  }

}
