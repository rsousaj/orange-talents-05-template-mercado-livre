package br.com.zup.orangetalents.mercadolivre.produto.controller;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import br.com.zup.orangetalents.mercadolivre.categoria.model.Categoria;
import br.com.zup.orangetalents.mercadolivre.produto.dto.OpiniaoRequest;
import br.com.zup.orangetalents.mercadolivre.produto.dto.ProdutoRequest;
import br.com.zup.orangetalents.mercadolivre.produto.model.Produto;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;

@RestController
@RequestMapping("${mercadolivre.produto.uri}")
public class ProdutoController {

	@PersistenceContext
	private EntityManager entityManager;
	
	public ProdutoController(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastra(@RequestBody @Valid ProdutoRequest request, @AuthenticationPrincipal Usuario usuario) {	
		Produto novoProduto = request.toModel((idCategoria) -> entityManager.find(Categoria.class, idCategoria), usuario);
		entityManager.persist(novoProduto);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/{id}/opinioes")
	@Transactional
	public ResponseEntity<?> adicionaOpiniao(@PathVariable Long id, @RequestBody @Valid OpiniaoRequest request, @AuthenticationPrincipal Usuario usuario) {
		Produto produto = entityManager.find(Produto.class, id);
		
		if (produto == null) {
//			return ResponseEntity.badRequest().body("O produto informado não existe");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O produto informado não existe");
		}
				
		produto.adicionaOpiniao(request.toModel(usuario));
		entityManager.merge(produto);
		
		return ResponseEntity.ok().build();
	}
}
