package br.gov.pe.recife.esus.translator;

import br.gov.pe.recife.esus.translator.TranslatorResource.TranslatorCatalog;

public class TranslatorFactory {
	public static ITranslator createInstance(TranslatorCatalog resourceType) {
		ITranslator iTranslator = null;

		switch (resourceType) {
		case PATIENT:
			iTranslator = new TranslatorPatient();
			break;
		}

		return iTranslator;
	}
}
