import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/constants/pagination';
import { Log } from 'src/app/models/log';
import { environment } from 'src/environments/environment';
import { LogSearch } from 'src/app/models/log-search';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  constructor(
    private http: HttpClient
  ) { }

  findAll(page: number, search: LogSearch): Observable<HttpResponse<Log[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.post<Log[]>(environment.logsApi, search, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

}
