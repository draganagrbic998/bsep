import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/constants/pagination';
import { Message } from 'src/app/models/message';
import { MessageSearch } from 'src/app/models/message-search';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_PATH = 'api/messages';

  findAll(page: number, search: MessageSearch): Observable<HttpResponse<Message[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.post<Message[]>(`${this.API_PATH}/search`, search, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

}
