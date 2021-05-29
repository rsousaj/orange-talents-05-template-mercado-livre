package br.com.zup.orangetalents.mercadolivre.produto.dto;

import javax.validation.constraints.NotBlank;

import br.com.zup.orangetalents.mercadolivre.categoria.model.Categoria;
import br.com.zup.orangetalents.mercadolivre.produto.model.Caracteristica;

public class CaracteristicaRequest {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String valor;

	public CaracteristicaRequest(@NotBlank String nome, @NotBlank String valor) {
		super();
		this.nome = nome;
		this.valor = valor;
	}

	public Caracteristica toModel() {
		return new Caracteristica(this.nome, this.valor);
	}
	
	@Override
	public String toString() {
		return "CaracteristicaRequest [nome=" + nome + ", valor=" + valor + "]";
	}
}
