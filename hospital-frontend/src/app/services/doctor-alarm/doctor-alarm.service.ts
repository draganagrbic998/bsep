import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/core/utils/pagination';
import { DoctorAlarm } from 'src/app/core/models/doctor-alarm';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DoctorAlarmService {

  constructor(
    private http: HttpClient
  ) { }

  private refreshData: Subject<null> = new Subject();
  refreshData$: Observable<null> = this.refreshData.asObservable();

  findAll(patientId: number, page: number): Observable<HttpResponse<DoctorAlarm[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.get<DoctorAlarm[]>(`${environment.doctorAlarmsApi}/${patientId}`, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

  save(patientId: number, alarm: DoctorAlarm): Observable<DoctorAlarm>{
    return this.http.post<DoctorAlarm>(`${environment.doctorAlarmsApi}/${patientId}`, alarm).pipe(
      catchError(() => of(null))
    );
  }

  delete(id: number): Observable<boolean>{
    return this.http.delete<null>(`${environment.doctorAlarmsApi}/${id}`).pipe(
      map(() => true),
      catchError(() => of(null))
    );
  }

  announceRefreshData(): void{
    this.refreshData.next();
  }

}
