import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ToastModule} from 'primeng/toast';

import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { CidadeModule } from './cidade/cidade.module';
import { PessoaModule } from './pessoa/pessoa.module';
import { CoreModule } from './core/core.module';
import { MessageService } from 'primeng/api';
import { CidadeService } from './cidade/cidade.service';
import { PessoaService } from './pessoa/pessoa.service';
import { RouterModule, Routes } from '@angular/router';
import { CidadesComponent } from './cidade/cidades/cidades.component';
import { CadastroCidadesComponent } from './cidade/cadastro-cidades/cadastro-cidades.component';
import { PessoasComponent } from './pessoa/pessoas/pessoas.component';
import { CadastroPessoasComponent } from './pessoa/cadastro-pessoas/cadastro-pessoas.component';
import { NotfoundComponent } from './core/notfound.component';

const routes: Routes = [
  { path: '', redirectTo:'cidades', pathMatch:'full' },
  { path:'notfound', component: NotfoundComponent },
  { path:'cidades', component: CidadesComponent },
  { path:'cidades/new', component: CadastroCidadesComponent },
  { path:'cidades/:id', component: CadastroCidadesComponent },
  { path:'pessoas', component: PessoasComponent },
  { path:'pessoas/new', component: CadastroPessoasComponent },
  { path:'pessoas/:id', component: CadastroPessoasComponent },
  { path: '**', redirectTo:'notfound', pathMatch:'full' }
]

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ToastModule,
    RouterModule.forRoot(routes),

    CidadeModule,
    PessoaModule,
    CoreModule

  ],
  providers: [
    CidadeService,
    PessoaService,
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}