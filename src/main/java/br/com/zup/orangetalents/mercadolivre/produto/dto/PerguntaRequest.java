package br.com.zup.orangetalents.mercadolivre.produto.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.orangetalents.mercadolivre.produto.model.Pergunta;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;

public class PerguntaRequest {

	@NotBlank
	private String titulo;
	
	@NotBlank
	private String descricao;

	public PerguntaRequest(@NotBlank String titulo, @NotBlank String descricao) {
		this.titulo = titulo;
		this.descricao = descricao;
	}
	
	public Pergunta toModel(@Valid @NotNull Usuario usuarioPerguntador) {
		return new Pergunta(titulo, descricao, usuarioPerguntador);
	}
}
