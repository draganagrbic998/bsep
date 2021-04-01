import { Injectable } from '@angular/core';
import { BehaviorSubject, of, Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CertificateInfo } from '../model/certificate-info';
import { catchError, map } from 'rxjs/operators';
import { CertificateRequest } from '../model/certificate-request';
import { Page } from '../model/page';
import { Revoke } from '../model/revoke';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  ca: BehaviorSubject<CertificateInfo | null> = new BehaviorSubject<CertificateInfo>(null);
  certificates: CertificateInfo[] = [];
  certificateRequests: CertificateRequest[] = [];

  constructor(
    private httpClient: HttpClient
  ) { }

  private readonly API_PATH = 'api/certificates';

  getCertificates(page: number, size: number): Observable<Page<CertificateInfo>> {
    const params = new HttpParams().set('page', page + '').set('size', size + '');
    return this.httpClient.get<Page<CertificateInfo>>(this.API_PATH, {params});
  }

  getCertificateRequests(page: number, size: number): Observable<Page<CertificateRequest>> {
    const params = new HttpParams().set('page', page + '').set('size', size + '');
    return this.httpClient.get<Page<CertificateRequest>>(`${this.API_PATH}/requests`, {params});
  }

  downloadCrt(alias: string): Observable<any> {
    return this.httpClient.get(`${this.API_PATH}/download-crt/${alias}`, {responseType: 'blob'});
  }

  downloadKey(alias: string): Observable<any> {
    return this.httpClient.get(`${this.API_PATH}/download-key/${alias}`, {responseType: 'blob'});
  }

  createCertificate(certificate: CertificateInfo): Observable<any> {
    return this.httpClient.post(this.API_PATH, certificate);
  }

  getByAlias(alias: string): Observable<any> {
    return this.httpClient.get<any>(`${this.API_PATH}/alias/${alias}`);
  }

  revokeCertificate(revoke: Revoke): Observable<boolean> {
    return this.httpClient.put<boolean>(this.API_PATH, revoke).pipe(
      map(() => true),
      catchError(() => of(false))
    );
  }

}
