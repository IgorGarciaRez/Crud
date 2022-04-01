import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { MessageService } from 'primeng/api';
import { catchError, finalize, of, retry } from 'rxjs';
import { Pageable } from 'src/app/pageable';
import { Cidade } from '../cidade';
import { CidadeService } from '../cidade.service';
import { CidadeFiltro } from '../cidadeFiltro';
import { Estado } from '../estado';

@Component({
  selector: 'app-cidades',
  templateUrl: './cidades.component.html',
  styleUrls: ['./cidades.component.css']
})
export class CidadesComponent implements OnInit {

  @Input() cidades: Cidade[] = [];

  estado = new Estado();

  estados = [];

  estadoSelected:any = null;

  @Input() filtro = new CidadeFiltro();
  pageable = new Pageable();

  totalRegistros = 0;

  @BlockUI('lista-cidades') blockUI!: NgBlockUI;

  constructor(private cidadeService:CidadeService, private messageService: MessageService ) { }

  ngOnInit() {
    this.listar(0);
    this.estados = this.estado.estados;
  }

  listar(pagina:number){
    this.blockUI.start();
    this.filtro.estado = this.estadoSelected?.name;
    this.pageable.page = pagina;
    this.cidadeService.listar(this.filtro, this.pageable).pipe(finalize(() => this.blockUI.stop())).subscribe(data => {
      this.totalRegistros = data.totalElements;
      this.cidades = data.content;
    }),
    retry(3),
    catchError(error => {
      console.log('Não foi possível listar as cidades');
      return of(0);
    });
  }

  deletar(cidade: any){
    if(confirm(`Quer deletar a cidade ${cidade.nome}?`)){
      this.blockUI.start();
      this.cidadeService.deletar(cidade.id).pipe(finalize(() => this.blockUI.stop()))
      .subscribe(() => {
        this.messageService.add({key:'msg', severity:'success', summary:'Cidade deletada com sucesso'});
      }),
      retry(3),
      catchError(error => {
        this.messageService.add({key:'msg', severity:'error', summary:'Cidade não deletada'});
        return of(0);
      });
    }
  }

}