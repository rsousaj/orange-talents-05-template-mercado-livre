package br.com.zup.orangetalents.mercadolivre.produto.controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.orangetalents.mercadolivre.produto.dto.PerguntaRequest;
import br.com.zup.orangetalents.mercadolivre.produto.model.Produto;
import br.com.zup.orangetalents.mercadolivre.produto.service.EnviadorEmail;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;

@RestController
@RequestMapping("${mercadolivre.produto.uri}")
public class NovaPerguntaController {

	private EntityManager entityManager;
	private EnviadorEmail enviadorEmail;
	
	public NovaPerguntaController(EntityManager entityManager, EnviadorEmail enviadorEmail) {
		this.entityManager = entityManager;
		this.enviadorEmail = enviadorEmail;
	}

	@PostMapping("/{id}/perguntas")
	@Transactional
	public ResponseEntity<?> pergunta(@PathVariable Long id, 
			@RequestBody @Valid PerguntaRequest novaPergunta, 
			@AuthenticationPrincipal Usuario usuario) {
		
		Produto produto = entityManager.find(Produto.class, id);
		
		if (produto == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O produto informado não existe");
		}
		
		produto.adicionaPergunta(novaPergunta.toModel(usuario));
		entityManager.merge(produto);
		enviadorEmail.enviar(produto.getEmailDonoProduto(), "Você tem uma nova pergunta sobre o produto que está vendendo.");
		
		return ResponseEntity.ok().build();
	}
}
