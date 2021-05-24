package br.com.zup.orangetalents.mercadolivre.usuario.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaLimpa {

	private @NotBlank @Size(min = 6) String senha;

	public SenhaLimpa(@NotBlank @Size(min = 6) String senha) {
		super();
		this.senha = senha;
	}
	
	public String hash() {
		return new BCryptPasswordEncoder().encode(senha);
	}
	
}
