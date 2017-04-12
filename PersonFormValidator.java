package com.config.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.entities.Person;

@Component
public class PersonFormValidator implements Validator {
	private Logger log = Logger.getLogger(PersonFormValidator.class);
	@Override
	public boolean supports(Class<?> userClass) {
		log.info("Enter into Person Form Validation Support...!");
		return Person.class.isAssignableFrom(userClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		log.info("Enter into Person Form Validation...!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "personData.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "personData.country");
	}
}