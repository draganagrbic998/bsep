import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
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
  private readonly REQUESTS_PATH = 'api/requests';

  getCertificates(page: number, size: number): Observable<Page<CertificateInfo>> {
    const params = new HttpParams().set('page', page + '').set('size', size + '');
    return this.httpClient.get<Page<CertificateInfo>>(this.API_PATH, {params}).pipe(
      catchError(() => of({content: [], totalElements: 0}))
    );
  }

  getCertificateRequests(page: number, size: number): Observable<Page<CertificateRequest>> {
    const params = new HttpParams().set('page', page + '').set('size', size + '');
    return this.httpClient.get<Page<CertificateRequest>>(this.REQUESTS_PATH, {params}).pipe(
      catchError(() => of({content: [], totalElements: 0}))
    );
  }

  getByAlias(alias: string): Observable<any> {
    // nmg gospodinu da ovde stavim CertificateIngo jer onda onaj child[depth] ne radi, nmp sta je gospodin raido
    return this.httpClient.get<any>(`${this.API_PATH}/${alias}`);
  }

  downloadCrt(alias: string): Observable<any> {
    return this.httpClient.get(`${this.API_PATH}/download-crt/${alias}`, {responseType: 'blob'});
  }

  downloadKey(alias: string): Observable<any> {
    return this.httpClient.get(`${this.API_PATH}/download-key/${alias}`, {responseType: 'blob'});
  }

  createCertificate(certificate: CertificateInfo): Observable<CertificateInfo> {
    return this.httpClient.post<CertificateInfo>(this.API_PATH, certificate);
  }

  revokeCertificate(revoke: Revoke): Observable<null> {
    return this.httpClient.put<null>(this.API_PATH, revoke);
  }

}
