package br.com.zup.orangetalents.mercadolivre.comum.security;

import java.io.IOException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.zup.orangetalents.mercadolivre.usuario.dto.TokenDTO;
import br.com.zup.orangetalents.mercadolivre.usuario.repository.UsuarioRepository;
import br.com.zup.orangetalents.mercadolivre.usuario.service.GerenciadorToken;

@Component
public class AutenticacaoTokenFilter extends OncePerRequestFilter {
	
	private UsuarioRepository usuarioRepository;
	private GerenciadorToken gerenciadorToken;
	
	public AutenticacaoTokenFilter(UsuarioRepository usuarioRepository, GerenciadorToken gerenciadorToken) {
		this.usuarioRepository = usuarioRepository;
		this.gerenciadorToken = gerenciadorToken;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		Optional<String> token = recuperarToken(request);
		
		if (token.isPresent() && gerenciadorToken.isValid(token.get())) {
			String emailUsuario = gerenciadorToken.getIdentificadorUsuario(token.get());
			
			UserDetails usuario =  usuarioRepository.findByEmail(emailUsuario)
					.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
			
			UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private Optional<String> recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		return Optional.ofNullable(token);
	}
}
