package br.com.zup.orangetalents.mercadolivre.compra.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Transacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String idTransacaoGateway; 
	private StatusTransacao status;
	
	@ManyToOne
	private @NotNull @Valid Compra compra;
	
	@Deprecated
	public Transacao() {
		
	}
	
	public Transacao(String idTransacaoGateway, StatusTransacao status, @NotNull @Valid Compra compra) {
		super();
		this.idTransacaoGateway = idTransacaoGateway;
		this.status = status;
		this.compra = compra;
	}

	@Override
	public String toString() {
		return "Transacao [id=" + id + ", idTransacaoGateway=" + idTransacaoGateway + ", status=" + status + "]";
	}
}
