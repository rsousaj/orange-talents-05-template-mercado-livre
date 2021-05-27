package br.com.zup.orangetalents.mercadolivre.usuario.validacao;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.zup.orangetalents.mercadolivre.usuario.dto.UsuarioRequest;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;
import br.com.zup.orangetalents.mercadolivre.usuario.repository.UsuarioRepository;

@Component
public class UsuarioEmailUnicoValidator implements Validator {

	private UsuarioRepository usuarioRepository;
	
	public UsuarioEmailUnicoValidator(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UsuarioRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (errors.hasErrors()) {
			return;
		}
		
		UsuarioRequest usuarioRequest = (UsuarioRequest) target;
		Optional<Usuario> possivelUsuario = usuarioRepository.findByEmail(usuarioRequest.getEmail());
		
		if (possivelUsuario.isPresent()) {
			errors.rejectValue("email", null, "Já existe usuário cadastrado com o e-mail informado.");
		}
	}

	
}
