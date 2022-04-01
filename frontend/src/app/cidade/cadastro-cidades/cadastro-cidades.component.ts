import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { MessageService } from 'primeng/api';
import { catchError, empty, finalize, of, retry } from 'rxjs';
import { Cidade } from '../cidade';
import { CidadeService } from '../cidade.service';
import { Estado } from '../estado';

@Component({
  selector: 'app-cadastro-cidades',
  templateUrl: './cadastro-cidades.component.html',
  styleUrls: ['./cadastro-cidades.component.css']
})
export class CadastroCidadesComponent implements OnInit {

  @BlockUI('cadastro-cidades') blockUI!: NgBlockUI;

  estado = new Estado();
  estados = [];
  estadoSelecionado: any;

  cidade: Cidade = new Cidade();

  constructor( private cidadeService: CidadeService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private router: Router ) { }

  ngOnInit(): void {
    this.blockUI.start();
    this.estados = this.estado.estados;
    const id = this.route.snapshot.params['id'];
    if(id){
      this.carregarCidade(id);
    }
    this.blockUI.stop();
  }

  carregarCidade(id: number){
    this.cidadeService.listarId(id).pipe(finalize(() => this.blockUI.stop())).subscribe(data => {
      const jsonStr = `{"name":"${data.estado}"}`
      this.estadoSelecionado = JSON.parse(jsonStr);
      this.cidade = data;
    }),
    retry(3),
    catchError(error => {
      this.messageService.add({key:'msg', severity:'error', summary:'Cidade não encontrada'});
      return of(0);
    });
  }

  get editando(){
    return Boolean(this.cidade.id);
  }

  salvar(form: NgForm){
    if(this.editando)
    this.atualizar(form);
    else
    this.adicionar(form);
  }

  adicionar(form: NgForm){
    this.blockUI.start();
    console.log(this.estadoSelecionado)
    this.cidade.estado = this.estadoSelecionado.name;
    this.cidadeService.salvar(this.cidade).pipe(finalize(() => this.blockUI.stop())).subscribe(() => {
      form.reset();
      this.messageService.add({key:'msg', severity:'success', summary:'Cidade criada com sucesso'});
    }),
    retry(3),
    catchError(error =>{
      this.messageService.add({key:'msg', severity:'error', summary:'Cidade não criada'});
      return of(0);
    });
  }

  atualizar(form: NgForm){
    this.cidade.estado = this.estadoSelecionado.name;
    this.blockUI.start();
    this.cidadeService.atualizar(this.cidade).pipe(finalize(() => this.blockUI.stop())).subscribe(() => {
      this.messageService.add({key:'msg', severity:'success', summary:'Cidade atualizada com sucesso'});
    }),
    retry(3),
    catchError(error =>{
      this.messageService.add({key:'msg', severity:'error', summary:'Cidade não atualizada'});
      return of(0);
    });
  }

  nova(form: NgForm){
    form.reset();
    this.cidade = new Cidade();
    this.router.navigate(['/cidades/new']);
  }

}
