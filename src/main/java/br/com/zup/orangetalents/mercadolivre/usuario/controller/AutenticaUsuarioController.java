package br.com.zup.orangetalents.mercadolivre.usuario.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orangetalents.mercadolivre.usuario.dto.TokenDTO;
import br.com.zup.orangetalents.mercadolivre.usuario.dto.UsuarioRequest;
import br.com.zup.orangetalents.mercadolivre.usuario.service.GerenciadorToken;

@RestController
@RequestMapping(value = "${mercadolivre.autenticacao.uri}")
public class AutenticaUsuarioController {

	private GerenciadorToken gerenciadorToken;
	private AuthenticationManager authManager;

	public AutenticaUsuarioController(GerenciadorToken gerenciadorToken, AuthenticationManager authManager) {
		this.gerenciadorToken = gerenciadorToken;
		this.authManager = authManager;
	}

	@PostMapping
	public ResponseEntity<TokenDTO> autentica(@RequestBody @Valid UsuarioRequest request) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha());
		
		try {
			Authentication authentication = authManager.authenticate(authenticationToken);
			String token = gerenciadorToken.gerar(authentication);
			TokenDTO tokenDTO = TokenDTO.build(token);
			
			return ResponseEntity.ok(tokenDTO);
		} catch (AuthenticationException ex) {
			return ResponseEntity.badRequest().build();
		}
	}
}
