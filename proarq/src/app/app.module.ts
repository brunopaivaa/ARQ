import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AutenticarComponent } from './autenticar/autenticar.component';
import { CriarcontaComponent } from './criarconta/criarconta.component';
import { RecuperarsenhaComponent } from './recuperarsenha/recuperarsenha.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'

@NgModule({
  declarations: [
    AppComponent,
    AutenticarComponent,
    CriarcontaComponent,
    RecuperarsenhaComponent
  ],
  imports: [
    BrowserModule,
    FormsModule, 
    ReactiveFormsModule, //formulátio reativos
    HttpClientModule // biblioteca para requisições http
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
