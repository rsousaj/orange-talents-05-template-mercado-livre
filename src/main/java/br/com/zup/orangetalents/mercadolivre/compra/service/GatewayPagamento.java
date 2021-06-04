package br.com.zup.orangetalents.mercadolivre.compra.service;

import br.com.zup.orangetalents.mercadolivre.compra.model.Compra;
import br.com.zup.orangetalents.mercadolivre.compra.model.Transacao;

public interface GatewayPagamento {

	String geraUrlPagamento(Compra compra);
	boolean processavel(String nomeGateway);
	Transacao criaTransacao(String idTransacao, String status, Compra compra);
}
