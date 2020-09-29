package br.gov.pe.recife.esus.translator;

import org.hl7.fhir.valueset.Patient;

import br.gov.saude.esusab.valueset.AdministrativeGender;

public class TranslatorPatient extends Translator {
	private static final String FIELD_GENDER = "gender";

	@Override
	public Object translate(String fieldName, String value) {
		Object retVal = null;

		switch (fieldName) {
		case FIELD_GENDER:
			retVal = Integer.valueOf(translateGender(value).toString());
			break;
		}

		return retVal;
	}

	public Object translateGender(String value) {
		int retVal = AdministrativeGender.UNKNOWN;

		switch (value) {
		case Patient.GENDER_MALE:
			retVal = AdministrativeGender.MALE;
			break;
		case Patient.GENDER_FEMALE:
			retVal = AdministrativeGender.FEMALE;
			break;
		default:
			retVal = AdministrativeGender.UNKNOWN;
		}

		return retVal;
	}

}
