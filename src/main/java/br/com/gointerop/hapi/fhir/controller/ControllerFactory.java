package br.com.gointerop.hapi.fhir.controller;

import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;

public class ControllerFactory {
	@Autowired
	private ControllerPatient controllerPatient;

	public Controller create(Resource resource) {
		Controller retVal = null;
		if(resource instanceof Patient) retVal = controllerPatient;
		return retVal;
	}
}
