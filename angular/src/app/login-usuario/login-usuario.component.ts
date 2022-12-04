import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Datos } from 'app/shared/app.model';
import { ClienteUsersService } from 'app/shared/cliente-users.service';
import { DataService } from 'app/shared/data.service';

@Component({
  selector: 'app-login-usuario',
  templateUrl: './login-usuario.component.html',
  styleUrls: ['./login-usuario.component.css']
})
export class LoginUsuarioComponent implements OnInit {

  datosVacios = {
    email: "",
    password: "",
  };

  form = this.datosVacios as Datos; 
  mostrarMensaje!: boolean;
  mensaje!: string;

  constructor(private ruta: ActivatedRoute, private router: Router, private clienteUsers: ClienteUsersService, private datos: DataService) { }

  ngOnInit(): void {
  }

  onSubmit(){

    console.log("Enviado formulario");
    this.clienteUsers.loginUser(this.form).subscribe(

      resp => {
        if (resp.status < 400) {

          localStorage.setItem('TOKEN',resp.body);
        }
        
        this.router.navigate(['users']);

      },
      err => {
        this.mensaje = 'Datos introducidos erroneos';
        this.mostrarMensaje = true;
        console.log("Error al hacer submit " + err.message);
        throw err;
      }
    )
  }

}
