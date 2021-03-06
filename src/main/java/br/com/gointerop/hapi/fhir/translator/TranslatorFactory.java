package br.com.gointerop.hapi.fhir.translator;

import br.com.gointerop.hapi.fhir.translator.TranslatorResource.TranslatorCatalog;
import br.com.gointerop.hapi.fhir.translator.patient.TranslatorAddress;

public class TranslatorFactory {
	public static ITranslator createInstance(TranslatorCatalog resourceType) {
		ITranslator iTranslator = null;

		switch (resourceType) {
		case PATIENT:
			iTranslator = new TranslatorPatient();
			break;
		case PATIENTADDRESS:
			iTranslator = new TranslatorAddress();
			break;
		case PRACTITIONER:
			iTranslator = new TranslatorPractitioner();
			break;
		case ORGANIZATION:
			iTranslator = new TranslatorOrganization();
		}

		return iTranslator;
	}
}
