import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Role } from './shared/app.model';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(public jwtHelper: JwtHelperService) { }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('TOKEN');
    // Check whether the token is expired and return
    // true or false
    return token == null ? false : !this.jwtHelper.isTokenExpired(token);
  }

  public getRole(): string {
    const token = localStorage.getItem('TOKEN');
    let decodedJWT;
    token == null ? false :  decodedJWT = JSON.parse(window.atob(token.split('.')[1]));
    return decodedJWT.role;


  }
}
