import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {CertificateInfo} from '../model/certificate-info';
import {asLiteral} from '@angular/compiler/src/render3/view/util';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  ca: CertificateInfo;
  certificates: CertificateInfo[] = [];

  constructor(private httpClient: HttpClient) { }

  getCertificates(): Observable<any> {
    return this.httpClient.get('api/certificates');
  }

  createCertificate(certificate: CertificateInfo): Observable<any> {
    return this.httpClient.post('api/certificates', certificate);
  }

  getByAlias(alias: string): Observable<any> {
    return this.httpClient.get(`/api/certificates/alias/${alias}`);
  }
}
