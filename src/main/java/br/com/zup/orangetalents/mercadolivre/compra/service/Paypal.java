package br.com.zup.orangetalents.mercadolivre.compra.service;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.zup.orangetalents.mercadolivre.compra.model.Compra;
import br.com.zup.orangetalents.mercadolivre.compra.model.StatusTransacao;
import br.com.zup.orangetalents.mercadolivre.compra.model.Transacao;

@Component
public class Paypal implements GatewayPagamento {

	@Override
	public String geraUrlPagamento(Compra compra) {
		return "paypal.com/buyerId=" + compra.getId() + "&redirectUrl=/api/compras/paypal/" + compra.getId();
	}
	
	@Override
	public boolean processavel(String nomeGateway) {
		if (nomeGateway.equals("paypal")) {
			return true;
		}
		
		return false;
	}

	@Override
	public Transacao criaTransacao(String idTransacao, String status, Compra compra) {
		Assert.isTrue(status.equals("1") || status.equals("0"), "O status informado é inválido.");
		Assert.isTrue(!idTransacao.isBlank(), "É necessário informar um ID de Transação");
		
		StatusTransacao statusTransacao = status.equals("1") ? StatusTransacao.SUCESSO : StatusTransacao.ERRO;
		
		return new Transacao(idTransacao, statusTransacao, compra);
	}
}
