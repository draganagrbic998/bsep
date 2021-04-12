import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {Configuration} from '../model/configuration';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  private readonly API = '/api/configuration';

  configuration: BehaviorSubject<Configuration | null> = new BehaviorSubject<Configuration>(null);

  constructor(private httpClient: HttpClient) { }

  connect(hospitalApi: string): Observable<Configuration> {
    return this.httpClient.post<Configuration>(this.API, {url: `https://${hospitalApi}`});
  }

  save(hospitalApi: string, configuration: Configuration): Observable<null> {
    return this.httpClient.put<null>(this.API, {url: `https://${hospitalApi}`, configuration});
  }

}
