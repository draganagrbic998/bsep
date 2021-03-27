import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/core/utils/pagination';
import { AdminAlarm } from 'src/app/core/models/admin-alarm';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminAlarmService {

  constructor(
    private http: HttpClient
  ) { }

  private refreshData: Subject<null> = new Subject();
  refreshData$: Observable<null> = this.refreshData.asObservable();

  findAll(page: number): Observable<HttpResponse<AdminAlarm[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.get<AdminAlarm[]>(environment.adminAlarmsApi, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

  save(alarm: AdminAlarm): Observable<AdminAlarm>{
    return this.http.post<AdminAlarm>(environment.adminAlarmsApi, alarm).pipe(
      catchError(() => of(null))
    );
  }

  delete(id: number): Observable<boolean>{
    return this.http.delete<null>(`${environment.adminAlarmsApi}/${id}`).pipe(
      map(() => true),
      catchError(() => of(null))
    );
  }

  announceRefreshData(): void{
    this.refreshData.next();
  }

}
