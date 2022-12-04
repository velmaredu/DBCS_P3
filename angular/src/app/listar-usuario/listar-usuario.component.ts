import { Component, OnInit } from '@angular/core';
import { User } from 'app/shared/app.model';
import { DataService } from 'app/shared/data.service';
import { ClienteUsersService } from 'app/shared/cliente-users.service';
import { HtmlParser } from '@angular/compiler';
import { LoginUsuarioComponent } from 'app/login-usuario/login-usuario.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-listar-usuario',
  templateUrl: './listar-usuario.component.html',
  styleUrls: ['./listar-usuario.component.css']
})
export class ListarUsuarioComponent implements OnInit {

  users!: User[];
  mostrarMensaje!: boolean;
  mensaje!: string;

  constructor(private clienteUser: ClienteUsersService, private datos: DataService,private ruta:ActivatedRoute, private router:Router, ) { }

  ngOnInit(): void {

    this.getUsers_Acceso();
  }

  getUsers_Acceso(){

    this.clienteUser.getAllUsers().subscribe(
      resp => {
        if(resp.status < 400) {
          this.users = resp.body!;
        } else {
          this.mensaje = 'Error al acceder a los datos';
          this.mostrarMensaje = true;
        }
      },
      err => {
        console.log("Error al traer la lista: " + err.message);
        throw err;
      }
    )
  }

  borrar(id: Number){
    this.clienteUser.borrarUser(String(id)).subscribe(
      resp => {
        if (resp.status < 400) {

          this.mostrarMensaje = true;
          this.mensaje = resp.body;
          this.getUsers_Acceso();

        } else {

          this.mostrarMensaje = true;
          this.mensaje = "Error al eliminar el registro";

        }

      },
      err => {
        console.log("Error al borrar: " + err.message);
        throw err;
      }
    )
  }

  filtrarEnabled(filtro: Boolean){

    this.clienteUser.getUsersEnabled(String(filtro)).subscribe(
      resp => {
        if(resp.status < 400){
          this.users = resp.body!;
        } else {
          this.mensaje = 'Error al acceder a los datos';
          this.mostrarMensaje = true;
        }
      },
      err => {
        console.log("Error al traer la lista: " + err.message);
        throw err;
      }
    )
  }

  logOut(){

    localStorage.clear();
    this.router.navigate(['login']);
  }

  activarSeleccionados(){

    var ids = "";
    var inputElements = document.getElementsByClassName('form-check-input');

    for(var i=0; i < inputElements.length ; ++i){
      if((inputElements[i] as HTMLInputElement).checked ){
          ids += + inputElements[i].id + ",";
      }
    }

  

    this.clienteUser.enableUsers(ids.substring(0,ids.length -1)).subscribe(
      resp => {
        if(resp.status < 400){
          
          this.mostrarMensaje = true;
          this.mensaje = String(resp.body);

        } else {
          this.mensaje = 'Error al modificar los datos';
          this.mostrarMensaje = true;
        }
      },
      err => {
        console.log("Error al traer la lista: " + err.message);
        throw err;
      }
    )


    this.getUsers_Acceso();

  }

  desactivarSeleccionados(){

    var ids = "";
    var inputElements = document.getElementsByClassName('form-check-input');

    for(var i=0; i < inputElements.length; ++i){
      if((inputElements[i] as HTMLInputElement).checked ){
          ids +=  inputElements[i].id + ",";
      }
    }

    this.clienteUser.disableUsers(ids.substring(0,ids.length - 1)).subscribe(
      resp => {
        if(resp.status < 400){
          
          this.mostrarMensaje = true;
          this.mensaje = resp.body;

        } else {
          this.mensaje = 'Error al modificar los datos';
          this.mostrarMensaje = true;
        }
      },
      err => {
        console.log("Error al traer la lista: " + err.message);
        throw err;
      }
    )

    this.getUsers_Acceso();


  }

}
