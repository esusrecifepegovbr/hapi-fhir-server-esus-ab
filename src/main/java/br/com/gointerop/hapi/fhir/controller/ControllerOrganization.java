package br.com.gointerop.hapi.fhir.controller;

import java.util.HashMap;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle.BundleEntryRequestComponent;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.adapter.organization.AdapterOrganization;
import br.com.gointerop.hapi.fhir.mapper.IMapper;
import br.com.gointerop.hapi.fhir.mapper.MapperOrganization;
import br.com.gointerop.hapi.fhir.repository.IQuery;
import br.com.gointerop.hapi.fhir.repository.Query;
import br.com.gointerop.hapi.fhir.service.IServiceOrganization;
import br.com.gointerop.hapi.fhir.translator.ITranslator;
import br.com.gointerop.hapi.fhir.translator.TranslatorFactory;
import br.com.gointerop.hapi.fhir.translator.TranslatorResource.TranslatorCatalog;
import br.com.gointerop.hapi.fhir.util.TransactionOutcome;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.BaseParam;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

public final class ControllerOrganization extends Controller<Organization> {
	private IQuery iQuery = new Query(MapperOrganization.TABLE_NAME, MapperOrganization.PRIMARY_KEY);
	private IMapper iMapping = MapperOrganization.getInstance();
	private ITranslator iTranslator = TranslatorFactory.createInstance(TranslatorCatalog.ORGANIZATION);
	private IAdapter<Organization> iAdapter = AdapterOrganization.getInstance();
	
	@Autowired
    private IServiceOrganization serviceOrganization;
	
	public static IController<Organization> getInstance() {
		return new ControllerOrganization();
	}
	
	@Override
	public Organization readById(String id) {
		List<Organization> retVal = serviceOrganization.readById(iQuery.readById(id), iAdapter);
		
		if(retVal.size() < 1) throw new ResourceNotFoundException(id.toString());
		
		return retVal.get(0);
	}

	@Override
	public List<Organization> search(HashMap<String, BaseParam> params) {
		List<Organization> retVal = null;
		HashMap<String, BaseParam> translatedValues = iTranslator.translate(params);
		HashMap<String, BaseParam> mappedColumns = iMapping.map(translatedValues);
		retVal = serviceOrganization.search(iQuery.search(mappedColumns), iAdapter);
		return retVal;
	}

	@Override
	public TransactionOutcome transaction(Organization theResource, BundleEntryRequestComponent request) {
		MethodOutcome theMethodOutcome = new MethodOutcome();
		OperationOutcome theOperationOutcome = new OperationOutcome();
		theOperationOutcome.addIssue().setDiagnostics("Successful Transaction for Organization");
		theMethodOutcome.setOperationOutcome(theOperationOutcome);
		
		return new TransactionOutcome(theResource, theMethodOutcome);
	}
}
