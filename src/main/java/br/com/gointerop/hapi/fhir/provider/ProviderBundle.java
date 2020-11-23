package br.com.gointerop.hapi.fhir.provider;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.Transaction;
import ca.uhn.fhir.rest.annotation.TransactionParam;

public class ProviderBundle {
	
	FhirContext fhirContext;

	public ProviderBundle(FhirContext fhirContext) {
		this.fhirContext = fhirContext;
	}
	
	@Transaction
	public Bundle transaction(@TransactionParam Bundle theInput) {
		for (BundleEntryComponent nextEntry : theInput.getEntry()) {
			// Process entry
		}

		Bundle retVal = new Bundle();
		// Populate return bundle
		return retVal;
	}
}
