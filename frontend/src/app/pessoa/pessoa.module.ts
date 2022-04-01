import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CadastroPessoasComponent } from './cadastro-pessoas/cadastro-pessoas.component';
import { PessoasComponent } from './pessoas/pessoas.component';
import { TabelaPessoasComponent } from './tabela-pessoas/tabela-pessoas.component';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { CascadeSelectModule } from 'primeng/cascadeselect';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';
import { SharedModule } from '../shared/shared.module';
import { TooltipModule } from 'primeng/tooltip';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BlockUIModule } from 'ng-block-ui';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [
    CadastroPessoasComponent,
    TabelaPessoasComponent,
    PessoasComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BlockUIModule.forRoot(),
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
export class PessoaModule { }
