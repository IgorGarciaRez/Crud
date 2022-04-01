import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Pessoa } from '../pessoa';
import { PessoaFiltro } from '../pessoaFiltro';
import { PessoasComponent } from '../pessoas/pessoas.component';

@Component({
  selector: 'app-tabela-pessoas',
  templateUrl: './tabela-pessoas.component.html',
  styleUrls: ['./tabela-pessoas.component.css']
})
export class TabelaPessoasComponent {

  @ViewChild('grid') grid: any;

  @Input() size: any;
  @Input() registros!: number;

  @Input() filtro!:PessoaFiltro;
  @Input() pessoas:Pessoa[] = [];

  lazyLoadPage = 0;
  pessoaToDelete!: Pessoa;
  pessoaVarToPdf!: Pessoa;

  @Output() onLazyLoad: EventEmitter<any> = new EventEmitter<any>();
  @Output() pessoaDelete: EventEmitter<any> = new EventEmitter<any>();
  @Output() pessoaToPdf: EventEmitter<any> = new EventEmitter<any>();

  handleLazyLoadCall(event: any){
    this.lazyLoadPage = event!.first! / event!.rows!;
    this.onLazyLoad.emit(this.lazyLoadPage);
  }

  handleDeletar(){
    this.pessoaDelete.emit(this.pessoaToDelete);
  }

  handleToPdf(){
    this.pessoaToPdf.emit(this.pessoaVarToPdf);
  }

  pessoa2BDeleted(pessoa:Pessoa){
    this.pessoaToDelete = pessoa;
    this.handleDeletar();
    this.grid.reset();
  }

  pessoa2Pdf(pessoa:Pessoa){
    this.pessoaVarToPdf = pessoa;
    this.handleToPdf();
  }

}