import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PAGE_SIZE } from 'src/app/constants/pagination';
import { Message } from 'src/app/models/message';
import { MessageSearch } from 'src/app/models/message-search';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(
    private http: HttpClient
  ) { }

  private refreshData: Subject<null> = new Subject();
  refreshData$: Observable<null> = this.refreshData.asObservable();

  findAll(page: number, search: MessageSearch): Observable<HttpResponse<Message[]>>{
    const params = new HttpParams().set('page', page + '').set('size', PAGE_SIZE + '');
    return this.http.post<Message[]>(`${environment.messagesApi}/search`, search, {observe: 'response', params}).pipe(
      catchError(() => of(null))
    );
  }

  announceRefreshData(): void{
    this.refreshData.next();
  }

}
