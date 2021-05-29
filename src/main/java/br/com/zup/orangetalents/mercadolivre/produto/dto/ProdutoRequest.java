package br.com.zup.orangetalents.mercadolivre.produto.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.util.Assert;

import br.com.zup.orangetalents.mercadolivre.categoria.model.Categoria;
import br.com.zup.orangetalents.mercadolivre.comum.validation.ExistsEntity;
import br.com.zup.orangetalents.mercadolivre.produto.model.Caracteristica;
import br.com.zup.orangetalents.mercadolivre.produto.model.Produto;

public class ProdutoRequest {

	private @NotBlank String nome;
	private @NotNull @Positive BigDecimal valor;
	private @NotNull @PositiveOrZero Long quantidade;
	private @NotBlank @Size(max = 1000) String descricao;
	
	@ExistsEntity(referenceEntity = Categoria.class, fieldName = "id")
	private @NotNull Long idCategoria;
	
	@NotEmpty @Size(min = 3)
	private Set<CaracteristicaRequest> caracteristicasRequest = new HashSet<CaracteristicaRequest>();

	public ProdutoRequest(@NotBlank String nome, @NotNull @Positive BigDecimal valor,
			@NotNull @PositiveOrZero Long quantidade, @NotBlank @Size(max = 1000) String descricao,
			@NotNull Long idCategoria, @NotEmpty @Size(min = 3) Set<CaracteristicaRequest> caracteristicas) {
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.idCategoria = idCategoria;
		this.caracteristicasRequest = caracteristicas;
	}

	public Produto toModel(Function<Long, Categoria> buscarCategoria) {
		Categoria categoria = buscarCategoria.apply(idCategoria);
		Set<Caracteristica> caracteristicas = caracteristicasRequest.stream()
					.map(CaracteristicaRequest::toModel)
					.collect(Collectors.toSet());
		
		return new Produto(this.nome, this.valor, this.quantidade, this.descricao, categoria, caracteristicas);
	}
}
