package br.com.proarq.usuario.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class RecuperarSenhaRequestDto {
	
	@Email(message = "Por favor, informe um endereço de email cadastrado.")
	@NotBlank(message = "Por favor, informe o email para recuperação da senha.")
	private String email;
}
