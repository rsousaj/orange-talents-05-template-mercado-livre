package br.com.zup.orangetalents.mercadolivre.produto.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.zup.orangetalents.mercadolivre.produto.model.Opiniao;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;

public class OpiniaoRequest {

	@NotNull
	@Min(value = 1) @Max(value = 5)
	private int nota;
	
	@NotBlank
	private String titulo;
	
	@NotBlank
	@Size(max = 500)
	private String descricao;

	public OpiniaoRequest(@NotNull @Size(min = 1, max = 5) int nota, @NotBlank String titulo,
			@NotBlank @Size(max = 500) String descricao) {
		this.nota = nota;
		this.titulo = titulo;
		this.descricao = descricao;
	}
	
	public Opiniao toModel(Usuario usuario) {
		return new Opiniao(nota, titulo, descricao, usuario);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
