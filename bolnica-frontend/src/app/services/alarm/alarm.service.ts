import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/constants/pagination';
import { Alarm } from 'src/app/models/alarm';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlarmService {

  constructor(
    private http: HttpClient
  ) { }

  private refreshData: Subject<null> = new Subject();
  refreshData$: Observable<null> = this.refreshData.asObservable();

  findAll(patientId: number, page: number): Observable<HttpResponse<Alarm[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.get<Alarm[]>(`${environment.alarmsApi}/${patientId}`, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

  save(patientId: number, alarm: Alarm): Observable<Alarm>{
    return this.http.post<Alarm>(`${environment.alarmsApi}/${patientId}`, alarm).pipe(
      catchError(() => of(null))
    );
  }

  delete(id: number): Observable<boolean>{
    return this.http.delete<null>(`${environment.alarmsApi}/${id}`).pipe(
      map(() => true),
      catchError(() => of(null))
    );
  }

  announceRefreshData(): void{
    this.refreshData.next();
  }

}
