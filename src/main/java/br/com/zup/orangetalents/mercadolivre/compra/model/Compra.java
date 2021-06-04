package br.com.zup.orangetalents.mercadolivre.compra.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.zup.orangetalents.mercadolivre.produto.model.Produto;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;
import io.jsonwebtoken.lang.Assert;

@Entity
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String gateway;
	
	@ManyToOne
	private Produto produto;
	
	@OneToOne
	private Usuario comprador;
	private BigDecimal valorProduto;
	private Long quantidade;
	
	@OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
	private Set<Transacao> transacoes = new HashSet<>();
	
	@Deprecated
	public Compra() { }

	public Compra(String gateway, Produto produto, Usuario comprador, Long quantidade) {
		super();
		this.gateway = gateway;
		this.produto = produto;
		this.comprador = comprador;
		this.valorProduto = produto.getValor();
		this.quantidade = quantidade;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getGateway() {
		return this.gateway;
	}
	
	public void adicionaTransacao(Transacao transacao) {
		Assert.state(!this.transacoes.contains(transacao), "Essa transação já foi incluída na compra.");
		
		this.transacoes.add(transacao);
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + ", gateway=" + gateway + ", produto=" + produto + ", comprador=" + comprador
				+ ", valorProduto=" + valorProduto + ", quantidade=" + quantidade + ", transacoes=" + transacoes + "]";
	}
}
