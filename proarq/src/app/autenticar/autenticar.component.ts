import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-autenticar',
  templateUrl: './autenticar.component.html',
  styleUrls: ['./autenticar.component.css']
})
export class AutenticarComponent {

  mensagem_sucesso: string =''; // para retorno
  mensagem_erro: string = ''; // para retorno

  //inicializando http client
  constructor(
    private httpClient: HttpClient
  ) { }


  //criando objeto para capturar os dados 
  formAutenticar = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    senha: new FormControl('', [Validators.required,
    Validators.pattern(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\-=_+[\]{}|\\,.<>/?])[a-zA-Z0-9!@#$%^&*()\-=_+[\]{}|\\,.<>/?]{8,}$/)])
  });

  //Acessando os campos na página

  get form(): any {
    return this.formAutenticar.controls;
  }

  onSubmit(): void {

    this.mensagem_sucesso = '';
    this.mensagem_erro = '';

    //fazendo requisição post para - api/usuarios/autenticar
    this.httpClient.post('http://localhost:8080/api/usuarios/autenticar', this.formAutenticar.value)
      .subscribe({
        //capturando a resposta
        next: (data: any) => { // resposta de sucesso

          //retornando dto.usuario.mensagem
          this.mensagem_sucesso = data.mensagem;

          //gravando dados do usuário no storage
          localStorage.setItem('auth_usuario', JSON.stringify(data));
        },
        error: (e) => { // resposta de erro
          this.mensagem_erro = e.error.mensagem;
        }
      })
  }

}
