package br.com.zup.orangetalents.mercadolivre.usuario.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Perfil implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nomePerfil;
	
	@Deprecated
	public Perfil() {}
	
	public Perfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.nomePerfil;
	}

}
