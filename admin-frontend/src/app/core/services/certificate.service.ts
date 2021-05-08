import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CertificateInfo } from '../model/certificate-info';
import { catchError } from 'rxjs/operators';
import { CertificateRequest } from '../model/certificate-request';
import { Page } from '../model/page';
import { Revoke } from '../model/revoke';
import { EMPTY_PAGE } from '../utils/constants';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  private readonly API_PATH = 'api/certificates';

  ca: CertificateInfo;
  certificate: CertificateInfo;

  constructor(private httpClient: HttpClient) { }

  findAll(page: number, size: number): Observable<Page<CertificateInfo>> {
    const params = new HttpParams().set('page', page + '').set('size', size + '');
    return this.httpClient.get<Page<CertificateInfo>>(this.API_PATH, {params}).pipe(
      catchError(() => of(EMPTY_PAGE))
    );
  }

  findByAlias(alias: string): Observable<CertificateInfo> {
    return this.httpClient.get<CertificateInfo>(`${this.API_PATH}/${alias}`).pipe(
      catchError(() => of(null))
    );
  }

  findAllRequests(page: number, size: number): Observable<Page<CertificateRequest>> {
    const params = new HttpParams().set('page', page + '').set('size', size + '');
    return this.httpClient.get<Page<CertificateRequest>>(`${this.API_PATH}/requests`, {params}).pipe(
      catchError(() => of(EMPTY_PAGE))
    );
  }

  create(certificate: CertificateInfo): Observable<CertificateInfo> {
    return this.httpClient.post<CertificateInfo>(this.API_PATH, certificate).pipe(
      catchError(() => of(null))
    );
  }

  revoke(revoke: Revoke): Observable<CertificateInfo> {
    return this.httpClient.put<CertificateInfo>(this.API_PATH, revoke).pipe(
      catchError(() => of(null))
    );
  }

  downloadCrt(alias: string): Observable<Blob> {
    return this.httpClient.get<Blob>(`${this.API_PATH}/download-crt/${alias}`,
      {responseType: 'blob' as 'json'})
    .pipe(catchError(() => of(null)));
  }

  downloadKey(alias: string): Observable<Blob> {
    return this.httpClient.get<Blob>(`${this.API_PATH}/download-key/${alias}`,
      {responseType: 'blob' as 'json'})
    .pipe(catchError(() => of(null)));
  }

  downloadJks(issuerAlias: string, alias: string): Observable<Blob> {
    return this.httpClient.get<Blob>(`${this.API_PATH}/download-jks/${issuerAlias}/${alias}`,
      {responseType: 'blob' as 'json'})
      .pipe(catchError(() => of(null)));
  }

}
