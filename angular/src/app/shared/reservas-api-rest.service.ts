import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Book, Status } from './app.model';

@Injectable({
  providedIn: 'root'
})
export class ReservasApiRestService {

  constructor(private http: HttpClient) { }

  private static readonly BASE_URI = 'http://localhost:8083/book/'

  getAvailability(startDate: String, endDate: String): Observable<HttpResponse<Number>> {
    let url = ReservasApiRestService.BASE_URI + "?startDate=" + startDate + "&endDate=" + endDate;
    return this.http.get<Number>(url,{observe : 'response'});
  }

  getBookings(mode: String,status: Status, id: String): Observable<HttpResponse<Book[]>> {
    let url = ReservasApiRestService.BASE_URI + "?mode=" + mode + "&=status" + status + "&guestID=" + id ;
    return this.http.get<Book[]>(url, {observe: 'response'});
  }

  getBookingsHost(mode: String,status: Status, startDate: String, endDate: String): Observable<HttpResponse<Book[]>> {
    let url = ReservasApiRestService.BASE_URI + "?mode=" + mode + "&=status" + status + 
                                "&startDate=" + startDate + "&endDate=" + endDate;
    return this.http.get<Book[]>(url, {observe: 'response'});
  }

  getBook(id: String): Observable<HttpResponse<Book>> {
    let url = ReservasApiRestService.BASE_URI + id;
    return this.http.get<Book>(url, { observe: 'response' });
  }

  modificarBook(id: String, book: Book) : Observable<HttpResponse<any>> {
    let url = ReservasApiRestService.BASE_URI + id;
    return this.http.put(url,book,{observe: 'response', responseType: 'text'});
  }

  crearBook(book: Book): Observable<HttpResponse<any>> {
    let url = ReservasApiRestService.BASE_URI;
    return this.http.post(url, book, { observe: 'response', responseType: 'text'});
  }
}
