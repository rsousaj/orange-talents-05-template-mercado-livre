package br.com.zup.orangetalents.mercadolivre.produto.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;
import io.jsonwebtoken.lang.Assert;

@Entity
public class Pergunta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private @NotBlank String titulo;
	private @NotBlank String descricao;
	private @NotNull @OneToOne Usuario usuario;
	
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@Deprecated
	public Pergunta( ) { }
	
	public Pergunta(@NotBlank String titulo, @NotBlank String descricao, @NotNull Usuario usuario) {
		Assert.notNull(usuario, "É imprescindível o usuário que fez a pergunta.");
		
		this.titulo = titulo;
		this.descricao = descricao;
		this.usuario = usuario;
	}
	
}
