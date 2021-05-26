package br.com.zup.orangetalents.mercadolivre.usuario.validacao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.zup.orangetalents.mercadolivre.usuario.dto.UsuarioRequest;

@Component
public class UsuarioEmailUnicoValidator implements Validator {

	private EntityManager entityManager;
	
	public UsuarioEmailUnicoValidator(EntityManager entityManager) {
		this.entityManager = entityManager;
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
		
		UsuarioRequest usuario = (UsuarioRequest) target;
		Query query = entityManager.createQuery("SELECT 1 FROM Usuario u WHERE u.email = :email");
		query.setParameter("email", usuario.getEmail());
		
		List<?> result = query.getResultList();
		Assert.isTrue(result.size() <= 1, String.format("Existem %d registros com o seguinte email: %s.", result.size(), usuario.getEmail()));
		
		if (result.size() > 0) {
			errors.rejectValue("email", null, "Já existe usuário cadastrado com o e-mail informado.");
		}
	}

	
}
