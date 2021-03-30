import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Report } from 'src/app/models/report';
import { ReportSearch } from 'src/app/models/report-search';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_PATH = 'api/report';

  report(search: ReportSearch): Observable<Report>{
    return this.http.post<Report[]>(this.API_PATH, search).pipe(
      catchError(() => of(null))
    );
  }

}
