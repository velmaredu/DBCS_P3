import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservasApiRestService {

  constructor(private http: HttpClient) { }

  private static readonly BASE_URI = 'http://localhost:8083/book/'

  getAvailability(startDate: String, endDate: String): Observable<HttpResponse<>> {

    let url = ReservasApiRestService.BASE_URI +;
    return this.http.get<>(url,{observe : 'response'});
  }

  getBookings()

  modificarBook()

  crearBook()
}
