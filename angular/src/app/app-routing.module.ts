import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ListarUsuarioComponent } from './listar-usuario/listar-usuario.component';
import { EditarUsuarioComponent } from './editar-usuario/editar-usuario.component';
import { LoginUsuarioComponent } from './login-usuario/login-usuario.component';
import { AuthGuardService as AuthGuard } from './auth-guard.service';
import { ListarReservasComponent } from './listar-reservas/listar-reservas.component';
import { CrearReservaComponent } from './crear-reserva/crear-reserva.component';

const routes: Routes = [
  {path : 'users', component:ListarUsuarioComponent,canActivate:[AuthGuard]},
  {path: 'users/:id/editar', component:EditarUsuarioComponent,canActivate:[AuthGuard]},
  {path: 'users/nuevo', component:EditarUsuarioComponent,canActivate:[AuthGuard]},
  {path: 'login',component:LoginUsuarioComponent},
  {path : 'book', component:ListarReservasComponent},
  {path : 'book/nuevo', component:CrearReservaComponent},
  /*{path : 'book/:id/editar', component:CrearReservaComponent,canActivate:[AuthGuard]},*/
  {path: '**', redirectTo:'users', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
