package br.com.proarq;

import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.proarq.usuario.dtos.AutenticarRequestDto;
import br.com.proarq.usuario.dtos.CriarContaRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // critério de ordem de exceução dos testes(manual)
class UsuarioApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	private static String emailUsuario;
	private static String senhaUsuario;
	
	@Test
	@Order(1)
	public void criarContaTest() throws Exception {
		
		Faker faker = new Faker();
		
		CriarContaRequestDto dto = new CriarContaRequestDto();
		dto.setNome(faker.name().firstName());
		dto.setEmail(faker.internet().emailAddress());
		dto.setSobrenome(faker.name().lastName());
		dto.setSenha("@Teste1234");
		
		mockMvc.perform(post("/api/usuarios/criar-conta") //
				.contentType("application/json") // enviando dados formato json
				.content(objectMapper.writeValueAsString(dto)))// enviando os dados do DTO criado acima
				.andExpect(status().isCreated()); // resultado desejado status created 201
		
		emailUsuario = dto.getEmail();
		senhaUsuario = dto.getSenha();
	}
	
	@Test
	@Order(2)
	public void autenticarTest() throws Exception {
		
		AutenticarRequestDto dto = new AutenticarRequestDto();
		dto.setEmail(emailUsuario);
		dto.setSenha(senhaUsuario);
		
		mockMvc.perform(post("/api/usuarios/autenticar")
				.contentType("application/json") //enviando dados formato json
				.content(objectMapper.writeValueAsString(dto))) //enviando os dados do DTO criado acima
				.andExpect(status().isOk()); //resultado desejado status created 200		
		
	}
	
	@Test
	@Order(3)
	public void recuperarSenhaTest() {
		fail("Não implementado");
	}

}
