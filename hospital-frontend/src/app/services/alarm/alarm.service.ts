import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/core/utils/pagination';
import { AdminAlarm } from 'src/app/core/models/admin-alarm';
import { environment } from 'src/environments/environment';
import { DoctorAlarm } from 'src/app/core/models/doctor-alarm';

@Injectable({
  providedIn: 'root'
})
export class AlarmService {

  constructor(
    private http: HttpClient
  ) { }

  private refreshAdminData: Subject<null> = new Subject();
  refreshAdminData$: Observable<null> = this.refreshAdminData.asObservable();
  private refreshDoctorData: Subject<null> = new Subject();
  refreshDoctorData$: Observable<null> = this.refreshDoctorData.asObservable();

  findAllAdmin(page: number): Observable<HttpResponse<AdminAlarm[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.get<AdminAlarm[]>(environment.alarmsApi, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

  saveAdmin(alarm: AdminAlarm): Observable<AdminAlarm>{
    return this.http.post<AdminAlarm>(environment.alarmsApi, alarm).pipe(
      catchError(() => of(null))
    );
  }

  findAllDoctor(patientId: number, page: number): Observable<HttpResponse<DoctorAlarm[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.get<DoctorAlarm[]>(`${environment.alarmsApi}/${patientId}`, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

  saveDoctor(patientId: number, alarm: DoctorAlarm): Observable<DoctorAlarm>{
    return this.http.post<DoctorAlarm>(`${environment.alarmsApi}/${patientId}`, alarm).pipe(
      catchError(() => of(null))
    );
  }

  // ovo ces izmeniti da radi kad sredis JWT
  deleteAdmin(id: number): Observable<boolean>{
    return this.http.delete<null>(`${environment.alarmsApi}/${id}`).pipe(
      map(() => true),
      catchError(() => of(null))
    );
  }

    // ovo ces izmeniti da radi kad sredis JWT
  deleteDoctor(id: number): Observable<boolean>{
    return this.http.delete<null>(`${environment.alarmsApi}/${id}/doctor`).pipe(
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