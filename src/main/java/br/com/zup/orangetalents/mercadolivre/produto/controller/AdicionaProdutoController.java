package br.com.zup.orangetalents.mercadolivre.produto.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orangetalents.mercadolivre.categoria.model.Categoria;
import br.com.zup.orangetalents.mercadolivre.produto.dto.ProdutoRequest;
import br.com.zup.orangetalents.mercadolivre.produto.model.Produto;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;

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
	public ResponseEntity<?> cadastra(@RequestBody @Valid ProdutoRequest request, @AuthenticationPrincipal Usuario usuario) {	
		Produto novoProduto = request.toModel((idCategoria) -> entityManager.find(Categoria.class, idCategoria), usuario);
		
		
		
		entityManager.persist(novoProduto);
		
		novoProduto.getCaracteristicas().stream().forEach(System.out::println);
		
		return ResponseEntity.ok().build();
	}
}
