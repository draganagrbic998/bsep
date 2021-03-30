import { Injectable } from '@angular/core';
import {BehaviorSubject, of, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {CertificateInfo} from '../model/certificate-info';
import {asLiteral} from '@angular/compiler/src/render3/view/util';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  ca: BehaviorSubject<CertificateInfo | null> = new BehaviorSubject<CertificateInfo>(null);
  certificates: CertificateInfo[] = [];

  constructor(private httpClient: HttpClient) { }

  getCertificates(page, size): Observable<any> {
    return this.httpClient.get(`https://localhost:8080/api/certificates?page=${page}&size=${size}`);
  }

  downloadCrt(alias: string): Observable<any> {
    return this.httpClient.get(`https://localhost:8080/api/certificates/download-crt/${alias}`, {responseType: 'blob'});
  }

  createCertificate(certificate: CertificateInfo): Observable<any> {
    return this.httpClient.post('https://localhost:8080/api/certificates', certificate);
  }

  getByAlias(alias: string): Observable<any> {
    return this.httpClient.get(`https://localhost:8080/api/certificates/alias/${alias}`);
  }

  revokeCertificate(certificateId: number): Observable<boolean> {
    return this.httpClient.delete(`https://localhost:8080/api/certificates/${certificateId}`).pipe(
      map((response: {value: boolean}) => response.value),
      catchError(() => of(false))
    );
  }

  getCertificateRequests(page, size): Observable<any> {
    return this.httpClient.get(`https://localhost:8080/api/certificates/requests?page=${page}&size=${size}`);
  }
}
