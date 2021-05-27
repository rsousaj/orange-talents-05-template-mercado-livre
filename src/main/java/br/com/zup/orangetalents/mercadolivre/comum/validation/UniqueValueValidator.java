package br.com.zup.orangetalents.mercadolivre.comum.validation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.Assert;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

	private String domainAttribute;
	private Class<?> klass;
	
	private EntityManager entityManager;
	
	public UniqueValueValidator(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public void initialize(UniqueValue params) {
		this.domainAttribute = params.fieldName();
		this.klass = params.domainClass();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Query query = entityManager.createQuery("SELECT 1 FROM " + klass.getName() + " WHERE " + domainAttribute + " = :value");
		query.setParameter("value", value);
		List<?> result = query.getResultList();
		Assert.state(result.size() <= 1, "Existem dois ou mais registros de " + klass.getName() + " com o mesmo atributo: " + domainAttribute);
		
		return result.isEmpty();
	}

}
