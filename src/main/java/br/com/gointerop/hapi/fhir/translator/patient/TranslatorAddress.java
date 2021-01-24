package br.com.gointerop.hapi.fhir.translator.patient;

import java.util.HashMap;

import org.hl7.fhir.valueset.Address;

import br.com.gointerop.hapi.fhir.translator.Translator;
import br.com.gointerop.hapi.fhir.util.UtilBaseParam;
import br.gov.saude.esusab.valueset.AddressUse;
import ca.uhn.fhir.rest.param.BaseParam;

public class TranslatorAddress extends Translator {
	private static final String FIELD_USE = "use";

	@Override
	public HashMap<String, BaseParam> translate(HashMap<String, BaseParam> params) {
		HashMap<String, BaseParam> retVal = new HashMap<String, BaseParam>();

		for (String key : params.keySet()) {
			BaseParam value = params.get(key);
			Object translatedValue = null;
			
			if(value == null) continue;
			
			switch (key) {
			case FIELD_USE:
				translatedValue = translateUse(UtilBaseParam.toValue(value));
				break;
			}
			
			if(translatedValue != null) {
				retVal.put(key, UtilBaseParam.toBaseParam(translatedValue));
			} else {
				retVal.put(key, value);
			}
		}

		return retVal;
	}

	public Object translateUse(String value) {
		int retVal = AddressUse.HOME;

		switch (value) {
		case Address.home:
			retVal = AddressUse.HOME;
			break;		
		default:
			retVal = AddressUse.HOME;
		}

		return retVal;
	}

}
