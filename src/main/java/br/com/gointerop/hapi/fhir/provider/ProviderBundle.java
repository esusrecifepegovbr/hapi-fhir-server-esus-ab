package br.com.gointerop.hapi.fhir.provider;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;

import br.com.gointerop.hapi.fhir.controller.ControllerFactory;
import br.com.gointerop.hapi.fhir.controller.IController;
import br.com.gointerop.hapi.fhir.util.TransactionOutcome;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.Transaction;
import ca.uhn.fhir.rest.annotation.TransactionParam;

public class ProviderBundle {

	@Autowired
	FhirContext fhirContext;
	
	@Autowired
	private ControllerFactory controllerFactory;

	@Transaction
	public Bundle transaction(@TransactionParam Bundle theInput) {
		Bundle retVal = new Bundle();

		try {
			for (BundleEntryComponent nextEntry : theInput.getEntry()) {
				IController iController = controllerFactory.create(nextEntry.getResource());
				TransactionOutcome transactionOutcome = iController.transaction(nextEntry.getResource(),
						nextEntry.getRequest());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return retVal;
	}
}
