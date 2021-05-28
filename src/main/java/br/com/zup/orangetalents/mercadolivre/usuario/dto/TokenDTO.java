package br.com.zup.orangetalents.mercadolivre.usuario.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TokenDTO {

	private String token;
	
	private TokenDTO(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public static TokenDTO build(String token) {
		return new TokenDTO(token);
	}
}
