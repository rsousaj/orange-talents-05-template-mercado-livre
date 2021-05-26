package br.com.zup.orangetalents.mercadolivre.usuario.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import br.com.zup.orangetalents.mercadolivre.usuario.validacao.UsuarioEmailUnicoValidator;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	@PersistenceContext
	private EntityManager entityManager;
	private UsuarioEmailUnicoValidator usuarioEmailUnicoValidator;
	
	public UsuarioController(UsuarioEmailUnicoValidator usuarioEmailUnicoValidator) {
		this.usuarioEmailUnicoValidator = usuarioEmailUnicoValidator;
	}

	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(usuarioEmailUnicoValidator);
	}
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> cadastra(@RequestBody @Valid UsuarioRequest request) {
		entityManager.persist(request.toModel());
		return ResponseEntity.ok().build();
	}
}
