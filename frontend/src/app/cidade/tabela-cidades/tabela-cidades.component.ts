import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { LazyLoadEvent } from 'primeng/api';
import { Cidade } from '../cidade';
import { CidadeFiltro } from '../cidadeFiltro';
import { CidadesComponent } from '../cidades/cidades.component';

@Component({
  selector: 'app-tabela-cidades',
  templateUrl: './tabela-cidades.component.html',
  styleUrls: ['./tabela-cidades.component.css']
})
export class TabelaCidadesComponent {

  @ViewChild('grid') grid: any;

  @Input() size: any;
  @Input() registros!: number;

  @Input() filtro!:CidadeFiltro;
  @Input() cidades:Cidade[] = [];

  lazyLoadPage = 0;
  cidadeToDelete:any;

  @Output() onLazyLoad: EventEmitter<any> = new EventEmitter<any>();
  @Output() cidadeDelete: EventEmitter<any> = new EventEmitter<any>();

  handleLazyLoadCall(event: any){
    this.lazyLoadPage = event!.first! / event!.rows!;
    this.onLazyLoad.emit(this.lazyLoadPage);
  }

  handleDeletar(){
    this.cidadeDelete.emit(this.cidadeToDelete)
  }

  saveCidade2bDeleted(cidade:Cidade){
    this.cidadeToDelete = cidade;
    this.handleDeletar();
    this.grid.reset();
  }

}
