package br.com.proarq.usuario.dtos.copy;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class CriarContaRequestDto {
	
	@Size(min = 3, max = 20, message = "O nome deve ter de 3 a 20 caracteres.")
	@NotBlank(message = "Por favor, informe o nome.")
	private String nome;
	
	@Size(min = 3, max = 40, message = "O sobrenome deve ter de 3 a 40 caracteres.")
	@NotBlank(message = "Por favor, informe o sobrenome.")
	private String sobrenome;
	
	@Email(message = "Por favor, informe um endereço de email não cadastrado.")
	@NotBlank(message = "Por favor, informe um email.")
	private String email;
	
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
			message = "Informe uma senha (de no mínimo 8 dígitos) com pelo menos 1 letra maiúscula, 1 letra minúscula, 1 número e 1 caractere especial.")
	@NotBlank(message = "Por favor, informe uma senha.")
	private String senha;

}
