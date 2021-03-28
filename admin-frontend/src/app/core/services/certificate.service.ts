import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {CertificateInfo} from '../model/certificate-info';
import {asLiteral} from '@angular/compiler/src/render3/view/util';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  ca: BehaviorSubject<CertificateInfo | null> = new BehaviorSubject<CertificateInfo>(null);
  certificates: CertificateInfo[] = [];

  constructor(private httpClient: HttpClient) { }

  getCertificates(page, size): Observable<any> {
    return this.httpClient.get(`api/certificates?page=${page}&size=${size}`);
  }

  createCertificate(certificate: CertificateInfo): Observable<any> {
    return this.httpClient.post('api/certificates', certificate);
  }

  getByAlias(alias: string): Observable<any> {
    return this.httpClient.get(`/api/certificates/alias/${alias}`);
  }
}
