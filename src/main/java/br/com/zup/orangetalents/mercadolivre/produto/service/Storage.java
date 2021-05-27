package br.com.zup.orangetalents.mercadolivre.produto.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface Storage {

	public List<String> enviarImagens(List<MultipartFile> imagens);
}
