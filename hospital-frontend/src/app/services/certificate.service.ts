import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { CertificateRequest } from '../models/certificate-request';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private http: HttpClient) { }

  private readonly API_PATH = 'api/certificates';

  request(request: CertificateRequest): Observable<CertificateRequest>{
    return this.http.post<CertificateRequest>(`${this.API_PATH}/request`, request).pipe(
      catchError(() => of(null))
    );
  }

  revoke(fileName: string): Observable<boolean>{
    return this.http.delete<boolean>(`${this.API_PATH}/${fileName}`).pipe(
      map(() => true),
      catchError(() => of(false))
    );
  }
}
