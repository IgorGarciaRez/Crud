import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CadastroCidadesComponent } from './cadastro-cidades/cadastro-cidades.component';
import { TabelaCidadesComponent } from './tabela-cidades/tabela-cidades.component';
import { CidadesComponent } from './cidades/cidades.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { CascadeSelectModule } from 'primeng/cascadeselect';
import { DropdownModule } from 'primeng/dropdown';
import { SharedModule } from '../shared/shared.module';
import { TooltipModule } from 'primeng/tooltip';

import { HttpClientModule } from '@angular/common/http';
import { BlockUIModule } from 'ng-block-ui';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    CadastroCidadesComponent,
    TabelaCidadesComponent,
    CidadesComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BlockUIModule,
    FormsModule,
    InputTextModule,
    ButtonModule,
    TableModule,
    CascadeSelectModule,
    DropdownModule,
    InputNumberModule,
    SharedModule,
    TooltipModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule
  ],
  exports: [
  ]
})
export class CidadeModule { }
