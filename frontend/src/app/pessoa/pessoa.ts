import { Cidade } from "../cidade/cidade";

export class Pessoa {
  id!: number;
  nome!: string;
  apelido!: string;
  timeCoracao!: string;
  cpf!: string;
  hobbie!: string;
  cidade!: Cidade;
}
