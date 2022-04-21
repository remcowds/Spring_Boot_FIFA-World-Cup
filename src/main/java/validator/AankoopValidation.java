package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.Aankoop;

public class AankoopValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
        return Aankoop.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Aankoop aankoop = (Aankoop) target;
	}

}

