package br.com.gointerop.hapi.fhir.controller;

import java.util.HashMap;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle.BundleEntryRequestComponent;
import org.hl7.fhir.r4.model.Resource;

import br.com.gointerop.hapi.fhir.util.TransactionOutcome;
import ca.uhn.fhir.rest.param.BaseParam;

public interface IController<T> {

	T readById(String id);

	List<T> search(HashMap<String, BaseParam> params);
	
	TransactionOutcome transaction(Resource resource, BundleEntryRequestComponent request) throws IllegalArgumentException, IllegalAccessException;
}
