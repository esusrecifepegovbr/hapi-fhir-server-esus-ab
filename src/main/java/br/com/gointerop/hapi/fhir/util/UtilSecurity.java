package br.com.gointerop.hapi.fhir.util;

import java.util.HashMap;

import ca.uhn.fhir.rest.param.BaseParam;

public class UtilSecurity {
	public static String createHash(HashMap<String, BaseParam> params) {
		String retVal = "";
		StringBuilder entry = new StringBuilder();
		
		for (String key : params.keySet()) {
			String value = UtilBaseParam.toValue(params.get(key));
					
			entry.append(value);
		}
		
		retVal = entry.toString().hashCode()+"";
		
		return retVal;
	}
}
