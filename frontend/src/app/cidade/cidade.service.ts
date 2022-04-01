import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Pageable } from '../pageable';
import { RequestUtil } from '../request-util';
import { Cidade } from './cidade';
import { CidadeFiltro } from './cidadeFiltro';

@Injectable({
  providedIn: 'root'
})
export class CidadeService {

  apiUrl = environment.apiUrl;
  cidadesUrl = environment.slashApi + '/cidades';

  constructor(private http: HttpClient) { }

  listar(filtro: CidadeFiltro, pageable: Pageable): Observable<any>{
    const options = RequestUtil.buildOptions(Object.assign(filtro, pageable));
    return this.http.get<any>(`${this.cidadesUrl}`, options);
  }

  listarAll(){
    return this.http.get<any>(this.cidadesUrl);
  }

  listarId(id: number){
    return this.http.get<any>(this.cidadesUrl+`/${id}`);
  }

  salvar(cidade:Cidade): Observable<any>{
    return this.http.post<Cidade>(this.cidadesUrl, cidade);
  }

  deletar(id: number): Observable<any>{
    return this.http.delete<Cidade>(this.cidadesUrl + `/delete/${id}`)
  }

  atualizar(cidade: Cidade): Observable<any>{
    return this.http.put(this.cidadesUrl+`/put/${cidade.id}`, cidade);
  }

}
