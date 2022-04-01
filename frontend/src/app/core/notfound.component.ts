import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-notfound',
  template: `
  <div class="mainbox">
    <div class="err">4</div>
    <i class="far fa-question-circle fa-spin"></i>
    <div class="err2">4</div>
    <div class="msg"><p>Voltar para <a routerLink="/cidades">Cidades</a>.</p></div>
  </div>
  `,
  styles: [`

    .mainbox {
      margin: auto;
      height: 600px;
      width: 600px;
      position: relative;
    }

      .err {
        color: #2196F3;
        font-size: 11rem;
        position:absolute;
        left: 20%;
        top: 8%;
      }

    .far {
      position: absolute;
      font-size: 8.5rem;
      left: 42%;
      top: 15%;
      color: #2196F3;
    }

    .err2 {
        color: #2196F3;
        font-size: 11rem;
        position:absolute;
        left: 68%;
        top: 8%;
      }

    .msg {
        text-align: center;
        font-size: 1.6rem;
        position:absolute;
        left: 16%;
        top: 45%;
        width: 75%;
      }

    a {
      text-decoration: none;
      color: #2196F3;
    }

    a:hover {
      text-decoration: underline;
    }
  `]
})
export class NotfoundComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
