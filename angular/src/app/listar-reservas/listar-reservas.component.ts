import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Booking, Status } from 'app/shared/app.model';
import { DataService } from 'app/shared/data.service';
import { ReservasApiRestService } from 'app/shared/reservas-api-rest.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-listar-reservas',
  templateUrl: './listar-reservas.component.html',
  styleUrls: ['./listar-reservas.component.css']
})
export class ListarReservasComponent implements OnInit {

  mostrarMensaje!: boolean;
  mensaje!: string;
  role!: string;
  id!: string;
  books!: Booking[];
  fechaInicio!: String;
  fechaFinal!: String;
  estado!: Status

  constructor(public auth: AuthService, private clienteReservas: ReservasApiRestService, private datos: DataService,private ruta:ActivatedRoute, private router:Router) { }

  ngOnInit(): void {

    this.getReservas()
  }


  getReservas() {

    this.role = this.auth.getRole();
    this.id = this.auth.getId();

    if(this.role == "GUEST"){
      this.clienteReservas.getBookings(this.estado,this.id,this.fechaInicio,this.fechaFinal).subscribe(
        resp => {
          if(resp.status < 400) {
            this.books = resp.body!;
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
    } else {
      this.clienteReservas.getBookingsHost(this.estado,this.fechaInicio,this.fechaFinal).subscribe(
        resp => {
          if(resp.status < 400) {
            this.books = resp.body!;
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

  }

  cancelarReserva(book: Booking ){

    book.status = Status.CANCELED;
    
    this.clienteReservas.modificarBook(book).subscribe(
      resp => {
        if (resp.status < 400) {

          this.datos.cambiarMostrarMensaje(true);
          this.datos.cambiarMensaje(resp.body);
        
        } else {
          this.datos.cambiarMostrarMensaje(true);
          this.datos.cambiarMensaje("Error al modificar comentario");
        }

      },
      err => {
        console.log("Error al editar " + err.message);
        throw err;
      }
    )
  }

 cambiarEstado(status: Status){
    this.estado = status;
 }

  confirmarReserva(book: Booking){
    book.status = Status.CONFIRMED;
    
    this.clienteReservas.modificarBook(book).subscribe(
      resp => {
        if (resp.status < 400) {

          this.datos.cambiarMostrarMensaje(true);
          this.datos.cambiarMensaje(resp.body);
        
        } else {
          this.datos.cambiarMostrarMensaje(true);
          this.datos.cambiarMensaje("Error al modificar comentario");
        }

      },
      err => {
        console.log("Error al editar " + err.message);
        throw err;
      }
    )
  }
}
