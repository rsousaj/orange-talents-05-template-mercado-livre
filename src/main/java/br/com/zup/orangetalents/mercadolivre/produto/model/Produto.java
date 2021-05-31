package br.com.zup.orangetalents.mercadolivre.produto.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import br.com.zup.orangetalents.mercadolivre.categoria.model.Categoria;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;
import io.jsonwebtoken.lang.Assert;

@Entity
@Table(name = "produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private @NotBlank String nome;
	private @NotNull @Positive BigDecimal valor;
	private @NotNull @PositiveOrZero Long quantidade;
	private @NotBlank @Size(max = 1000) String descricao;
	
	@ManyToOne
	@Valid
	private @NotNull Categoria categoria;
	
	@NotEmpty 
	@Size(min = 3)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "produto_id")
	private Set<Caracteristica> caracteristicas = new HashSet<Caracteristica>();
	
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	@ManyToOne
	private @NotNull Usuario donoProduto;

	@Deprecated
	public Produto() {}
	
	public Produto(@NotBlank String nome, @NotNull @Positive BigDecimal valor,
			@NotNull @PositiveOrZero Long quantidade, @NotBlank @Size(max = 1000) String descricao,
			@NotNull @Valid Categoria categoria, @NotEmpty @Size(min = 3) Set<Caracteristica> caracteristicas, @NotNull Usuario donoProduto) {
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.categoria = categoria;
		this.caracteristicas.addAll(caracteristicas);
		this.donoProduto = donoProduto;
		
		Assert.isTrue(this.caracteristicas.size() >= 3, "Não é permitido o cadastramento de produto com menos de 3 características.");
	}
	
	public Set<Caracteristica> getCaracteristicas() {
		return this.caracteristicas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
