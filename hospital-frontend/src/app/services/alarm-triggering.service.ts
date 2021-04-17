import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { EMPTY_PAGE, PAGE_SIZE } from 'src/app/utils/constants';
import { AlarmTriggering } from 'src/app/models/alarm-triggering';
import { Page } from 'src/app/models/page';

@Injectable({
  providedIn: 'root'
})
export class AlarmTriggeringService {

  constructor(private http: HttpClient) { }

  private readonly API_PATH = 'api/alarm-triggerings';

  findAll(page: number): Observable<Page<AlarmTriggering>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.get<Page<AlarmTriggering>>(this.API_PATH, {params}).pipe(
      catchError(() => of(EMPTY_PAGE))
    );
  }

}
