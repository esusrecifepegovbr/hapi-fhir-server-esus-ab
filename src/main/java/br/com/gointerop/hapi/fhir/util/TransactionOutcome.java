package br.com.gointerop.hapi.fhir.util;

import org.hl7.fhir.r4.model.Resource;

import ca.uhn.fhir.rest.api.MethodOutcome;

public class TransactionOutcome {
	public Resource resource;
	public MethodOutcome methodOutcome;

	public TransactionOutcome(Resource resource, MethodOutcome methodOutcome) {
		this.resource = resource;
		this.methodOutcome = methodOutcome;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public MethodOutcome getMethodOutcome() {
		return methodOutcome;
	}

	public void setMethodOutcome(MethodOutcome methodOutcome) {
		this.methodOutcome = methodOutcome;
	}
	
	
}
