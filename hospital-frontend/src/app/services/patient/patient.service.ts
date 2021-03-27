import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { Patient } from 'src/app/core/models/patient';
import { environment } from 'src/environments/environment';
import { catchError, map } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/core/utils/pagination';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(
    private http: HttpClient
  ) { }

  private refreshData: Subject<null> = new Subject();
  refreshData$: Observable<null> = this.refreshData.asObservable();

  private searchData: Subject<string> = new Subject();
  searchData$: Observable<string> = this.searchData.asObservable();

  findAll(page: number, search: string): Observable<HttpResponse<Patient[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '').set('search', search);
    return this.http.get<Patient[]>(environment.patientsApi, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

  save(patient: Patient): Observable<Patient>{
    if (patient.id){
      return this.http.put<Patient>(`${environment.patientsApi}/${patient.id}`, patient).pipe(
        catchError(() => of(null))
      );
    }
    return this.http.post<Patient>(environment.patientsApi, patient).pipe(
      catchError(() => of(null))
    );
  }

  delete(id: number): Observable<boolean>{
    return this.http.delete<null>(`${environment.patientsApi}/${id}`).pipe(
      map(() => true),
      catchError(() => of(null))
    );
  }

  announceRefreshData(): void{
    this.refreshData.next();
  }

  announceSearchData(search: string): void{
    this.searchData.next(search);
  }

}
