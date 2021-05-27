package br.com.zup.orangetalents.mercadolivre.produto.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.lang.Assert;

public class ImagensRequest {

	@NotNull
	@Size(min = 1)
	private List<MultipartFile> imagens = new ArrayList<>();

	public List<MultipartFile> getImagens() {
		return Collections.unmodifiableList(this.imagens);
	}
	
	public void setImagens(List<MultipartFile> imagens) {
		Assert.notNull(imagens, "É necessário ao menos uma imagem.");
		
		this.imagens.addAll(imagens);
	}
}
