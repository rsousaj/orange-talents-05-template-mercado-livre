package br.com.zup.orangetalents.mercadolivre.compra.service;

import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class GatewayPagamentoSelector {
	
	private final Set<GatewayPagamento> gateways;
	
	public GatewayPagamentoSelector(Set<GatewayPagamento> gateways) {
		this.gateways = gateways;
	}
	
	public GatewayPagamento find(String nomeGateway) {
		return gateways
			.stream()
			.filter((gateway) -> gateway.processavel(nomeGateway))
			.findAny()
			.orElseThrow(() -> new IllegalStateException("Não foi possível localizar Processador para o Gateway de pagamento informado."));
	}
}
