import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { User } from 'app/shared/app.model';
import { Role } from 'app/shared/app.model';
import { ClienteUsersService } from 'app/shared/cliente-users.service';
import { DataService } from 'app/shared/data.service';


@Component({
  selector: 'app-editar-usuario',
  templateUrl: './editar-usuario.component.html',
  styleUrls: ['./editar-usuario.component.css']
})
export class EditarUsuarioComponent implements OnInit {

  userVacio = {
    id: 0,
    createdAt: "",
    email: "",
    enabled: true,
    firstName: "",
    lastName: "",
    name: "",
    password: "",
    role: 0,
    updatedAt: "",
  };

  user = this.userVacio as User;
  operacion!: String;
  id!: String;

  constructor(private ruta: ActivatedRoute, private router: Router, private clienteUsers: ClienteUsersService, private datos: DataService) { }

  ngOnInit() {
    console.log("En editar-usuario");

    this.operacion = this.ruta.snapshot.url[this.ruta.snapshot.url.length -1].path;
    if(this.operacion == "editar"){

      console.log("En editar");
      this.ruta.paramMap.subscribe(
        params=> {
          this.id = params.get('id')!;
        },
        err => console.log("Error al leer id para editar: " + err)
      )

      this.clienteUsers.getUser(this.id).subscribe(

        resp => {
          this.user = resp.body!;
        },
        err => {
          console.log("error al traer el usuario " + err.message);
          throw err;
        }
      )
    }
  }

  onSubmit() {
    console.log("Enviado formulario");
    if(this.id) {

      this.clienteUsers.modificarUser(String(this.user.id), this.user).subscribe(
        resp => {
          if (resp.status < 400) {

            this.datos.cambiarMostrarMensaje(true);
            this.datos.cambiarMensaje(resp.body);
          
          } else {
            this.datos.cambiarMostrarMensaje(true);
            this.datos.cambiarMensaje("Error al modificar comentario");
          }
          this.router.navigate(['users']);

        },
        err => {
          console.log("Error al editar " + err.message);
          throw err;
        }
      )
    } else {
      this.clienteUsers.anadirUser(this.user).subscribe(
        resp => {
          if(resp.status < 400) {
            this.datos.cambiarMostrarMensaje(true);
            this.datos.cambiarMensaje(resp.body);

          } else {
            this.datos.cambiarMostrarMensaje(true);
            this.datos.cambiarMensaje("Error al añadir usuario");
          }
          this.router.navigate(['users']);
        },
        err => {
          console.log("Error al editar: " + err.message);
          throw err;
        }
      )
    }
  }
}
