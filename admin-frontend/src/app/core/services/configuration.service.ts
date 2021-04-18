import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Configuration } from '../model/configuration';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  private readonly API_PATH = '/api/configuration';

  constructor(private httpClient: HttpClient) { }

  connect(hospitalApi: string): Observable<Configuration> {
    return this.httpClient.post<Configuration>(this.API_PATH, {url: `https://${hospitalApi}`}).pipe(
      catchError(() => of(null))
    );
  }

  save(hospitalApi: string, configuration: Configuration): Observable<boolean> {
    return this.httpClient.put<null>(this.API_PATH, {url: `https://${hospitalApi}`, configuration}).pipe(
      map(() => true),
      catchError(() => of(false))
    );
  }

}
