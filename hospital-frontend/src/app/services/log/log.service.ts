import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/constants/main';
import { Log } from 'src/app/models/log';
import { LogSearch } from 'src/app/models/log-search';
import { Page } from 'src/app/models/page';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_PATH = 'api/logs';

  findAll(page: number, search: LogSearch): Observable<Page<Log>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.post<Page<Log>>(this.API_PATH, search, {params}).pipe(
      catchError(() => of({content: [], first: true, last: true}))
    );
  }

}
