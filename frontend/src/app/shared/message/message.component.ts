import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-message',
  template: `
    <div class="mensagem-erro" *ngIf="temErro()">
          {{ text }} </div>
  `,
  styles: [
  ]
})
export class MessageComponent {

  @Input() error!: string;
  @Input() control?: FormControl;
  @Input() text!: string;

  temErro():boolean{
    return this.control? this.control.hasError(this.error) && this.control.dirty: true;
  }

}
