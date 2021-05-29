package br.com.zup.orangetalents.mercadolivre.produto.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orangetalents.mercadolivre.categoria.model.Categoria;
import br.com.zup.orangetalents.mercadolivre.produto.dto.ProdutoRequest;
import br.com.zup.orangetalents.mercadolivre.produto.model.Produto;

@RestController
@RequestMapping("${mercadolivre.produto.uri}")
public class AdicionaProdutoController {

	@PersistenceContext
	private EntityManager entityManager;
	
	public AdicionaProdutoController(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@PostMapping
	public ResponseEntity<?> cadastra(@RequestBody @Valid ProdutoRequest request) {
		Produto novoProduto = request.toModel(
				(idCategoria) -> entityManager.find(Categoria.class, idCategoria));
		entityManager.persist(novoProduto);
		return ResponseEntity.ok().build();
	}
}
