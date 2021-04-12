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

  ca: BehaviorSubject<CertificateInfo | null> = new BehaviorSubject<CertificateInfo | null>(null);
  certificates: CertificateInfo[] = [];
  certificateRequests: CertificateRequest[] = [];
  requestCertificate: BehaviorSubject<CertificateInfo | undefined> = new BehaviorSubject<CertificateInfo | undefined>(undefined);

  constructor(
    private httpClient: HttpClient
  ) { }

  private readonly API_PATH = 'api/certificates';

  findAll(page: number, size: number): Observable<Page<CertificateInfo>> {
    const params = new HttpParams().set('page', page + '').set('size', size + '');
    return this.httpClient.get<Page<CertificateInfo>>(this.API_PATH, {params}).pipe(
      catchError(() => of({content: [], totalElements: 0}))
    );
  }

  findByAlias(alias: string): Observable<any> {
    // nmg gospodinu da ovde stavim CertificateIngo jer onda onaj child[depth] ne radi, nmp sta je gospodin raido
    return this.httpClient.get<any>(`${this.API_PATH}/${alias}`);
  }

  findAllRequests(page: number, size: number): Observable<Page<CertificateRequest>> {
    const params = new HttpParams().set('page', page + '').set('size', size + '');
    return this.httpClient.get<Page<CertificateRequest>>(`${this.API_PATH}/requests`, {params}).pipe(
      catchError(() => of({content: [], totalElements: 0}))
    );
  }

  create(certificate: CertificateInfo): Observable<CertificateInfo> {
    return this.httpClient.post<CertificateInfo>(this.API_PATH, certificate);
  }

  revoke(revoke: Revoke): Observable<null> {
    return this.httpClient.put<null>(this.API_PATH, revoke);
  }

  downloadCrt(alias: string): Observable<any> {
    return this.httpClient.get(`${this.API_PATH}/download-crt/${alias}`, {responseType: 'blob'});
  }

  downloadKey(alias: string): Observable<any> {
    return this.httpClient.get(`${this.API_PATH}/download-key/${alias}`, {responseType: 'blob'});
  }

}
