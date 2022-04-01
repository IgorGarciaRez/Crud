import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CidadeFiltro } from '../cidade/cidadeFiltro';
import { Pageable } from '../pageable';
import { RequestUtil } from '../request-util';
import { Pessoa } from './pessoa';
import { PessoaFiltro } from './pessoaFiltro';

@Injectable({
  providedIn: 'root'
})
export class PessoaService {

  apiUrl = environment.apiUrl;
  pessoasUrl = environment.slashApi + '/pessoas';

  constructor(private http: HttpClient) { }

  listar(filtro: PessoaFiltro, pageable: Pageable): Observable<any>{
    const options = RequestUtil.buildOptions(Object.assign(filtro, pageable));
    return this.http.get<any>(`${this.pessoasUrl}`, options);
  }

  listarId(id: number): Observable<any>{
    return this.http.get<any>(this.pessoasUrl+`/${id}`)
  }

  salvar(pessoa:Pessoa): Observable<any>{
    return this.http.post<Pessoa>(this.pessoasUrl, pessoa);
  }

  deletar(id: number): Observable<any>{
    return this.http.delete<Pessoa>(this.pessoasUrl + `/delete/${id}`);
  }

  atualizar(pessoa: Pessoa): Observable<any>{
    return this.http.put<Pessoa>(this.pessoasUrl + `/put/${pessoa.id}`, pessoa);
  }

  pdf(id:number): Observable<Pessoa>{
    return this.http.get<Pessoa>(this.pessoasUrl + `/pdf/${id}`, {responseType: 'blob' as 'json'});
  }

  xls(): Observable<any>{
    return this.http.get<any>(this.pessoasUrl + '/xls', {responseType: 'blob' as 'json'})
  }

}