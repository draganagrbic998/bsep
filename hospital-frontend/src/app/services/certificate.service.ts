import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CertificateRequest } from '../models/certificate-request';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_PATH = 'api/certificates';

  sendRequest(request: CertificateRequest): Observable<CertificateRequest>{
    return this.http.post<CertificateRequest>(this.API_PATH, request).pipe(
      catchError(() => of(null))
    );
  }
}
