package br.com.proarq.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.proarq.dtos.AutenticarRequestDto;
import br.com.proarq.dtos.AutenticarResponseDto;
import br.com.proarq.dtos.CriarContaRequestDto;
import br.com.proarq.dtos.CriarContaResponseDto;
import br.com.proarq.dtos.RecuperarSenhaRequestDto;
import br.com.proarq.dtos.RecuperarSenhaResponseDto;
import br.com.proarq.entities.Usuario;
import br.com.proarq.helpers.MD5Helper;
import br.com.proarq.repositories.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/usuarios")
public class UsuariosController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping("autenticar")
	public ResponseEntity<AutenticarResponseDto> autenticar(@RequestBody @Valid AutenticarRequestDto dto) {
		return null;
	}

	@PostMapping("criar-conta")
	public ResponseEntity<CriarContaResponseDto> criarConta(@RequestBody @Valid CriarContaRequestDto dto) {
		try {
			CriarContaResponseDto response = new CriarContaResponseDto();
			
			if (usuarioRepository.findByEmail(dto.getEmail()) != null) {
				response.setMensagem("O email informado já está cadastro no sistema, tente outro.");
				// HTTP 400 - CREATED
				return ResponseEntity.status(400).body(response);
			} 
			else {
				Usuario usuario = new Usuario();

				usuario.setNome(dto.getNome());
				usuario.setSobrenome(dto.getSobrenome());
				usuario.setEmail(dto.getEmail());
				usuario.setSenha(MD5Helper.encryptToMD5(dto.getSenha()));

				// salvando usuario
				usuarioRepository.save(usuario);

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
		return null;
	}

}
