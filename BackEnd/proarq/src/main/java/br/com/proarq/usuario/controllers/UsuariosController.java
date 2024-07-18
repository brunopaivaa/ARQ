package br.com.proarq.usuario.controllers;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import br.com.proarq.helpers.MD5Helper;
import br.com.proarq.services.EmailService;
import br.com.proarq.services.TokenService;
import br.com.proarq.usuario.dtos.AutenticarRequestDto;
import br.com.proarq.usuario.dtos.AutenticarResponseDto;
import br.com.proarq.usuario.dtos.CriarContaRequestDto;
import br.com.proarq.usuario.dtos.CriarContaResponseDto;
import br.com.proarq.usuario.dtos.RecuperarSenhaRequestDto;
import br.com.proarq.usuario.dtos.RecuperarSenhaResponseDto;
import br.com.proarq.usuario.entities.Usuario;
import br.com.proarq.usuario.repositories.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/usuarios")
public class UsuariosController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping("autenticar")
	public ResponseEntity<AutenticarResponseDto> autenticar(@RequestBody @Valid AutenticarRequestDto dto) {
		try {
			AutenticarResponseDto response = new AutenticarResponseDto();

			Usuario usuario = usuarioRepository.findByEmailAndSenha(dto.getEmail(),MD5Helper.encryptToMD5(dto.getSenha()));

			if (usuario != null) {
				response.setMensagem("Usuário autenticado com sucesso.");
				response.setIdUsuario(usuario.getIdUsuario());
				response.setNome(usuario.getNome());
				response.setSobrenome(usuario.getSobrenome());
				response.setEmail(usuario.getEmail());
				response.setAccessToken(tokenService.generateToken(usuario.getEmail()));
				// HTTP 200 (OK)
				return ResponseEntity.ok(response);
			} else {
				response.setMensagem("Acesso negado. Usuário não encontrado.");
				// HTTP 401 (Unauthorized)
				return ResponseEntity.status(401).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	@PostMapping("criar-conta")
	public ResponseEntity<CriarContaResponseDto> criarConta(@RequestBody @Valid CriarContaRequestDto dto) {
		try {
			CriarContaResponseDto response = new CriarContaResponseDto();

			if (usuarioRepository.findByEmail(dto.getEmail()) != null) {
				response.setMensagem("O email informado já está cadastro no sistema, tente outro.");
				// HTTP 400 - CREATED
				return ResponseEntity.status(400).body(response);
			} else {
				Usuario usuario = new Usuario();

				usuario.setNome(dto.getNome());
				usuario.setSobrenome(dto.getSobrenome());
				usuario.setEmail(dto.getEmail());
				usuario.setSenha(MD5Helper.encryptToMD5(dto.getSenha()));

				// salvando usuario
				usuarioRepository.save(usuario);
				
				response.setMensagem("Usuário Cadastrado com sucesso.");
				response.setIdUsuario(usuario.getIdUsuario());
				response.setNome(usuario.getNome());
				response.setSobrenome(usuario.getSobrenome());
				response.setEmail(usuario.getEmail());
				response.setDataHoraCriacao(Instant.now());

				// HTTP 201 - CREATED
				return ResponseEntity.status(201).body(response);
			}

		} catch (Exception e) {
			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(null);
		}
	}

	@PostMapping("recuperar-senha")
	public ResponseEntity<RecuperarSenhaResponseDto> recuperarSenha(@RequestBody @Valid RecuperarSenhaRequestDto dto) {
		
	
		try {
			
			RecuperarSenhaResponseDto response = new RecuperarSenhaResponseDto();
			
			Usuario usuario = usuarioRepository.findByEmail(dto.getEmail());
			
			if (usuario != null) {
				
				Faker faker = new Faker();
				String novaSenha = faker.internet().password(8, 12, true, true, true);
				
				String para = usuario.getEmail();
				String assunto = "PROARQ - Recuperação de senha";
				String corpoEmail = "Olá " + usuario.getNome() + " " + usuario.getSobrenome() +
									"\n\n Uma nova senha foi gerada para acessar nossa plataforma." + 
									 "\n\n Utilize sua nova senha: " + novaSenha + "." +
									"\n\n Equipe PROARQ.";
				
				emailService.send(para, assunto, corpoEmail);
				
				usuario.setSenha(MD5Helper.encryptToMD5(novaSenha));
				usuarioRepository.save(usuario);
				
				response.setMensagem("Recuperação de senha realizada com sucesso.");
				response.setIdUsuario(usuario.getIdUsuario());
				response.setNome(usuario.getNome());
				response.setSobrenome(usuario.getSobrenome());
				response.setEmail(usuario.getEmail());
				response.setDataHotaRecuperacao(Instant.now());
				
				return ResponseEntity.status(200).body(response);
				
			}else {
				response.setMensagem("Email não encontrado. Por favor, verifique o email informado.");
				return ResponseEntity.status(404).body(response);
			}
					
		} catch (Exception e) {
			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(null);
		}
	}
	
}
	
	

