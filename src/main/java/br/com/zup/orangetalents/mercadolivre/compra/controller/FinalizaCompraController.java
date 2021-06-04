package br.com.zup.orangetalents.mercadolivre.compra.controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orangetalents.mercadolivre.compra.model.Compra;
import br.com.zup.orangetalents.mercadolivre.compra.model.Transacao;
import br.com.zup.orangetalents.mercadolivre.compra.service.GatewayPagamento;
import br.com.zup.orangetalents.mercadolivre.compra.service.GatewayPagamentoSelector;

@RestController
@RequestMapping("/api/compras")
public class FinalizaCompraController {

	private GatewayPagamentoSelector gatewayPagamentoSelector;
	private EntityManager entityManager;
	
	public FinalizaCompraController(GatewayPagamentoSelector gatewayPagamentoSelector, EntityManager entityManager) {
		super();
		this.gatewayPagamentoSelector = gatewayPagamentoSelector;
		this.entityManager = entityManager;
	}
	
	@PostMapping("/retorno-{gateway}/{idCompra}")
	@Transactional
	public String processa(@PathVariable String gateway, 
			@PathVariable Long idCompra, 
			@RequestParam String idTransacao, 
			@RequestParam String status) {
				
		GatewayPagamento gatewayPagamento = gatewayPagamentoSelector.find(gateway);
		Compra compra = entityManager.find(Compra.class, idCompra);
		Transacao transacao = gatewayPagamento.criaTransacao(idTransacao, status, compra);
		
		compra.adicionaTransacao(transacao);
		entityManager.merge(compra);
		
		return compra.toString();
	}
}
