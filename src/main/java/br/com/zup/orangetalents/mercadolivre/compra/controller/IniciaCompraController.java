package br.com.zup.orangetalents.mercadolivre.compra.controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.orangetalents.mercadolivre.compra.dto.NovaCompraRequest;
import br.com.zup.orangetalents.mercadolivre.compra.model.Compra;
import br.com.zup.orangetalents.mercadolivre.compra.service.GatewayPagamento;
import br.com.zup.orangetalents.mercadolivre.compra.service.GatewayPagamentoSelector;
import br.com.zup.orangetalents.mercadolivre.produto.model.Produto;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;

@RestController
@RequestMapping("/api/compras")
public class IniciaCompraController {

	private EntityManager entityManager;
	private GatewayPagamentoSelector gatewayPagamentoSelector;
	
	public IniciaCompraController(EntityManager entityManager, GatewayPagamentoSelector gatewayPagamentoSelector) {
		this.entityManager = entityManager;
		this.gatewayPagamentoSelector = gatewayPagamentoSelector;
	}
	
	@PostMapping
	@Transactional
	public String compra(@RequestBody @Valid NovaCompraRequest compraRequest, @AuthenticationPrincipal Usuario comprador) {
		Produto produto = entityManager.find(Produto.class, compraRequest.getIdProduto());
		Compra compra = compraRequest.toModel(produto, comprador);
		GatewayPagamento gateway = gatewayPagamentoSelector.find(compra.getGateway());
		
		if (produto == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O produto informado não existe");
		}
		
		produto.baixaEstoque(compraRequest.getQuantidade());
		
		entityManager.persist(compra);
		entityManager.merge(produto);
		
		//envia email
		
		String urlRedirect = gateway.geraUrlPagamento(compra);
		
		return urlRedirect;
	}
}
