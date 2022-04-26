package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.Aankoop;

public class AankoopValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Aankoop.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// not null/empty wordt gedaan bij binding adhv annotation
		// email wordt ook gedaan adhv annotation
		// aantaltickets moet tussen 1 en 25 liggen --> annotation

		Aankoop aankoop = (Aankoop) target;

		// deze 3 properties moeten getallen zijn
		String[][] properties = { { "aantalTickets", aankoop.getAantalTickets() },
				{ "voetbalcode1", aankoop.getVoetbalcode1() }, { "voetbalcode2", aankoop.getVoetbalcode2() } };

		for (String[] pair : properties) {
			if (!isNumeric(pair[1])) {
				errors.rejectValue(pair[0], "must.be.numeric", "moet uit getallen bestaan");
			}
		}

		// code1 < code2
		if (isNumeric(aankoop.getVoetbalcode1()) && isNumeric(aankoop.getVoetbalcode2())) {
			if (Integer.parseInt(aankoop.getVoetbalcode1()) >= Integer.parseInt(aankoop.getVoetbalcode2())) {
				errors.rejectValue("voetbalcode1", "code1.kleiner.dan.code2",
						"voetbalcode1 moet kleiner zijn dan voetbalcode2");
			}
		}

	}

	public boolean isNumeric(String invoer) {
		if (invoer == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(invoer);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
