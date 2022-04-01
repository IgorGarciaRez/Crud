import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { MessageService } from 'primeng/api';
import { catchError, finalize, of, retry } from 'rxjs';
import { CidadeService } from 'src/app/cidade/cidade.service';
import { Pessoa } from '../pessoa';
import { PessoaService } from '../pessoa.service';

@Component({
  selector: 'app-cadastro-pessoas',
  templateUrl: './cadastro-pessoas.component.html',
  styleUrls: ['./cadastro-pessoas.component.css']
})
export class CadastroPessoasComponent implements OnInit {

  cidadesD: any[] =[];

  pessoa: Pessoa = new Pessoa();

  cidadeSelecionada:any;

  @BlockUI('lista-cidades-dropdown') blockUI!: NgBlockUI;
  @BlockUI('cadastro-pessoas') blockUIP!: NgBlockUI;

  constructor( private cidadeService: CidadeService,
    private pessoaService: PessoaService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private router: Router ){}

  ngOnInit(): void {
    this.blockUIP.start();
    const id = this.route.snapshot.params['id'];
    if(id){
      this.carregarPessoa(id);
    }
    this.listarCidades();
    this.blockUIP.stop();
  }

  carregarPessoa(id: number){
    this.pessoaService.listarId(id).pipe(finalize(() => this.blockUIP.stop))
      .subscribe(data =>{
        this.cidadeSelecionada = data.cidadeDTO;
        delete data.cidadeDTO;
        this.pessoa = data;
      }),
      retry(3),
      catchError(error => {
        this.messageService.add({key:'msg', severity:'error', summary:'Pessoa não encontrada'});
        return of(0);
      });
  }

  get editando(){
    return Boolean(this.pessoa.id);
  }

  listarCidades(){
    this.blockUI.start();
    this.cidadeService.listarAll().pipe(finalize(() => {this.blockUI.stop()}))
    .subscribe(data => {
      const contentKey:any = Object.values(data)[0];
      this.cidadesD = contentKey;
    }),
    retry(3),
    catchError(error => {
      console.error('Cidades não encontradas');
      return of(0);
    });
  }

  salvar(form: NgForm){
    if(this.editando){
      this.atualizar(form);
    }else{
      this.adicionar(form);
    }
  }

  adicionar(form: NgForm){
    this.blockUIP.start();
    this.pessoa.cidade = this.cidadeSelecionada;
    this.pessoaService.salvar(this.pessoa).pipe(finalize(() => this.blockUIP.stop())).subscribe(() => {
      form.reset();
      this.messageService.add({key:'msg', severity:'success', summary:'Pessoa criada com sucesso'});
    }),
    retry(3),
    catchError(error => {
      this.messageService.add({key:'msg', severity:'error', summary:'Pessoa não criada'});
      return of(0);
    });
  }

  atualizar(form: NgForm){
    this.pessoa.cidade = this.cidadeSelecionada;

    this.blockUIP.start();
    this.pessoaService.atualizar(this.pessoa).pipe(finalize(() => this.blockUIP.stop())).subscribe(() => {
      this.messageService.add({key:'msg', severity:'success', summary:'Pessoa atualizada com sucesso'});
    }),
    retry(3),
    catchError(error => {
      this.messageService.add({key:'msg', severity:'error', summary:'Pessoa não atualizada'});
      return of(0);
    });
  }

  novo(form: NgForm){
    form.reset();
    this.pessoa = new Pessoa();
    this.router.navigate(['/pessoas/new']);
  }

}
