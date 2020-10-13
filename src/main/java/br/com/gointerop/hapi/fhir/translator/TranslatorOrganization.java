package br.com.gointerop.hapi.fhir.translator;

import java.util.HashMap;

import br.com.gointerop.hapi.fhir.util.UtilBaseParam;
import br.gov.saude.esusab.valueset.AdministrativeGender;
import ca.uhn.fhir.rest.param.BaseParam;

public class TranslatorOrganization extends Translator {
	private static final String FIELD_GENDER = "gender";

	@Override
	public HashMap<String, BaseParam> translate(HashMap<String, BaseParam> params) {
		HashMap<String, BaseParam> retVal = new HashMap<String, BaseParam>();

		for (String key : params.keySet()) {
			BaseParam value = params.get(key);
			Object translatedValue = null;
			
			if(value == null) continue;
			
			switch (key) {
			case FIELD_GENDER:
				default:
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

}
