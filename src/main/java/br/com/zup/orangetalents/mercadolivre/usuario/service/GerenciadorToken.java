package br.com.zup.orangetalents.mercadolivre.usuario.service;

import org.springframework.security.core.Authentication;

public interface GerenciadorToken {
	
	String gerar(Authentication auth);
	String getIdentificadorUsuario(String token);
	boolean isValid(String token);
	
}
