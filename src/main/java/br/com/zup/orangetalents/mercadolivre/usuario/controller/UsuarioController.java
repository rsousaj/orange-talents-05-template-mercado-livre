package br.com.zup.orangetalents.mercadolivre.usuario.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orangetalents.mercadolivre.usuario.dto.UsuarioRequest;
import br.com.zup.orangetalents.mercadolivre.usuario.repository.UsuarioRepository;
import br.com.zup.orangetalents.mercadolivre.usuario.validacao.UsuarioEmailUnicoValidator;

@RestController
@RequestMapping(value = "${mercadolivre.usuario.uri}")
public class UsuarioController {
	
	private UsuarioRepository usuarioRepository;
	private UsuarioEmailUnicoValidator usuarioEmailUnicoValidator;

	public UsuarioController(UsuarioRepository usuarioRepository,
			UsuarioEmailUnicoValidator usuarioEmailUnicoValidator) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.usuarioEmailUnicoValidator = usuarioEmailUnicoValidator;
	}

	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(usuarioEmailUnicoValidator);
	}
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> cadastra(@RequestBody @Valid UsuarioRequest request) {
		usuarioRepository.save(request.toModel());
		return ResponseEntity.ok().build();
	}
}
