package br.com.zup.orangetalents.mercadolivre.usuario.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;

public class UsuarioRequest {

	private @NotBlank @Email String email;
	private @NotBlank @Size(min = 6) String senha;
	
	public UsuarioRequest(@NotBlank @Email String email, @NotBlank @Size(min = 6) String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}

	public Usuario toModel() {
		return new Usuario(email, senha);
	}
}
