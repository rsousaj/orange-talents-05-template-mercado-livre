package br.com.zup.orangetalents.mercadolivre.usuario.controller;

import static org.hamcrest.core.IsIterableContaining.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.orangetalents.mercadolivre.usuario.dto.UsuarioRequest;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;
import br.com.zup.orangetalents.mercadolivre.usuario.repository.UsuarioRepository;

// Nao consegui fazer funcionar só com o WebMvcTest pq só coloca no contexto as classes relacionadas ao MVC e, com isso, nao instancia o Validator.
// Se @MockBean no Validator e usar o doCallRealMethod() dá erro pq não tem o UsuarioRepository
//@WebMvcTest(UsuarioController.class)
@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class UsuarioControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private UsuarioRepository usuarioRepository;

	@Value("${mercadolivre.usuario.uri}")
	private String URL;

	@ParameterizedTest
	@MethodSource("proveDadosInvalidos")
	public void deveriaRetornarBadRequestSeEmailInvalido(UsuarioRequest usuarioTest, String campoInconsistente)
			throws Exception {
		URI uri = new URI(URL);
		String jsonRequisicao = mapper.writeValueAsString(usuarioTest);

		mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonRequisicao))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("*.campo", hasItem(campoInconsistente)));
	}

	public static Stream<Arguments> proveDadosInvalidos() {
		return Stream.of(Arguments.of(new UsuarioRequest("emailinvalido", "123456"), "email"),
				Arguments.of(new UsuarioRequest("", "123456"), "email"),
				Arguments.of(new UsuarioRequest("teste@teste.com", ""), "senha"),
				Arguments.of(new UsuarioRequest("teste@teste.com", "12345"), "senha"));
	}

	@Test
	public void deveriaRetornarBadRequestSeEmailJaCadastrado() throws Exception {
		URI uri = new URI(URL);
		String emailExistente = "teste@teste.com";
		UsuarioRequest usuarioEmailExistente = new UsuarioRequest(emailExistente, "senha123");
		String jsonRequisicao = mapper.writeValueAsString(usuarioEmailExistente);

		when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(new Usuario()));

		mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonRequisicao))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("*.campo", hasItem("email")));
	}
}
