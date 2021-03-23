import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Patient } from 'src/app/models/patient';
import { environment } from 'src/environments/environment';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(
    private http: HttpClient
  ) { }

  save(patient: Patient): Observable<Patient>{
    return this.http.post<Patient>(environment.patientsApi, patient).pipe(
      catchError(() => of(null))
    );
  }

}
