import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/constants/pagination';
import { AlarmTriggering } from 'src/app/models/alarm-triggering';
import { Search } from 'src/app/models/search';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlarmTriggeringService {

  constructor(
    private http: HttpClient
  ) { }

  findAll(page: number, search: Search): Observable<HttpResponse<AlarmTriggering[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.post<AlarmTriggering[]>(`${environment.alarmsApi}/search`, search, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

}
