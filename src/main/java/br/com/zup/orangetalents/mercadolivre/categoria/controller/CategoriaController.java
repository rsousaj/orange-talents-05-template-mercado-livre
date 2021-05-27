package br.com.zup.orangetalents.mercadolivre.categoria.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orangetalents.mercadolivre.categoria.dto.CategoriaRequest;
import br.com.zup.orangetalents.mercadolivre.categoria.repository.CategoriaRepository;

@RestController
@RequestMapping(value = "${mercadolivre.categoria.uri}")
public class CategoriaController {

	private CategoriaRepository categoriaRepository;

	public CategoriaController(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}
	
	@PostMapping
	public ResponseEntity<?> cadastra(@RequestBody @Valid CategoriaRequest request) {
		categoriaRepository.save(request.toModel(categoriaRepository));
		return ResponseEntity.ok().build();
	}
}
