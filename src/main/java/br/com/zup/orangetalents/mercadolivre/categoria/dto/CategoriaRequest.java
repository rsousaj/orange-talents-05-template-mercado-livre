package br.com.zup.orangetalents.mercadolivre.categoria.dto;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.zup.orangetalents.mercadolivre.categoria.model.Categoria;
import br.com.zup.orangetalents.mercadolivre.categoria.repository.CategoriaRepository;
import br.com.zup.orangetalents.mercadolivre.comum.validation.ExistsEntity;
import br.com.zup.orangetalents.mercadolivre.comum.validation.UniqueValue;

public class CategoriaRequest {

	@NotBlank
	@UniqueValue(domainClass = Categoria.class, fieldName = "nome")
	public String nome;
	
	@JsonAlias(value = "categoria_mae")
	@ExistsEntity(referenceEntity = Categoria.class, fieldName = "id")
	public Long idCategoriaMae;
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setIdCategoriaMae(Long idCategoriaMae) {
		this.idCategoriaMae = idCategoriaMae;
	}
	
	public Categoria toModel(CategoriaRepository repository) {
		Categoria novaCategoria = new Categoria(nome);
		
		if (idCategoriaMae != null) {
			Optional<Categoria> categoriaMae = repository.findById(idCategoriaMae);
			Assert.isTrue(categoriaMae.isPresent(), "Não foi possível localizar a categoria");
					
			novaCategoria.setCategoriaMae(categoriaMae.get());
		}
		
		return novaCategoria;
	}
}
