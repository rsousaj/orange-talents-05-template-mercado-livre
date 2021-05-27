package br.com.zup.orangetalents.mercadolivre.produto.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.orangetalents.mercadolivre.produto.dto.ImagensRequest;
import br.com.zup.orangetalents.mercadolivre.produto.model.Imagem;
import br.com.zup.orangetalents.mercadolivre.produto.model.Produto;
import br.com.zup.orangetalents.mercadolivre.produto.service.Storage;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;

@RestController
@RequestMapping("${mercadolivre.produto.uri}")
public class AdicionaImagemController {

	@PersistenceContext
	private EntityManager entityManager;
	
	private Storage storage;
	
	public AdicionaImagemController(EntityManager entityManager, Storage storage) {
		this.entityManager = entityManager;
		this.storage = storage;
	}
	
	@PostMapping("/{id}/imagens")
	@Transactional
	public ResponseEntity<?> adicionaImagens(@PathVariable Long id, @Valid ImagensRequest request, @AuthenticationPrincipal Usuario usuario) {
		Produto produto = entityManager.find(Produto.class, id);
		
		if (produto == null) {
			return ResponseEntity.badRequest().body("O produto informado não existe");
		}
		
		if (!produto.validaDono(usuario)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Operação não permitida.");
		}
		
		List<Imagem> imagens = storage.enviarImagens(request.getImagens())
				.stream()
				.map(Imagem::new)
				.collect(Collectors.toList());
			
		produto.setImagens(imagens);
		entityManager.merge(produto);
		
		return ResponseEntity.ok().build();
	}
}
