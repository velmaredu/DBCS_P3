import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ListarUsuarioComponent } from './listar-usuario/listar-usuario.component';
import { EditarUsuarioComponent } from './editar-usuario/editar-usuario.component';


const routes: Routes = [
  {path : 'users', component:ListarUsuarioComponent},
  {path: 'users/:id/editar', component:EditarUsuarioComponent},
  {path: 'users/nuevo', component:EditarUsuarioComponent},
  {path: '**', redirectTo:'users', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
