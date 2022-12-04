import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EditarUsuarioComponent } from './editar-usuario/editar-usuario.component';
import { ListarUsuarioComponent } from './listar-usuario/listar-usuario.component';

import { ClienteUsersService } from './shared/cliente-users.service';
import { DataService } from './shared/data.service';
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from '@angular/forms';
import { LoginUsuarioComponent } from './login-usuario/login-usuario.component';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';

@NgModule({
  declarations: [
    AppComponent,
    EditarUsuarioComponent,
    ListarUsuarioComponent,
    LoginUsuarioComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    ClienteUsersService,
    DataService,
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
