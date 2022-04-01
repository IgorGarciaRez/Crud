import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastroCidadesComponent } from './cidade/cadastro-cidades/cadastro-cidades.component';
import { CidadesComponent } from './cidade/cidades/cidades.component';
import { CadastroPessoasComponent } from './pessoa/cadastro-pessoas/cadastro-pessoas.component';
import { PessoasComponent } from './pessoa/pessoas/pessoas.component';

const routes: Routes = [
  {path: 'cidades', component: CidadesComponent},
  {path: 'addcidades', component: CadastroCidadesComponent},
  {path: 'pessoas', component: PessoasComponent},
  {path: 'addpessoas', component: CadastroPessoasComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
