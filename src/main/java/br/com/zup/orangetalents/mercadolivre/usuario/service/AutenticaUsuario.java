package br.com.zup.orangetalents.mercadolivre.usuario.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.zup.orangetalents.mercadolivre.usuario.repository.UsuarioRepository;

@Component
public class AutenticaUsuario implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;
	
	public AutenticaUsuario(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Dados inválidos!"));
	}

}
