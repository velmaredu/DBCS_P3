import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { User } from './app.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClienteUsersService {

  private static readonly BASE_URI = 'http://localhost:8080/users/'

  constructor(private http: HttpClient) { }
  
  getAllUsers(): Observable<HttpResponse<User[]>> {
    let url = ClienteUsersService.BASE_URI;
    return this.http.get<User[]>(url, {observe : 'response'});
  } 

  borrarUser(id: String): Observable<HttpResponse<any>> {
    let url = ClienteUsersService.BASE_URI + id;
    return this.http.delete(url, { observe: 'response', responseType: 'text'});
  }

  anadirUser(User: User): Observable<HttpResponse<any>> {
    let url = ClienteUsersService.BASE_URI;
    return this.http.post(url, User, { observe: 'response', responseType: 'text'});
  }

  getUser(id: String): Observable<HttpResponse<User>> {
    let url = ClienteUsersService.BASE_URI + id;
    return this.http.get<User>(url, { observe: 'response' });
  }

  getUsersEnabled(filtro: String): Observable<HttpResponse<User[]>>{
    let url = ClienteUsersService.BASE_URI + "?enable=" + filtro;
    return this.http.get<User[]>(url, { observe: 'response' });
  }

  modificarUser(id: String, user: User): Observable<HttpResponse<any>> {
    let url = ClienteUsersService.BASE_URI + id;
    return this.http.put(url,user,{observe: 'response', responseType: 'text'});
  }

  enableUsers(ids: string): Observable<HttpResponse<any>> {
    let url = ClienteUsersService.BASE_URI + "enable?user_id=" + ids;
    return this.http.put(url,{},{observe: 'response', responseType: 'text'});
  }

  disableUsers(ids: string): Observable<HttpResponse<any>> {
    let url = ClienteUsersService.BASE_URI + "disable?user_id=" + ids;
    return this.http.put(url,{},{observe: 'response', responseType: 'text'});
  }
}
