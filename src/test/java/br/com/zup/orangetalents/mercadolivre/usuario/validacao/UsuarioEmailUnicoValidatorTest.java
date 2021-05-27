package br.com.zup.orangetalents.mercadolivre.usuario.validacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BeanPropertyBindingResult;

import br.com.zup.orangetalents.mercadolivre.usuario.dto.UsuarioRequest;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;
import br.com.zup.orangetalents.mercadolivre.usuario.repository.UsuarioRepository;

@DataJpaTest
public class UsuarioEmailUnicoValidatorTest {

	@MockBean
	private UsuarioRepository usuarioRepository;
	private UsuarioEmailUnicoValidator validador;
	
	
	@BeforeEach
	public void inicializa() {
		validador = new UsuarioEmailUnicoValidator(usuarioRepository);
	}
	
	@Test
	public void deveriaRejeitarARequisicaoSeJaExistirUsuarioComOMesmoEmail() {
		String emailJaCadastrado = "teste@teste.com";

		when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(new Usuario()));
		
		UsuarioRequest novoUsuario = new UsuarioRequest(emailJaCadastrado, "senha");
		BeanPropertyBindingResult erros = new BeanPropertyBindingResult(novoUsuario, "teste");
		
		validador.validate(novoUsuario, erros);
		
		assertEquals(1, erros.getFieldErrors().size());
		assertEquals(true, erros.hasFieldErrors("email"));
	}
	
	@Test
	public void deveriaValidarRegistroDeUsuarioSeOEmailNaoExistir() {
		when(usuarioRepository.findByEmail(any())).thenReturn(Optional.empty());
		
		UsuarioRequest novoUsuario = new UsuarioRequest("teste@teste.com", "teste");
		BeanPropertyBindingResult erros = new BeanPropertyBindingResult(novoUsuario, "teste");
		
		validador.validate(novoUsuario, erros);
		
		assertTrue(erros.getFieldErrors().isEmpty());
	}
}
