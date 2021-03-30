import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/constants/pagination';
import { AdminAlarm } from 'src/app/models/admin-alarm';
import { DoctorAlarm } from 'src/app/models/doctor-alarm';

@Injectable({
  providedIn: 'root'
})
export class AlarmService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_PATH = 'api/alarms';

  private refreshAdminData: Subject<null> = new Subject();
  refreshAdminData$: Observable<null> = this.refreshAdminData.asObservable();
  private refreshDoctorData: Subject<null> = new Subject();
  refreshDoctorData$: Observable<null> = this.refreshDoctorData.asObservable();

  findAllAdmin(page: number): Observable<HttpResponse<AdminAlarm[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.get<AdminAlarm[]>(this.API_PATH, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

  saveAdmin(alarm: AdminAlarm): Observable<AdminAlarm>{
    return this.http.post<AdminAlarm>(this.API_PATH, alarm).pipe(
      catchError(() => of(null))
    );
  }

  findAllDoctor(patientId: number, page: number): Observable<HttpResponse<DoctorAlarm[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.get<DoctorAlarm[]>(`${this.API_PATH}/${patientId}`, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

  saveDoctor(patientId: number, alarm: DoctorAlarm): Observable<DoctorAlarm>{
    return this.http.post<DoctorAlarm>(`${this.API_PATH}/${patientId}`, alarm).pipe(
      catchError(() => of(null))
    );
  }

  delete(id: number): Observable<boolean>{
    return this.http.delete<null>(`${this.API_PATH}/${id}`).pipe(
      map(() => true),
      catchError(() => of(null))
    );
  }

  announceRefreshAdminData(): void{
    this.refreshAdminData.next();
  }

  announceRefreshDoctorData(): void{
    this.refreshDoctorData.next();
  }

}
