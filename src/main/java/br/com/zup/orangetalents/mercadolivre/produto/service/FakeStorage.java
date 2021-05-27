package br.com.zup.orangetalents.mercadolivre.produto.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FakeStorage implements Storage {

	private final String STORAGE_URL = "http://site.com/imagens/";
	
	@Override
	public List<String> enviarImagens(List<MultipartFile> imagens) {
		return imagens.stream()
				.map(imagem -> STORAGE_URL + UUID.randomUUID().toString())
				.collect(Collectors.toList());
	}
}
