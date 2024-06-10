package br.com.proarq.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AutenticarResponseDto {
	
	private Integer idUsuario;
	private String nome;
	private String sobrenome;
	private String email;
	private String acessToken;

}
