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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CaracteristicaRequest))
			return false;
		CaracteristicaRequest other = (CaracteristicaRequest) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
}
