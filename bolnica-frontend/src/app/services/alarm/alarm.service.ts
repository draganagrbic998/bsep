import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Alarm } from 'src/app/models/alarm';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlarmService {

  constructor(
    private http: HttpClient
  ) { }

  save(patientId: number, alarm: Alarm): Observable<Alarm>{
    return this.http.post<Alarm>(`${environment.alarmsApi}/${patientId}`, alarm).pipe(
      catchError(() => of(null))
    );
  }

}
