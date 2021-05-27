package br.com.zup.orangetalents.mercadolivre.produto.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Imagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String link;
	
	@Deprecated
	public Imagem() {}
	
	public Imagem(String link) {
		this.link = link;
	}
	
	public String getLink() {
		return this.link;
	}
}
