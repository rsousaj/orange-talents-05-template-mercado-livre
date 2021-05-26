package br.com.zup.orangetalents.mercadolivre.usuario.validacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;

import br.com.zup.orangetalents.mercadolivre.usuario.dto.UsuarioRequest;
import br.com.zup.orangetalents.mercadolivre.usuario.model.SenhaLimpa;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;

@DataJpaTest
public class UsuarioEmailUnicoValidatorTest {

	@PersistenceContext
	private EntityManager entityManager;
	private UsuarioEmailUnicoValidator validador;
	
	
	@BeforeEach
	public void inicializa() {
		validador = new UsuarioEmailUnicoValidator(entityManager);
	}
	
	@Test
	public void deveRejeitarARequisicaoSeJaExistirUsuarioComOMesmoEmail() {
		String mesmoEmail = "teste@teste.com";
		
		Usuario usuarioCadastrado = new Usuario(mesmoEmail, new SenhaLimpa("teste"));
		entityManager.persist(usuarioCadastrado);
		
		UsuarioRequest novoUsuario = new UsuarioRequest(mesmoEmail, "senha");
		BeanPropertyBindingResult erros = new BeanPropertyBindingResult(novoUsuario, "teste");
		
		validador.validate(novoUsuario, erros);
		
		assertEquals(1, erros.getFieldErrors().size());
		assertEquals(true, erros.hasFieldErrors("email"));
	}
	
	@Test
	public void deveValidarRegistroDeUsuarioSeOEmailNaoExistir() {
		UsuarioRequest novoUsuario = new UsuarioRequest("teste@teste.com", "teste");
		BeanPropertyBindingResult erros = new BeanPropertyBindingResult(novoUsuario, "teste");
		
		validador.validate(novoUsuario, erros);
		
		assertTrue(erros.getFieldErrors().isEmpty());
	}
}
