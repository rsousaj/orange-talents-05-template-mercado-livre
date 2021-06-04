package br.com.zup.orangetalents.mercadolivre.compra.dto;

import br.com.zup.orangetalents.mercadolivre.compra.model.Compra;
import br.com.zup.orangetalents.mercadolivre.produto.model.Produto;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;

public class NovaCompraRequest {

	private Long idProduto;
	private Long quantidade;
	private String gatewayPagamento;
	
	public NovaCompraRequest(Long idProduto, Long quantidade, String gatewayPagamento) {
		super();
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.gatewayPagamento = gatewayPagamento;
	}
	
	public Long getIdProduto() {
		return this.idProduto;
	}
	
	public Long getQuantidade() {
		return this.quantidade;
	}
	
	public Compra toModel(Produto produto, Usuario comprador) {
		return new Compra(gatewayPagamento, produto, comprador, quantidade);
	}
}
