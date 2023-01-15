import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Booking, Status } from './app.model';

@Injectable({
  providedIn: 'root'
})
export class ReservasApiRestService {

  constructor(private http: HttpClient) { }

  private static readonly BASE_URI = 'http://localhost:8082/book/'

  getAvailability(startDate: String, endDate: String): Observable<HttpResponse<Number>> {
    let url = ReservasApiRestService.BASE_URI + "?startDate=" + startDate + "&endDate=" + endDate;
    return this.http.get<Number>(url,{observe : 'response'});
  }

  getBookings(status: Status, id: String, startDate: String, endDate: String): Observable<HttpResponse<Booking[]>> {
    let url = ReservasApiRestService.BASE_URI + "?mode=0" + "&status=" + status + "&guestID=" + id 
                                    + "?startDate=" + startDate + "&endDate=" + endDate;
    return this.http.get<Booking[]>(url, {observe: 'response'});
  }

  getBookingsHost(status: Status, startDate: String, endDate: String): Observable<HttpResponse<Booking[]>> {
    let url = ReservasApiRestService.BASE_URI + "?mode=1" + "&status=" + status + 
                                "&startDate=" + startDate + "&endDate=" + endDate;
    return this.http.get<Booking[]>(url, {observe: 'response'});
  }

  getBook(id: String): Observable<HttpResponse<Booking>> {
    let url = ReservasApiRestService.BASE_URI + id;
    return this.http.get<Booking>(url, { observe: 'response' });
  }

  modificarBook(book: Booking) : Observable<HttpResponse<any>> {
    let url = ReservasApiRestService.BASE_URI + book.id;
    return this.http.put(url,book,{observe: 'response', responseType: 'text'});
  }

  crearBook(book: Booking): Observable<HttpResponse<any>> {
    let url = ReservasApiRestService.BASE_URI;
    return this.http.post(url, book, { observe: 'response', responseType: 'text'});
  }
}
