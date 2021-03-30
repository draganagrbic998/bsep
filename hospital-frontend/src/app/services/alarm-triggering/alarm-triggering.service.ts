import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/constants/pagination';
import { AlarmTriggering } from 'src/app/models/alarm-triggering';

@Injectable({
  providedIn: 'root'
})
export class AlarmTriggeringService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_PATH = 'api/alarm-triggerings';

  findAll(page: number): Observable<HttpResponse<AlarmTriggering[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.get<AlarmTriggering[]>(this.API_PATH, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

}
