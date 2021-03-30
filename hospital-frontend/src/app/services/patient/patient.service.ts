import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { Patient } from 'src/app/models/patient';
import { catchError, map } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/constants/pagination';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_PATH = 'api/patients';

  private refreshData: Subject<null> = new Subject();
  refreshData$: Observable<null> = this.refreshData.asObservable();

  private searchData: Subject<string> = new Subject();
  searchData$: Observable<string> = this.searchData.asObservable();

  findAll(page: number, search: string): Observable<HttpResponse<Patient[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '').set('search', search);
    return this.http.get<Patient[]>(this.API_PATH, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

  save(patient: Patient): Observable<Patient>{
    if (patient.id){
      return this.http.put<Patient>(`${this.API_PATH}/${patient.id}`, patient).pipe(
        catchError(() => of(null))
      );
    }
    return this.http.post<Patient>(this.API_PATH, patient).pipe(
      catchError(() => of(null))
    );
  }

  delete(id: number): Observable<boolean>{
    return this.http.delete<null>(`${this.API_PATH}/${id}`).pipe(
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
