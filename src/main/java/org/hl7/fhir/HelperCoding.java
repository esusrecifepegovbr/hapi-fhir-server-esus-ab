package org.hl7.fhir;

import org.hl7.fhir.r4.model.Coding;

public class HelperCoding {
	public static Coding create(String system, String code) {
		Coding retVal = new Coding();
		
		retVal.setSystem(system);
		retVal.setCode(code);
		
		return retVal;
	}
}
