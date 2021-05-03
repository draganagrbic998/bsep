import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Report } from 'src/app/models/report';
import { dateFormat } from '../utils/date-format';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient) { }

  private readonly API_PATH = 'api/report';

  report(start: Date, end: Date): Observable<Report>{
    const params = new HttpParams().set('start', dateFormat(start)).set('end', dateFormat(end));
    return this.http.get<Report[]>(this.API_PATH, {params}).pipe(
      catchError(() => of(null))
    );
  }

}
