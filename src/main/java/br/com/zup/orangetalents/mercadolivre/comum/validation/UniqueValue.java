package br.com.zup.orangetalents.mercadolivre.comum.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueValueValidator.class)
public @interface UniqueValue {
	
	String message() default "{javax.validation.constraints.UniqueValue.message}";
	Class<?> domainClass();
	String fieldName();
	
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
