package br.com.zup.orangetalents.mercadolivre.categoria.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "categoria_mae_id", referencedColumnName = "id")
	private Categoria categoriaMae;
	
	@Deprecated
	public Categoria() {	
	}

	public Categoria(@NotBlank String nome) {
		super();
		this.nome = nome;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setCategoriaMae(Categoria categoriaMae) {
		this.categoriaMae = categoriaMae;
	}
}
