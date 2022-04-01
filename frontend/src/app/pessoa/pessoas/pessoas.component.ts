import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { MessageService } from 'primeng/api';
import { catchError, finalize, of, retry } from 'rxjs';
import { Pageable } from 'src/app/pageable';
import { PessoaService } from '../pessoa.service';
import { PessoaFiltro } from '../pessoaFiltro';

@Component({
  selector: 'app-pessoas',
  templateUrl: './pessoas.component.html',
  styleUrls: ['./pessoas.component.css']
})
export class PessoasComponent implements OnInit{

  constructor(private pessoaService:PessoaService, private messageService: MessageService ) { }

  @Input() pessoas: any[] = [];

  @Input() filtro = new PessoaFiltro();
  pageable = new Pageable();

  totalRegistros = 0;

  @BlockUI('lista-pessoas') blockUI!: NgBlockUI;

  ngOnInit() {
    this.listar(0);
  }

  listar(pagina:number){
    this.blockUI.start();
    this.pageable.page = pagina;
    this.pessoaService.listar(this.filtro, this.pageable).pipe(finalize(() => this.blockUI.stop())).subscribe(data => {
      this.totalRegistros = data.totalElements;
      this.pessoas = data.content;
    }),
    retry(3),
    catchError(error => {
      console.log('Não foi possível listar as pessoas');
      return of(0);
    });
  }

  deletar(pessoa: any){
    if(confirm(`Quer deletar a pessoa ${pessoa.nome}?`)){
      this.blockUI.start();
      this.pessoaService.deletar(pessoa.id).pipe(finalize(() => {this.blockUI.stop()}))
      .subscribe(() =>{
        this.messageService.add({key:'msg', severity:'success', summary:'Pessoa deletada com sucesso'});
      }),
      retry(3),
      catchError(error => {
        this.messageService.add({key:'msg', severity:'error', summary:'Pessoa não deletada'});
        return of(0);
      });
    }
  }

  pdf(pessoa:any){
    this.blockUI.start();
    this.pessoaService.pdf(pessoa.id).pipe(finalize(() => this.blockUI.stop())).subscribe((data: any) =>{
      var file = new Blob([data], { type: 'application/pdf' })
      var fileURL = URL.createObjectURL(file);
      var a = document.createElement('a');
      a.href = fileURL;
      a.download = pessoa.nome+'.pdf';
      document.body.appendChild(a);
      a.click();
    }),
    retry(3),
    catchError(error => {
      console.log('Não foi possível criar um pdf da pessoa');
      return of(0);
    });
  }

  xls(){
    console.log('teste')
    this.blockUI.start();
    this.pessoaService.xls().pipe(finalize(() => this.blockUI.stop())).subscribe((data: any) =>{
      var file = new Blob([data], { type: 'application/xls' })
      var fileURL = URL.createObjectURL(file);
      console.log(data);
      var a = document.createElement('a');
      a.href = fileURL;
      a.download = 'planilha-pessoas.xls';
      document.body.appendChild(a);
      a.click();
    }),
    retry(3),
    catchError(error => {
      console.log('Não foi possível criar um xls das pessoas');
      return of(0);
    });
  }

}
