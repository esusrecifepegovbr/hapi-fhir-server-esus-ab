package br.com.gointerop.hapi.fhir.controller.patient;

import java.util.HashMap;
import java.util.List;

import org.hl7.fhir.dstu2016may.model.Patient;
import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryRequestComponent;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.OperationOutcome.IssueSeverity;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.adapter.patient.address.AdapterAddress;
import br.com.gointerop.hapi.fhir.mapper.IMapper;
import br.com.gointerop.hapi.fhir.mapper.patient.MapperAddress;
import br.com.gointerop.hapi.fhir.repository.IQuery;
import br.com.gointerop.hapi.fhir.repository.Query;
import br.com.gointerop.hapi.fhir.service.IService;
import br.com.gointerop.hapi.fhir.translator.ITranslator;
import br.com.gointerop.hapi.fhir.translator.TranslatorFactory;
import br.com.gointerop.hapi.fhir.translator.TranslatorResource.TranslatorCatalog;
import br.com.gointerop.hapi.fhir.util.TransactionOutcome;
import br.com.gointerop.hapi.fhir.util.UtilBaseParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.BaseParam;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

@Controller
public final class ControllerAddress extends br.com.gointerop.hapi.fhir.controller.Controller<Address> {
	private IMapper iMapper = MapperAddress.getInstance();
	private IQuery iQuery = new Query(MapperAddress.TABLE_NAME, MapperAddress.PRIMARY_KEY);
	private ITranslator iTranslator = TranslatorFactory.createInstance(TranslatorCatalog.PATIENTADDRESS);
	private IAdapter<Address> iAdapter = AdapterAddress.getInstance();
	
	@Autowired
	private IService<Address> serviceAddress;

	@Override
	public Address readById(String id) {
		List<Address> retVal = serviceAddress.readById(iQuery.readById(id.toString()), iAdapter);
		
		if(retVal.size() < 1) throw new ResourceNotFoundException(id.toString());
		
		return retVal.get(0);
	}

	@Override
	public List<Address> search(HashMap<String, BaseParam> params) {
		List<Address> retVal = null;
		HashMap<String, BaseParam> translatedValues = iTranslator.translate(params);
		HashMap<String, BaseParam> mappedColumns = iMapper.map(translatedValues);
		retVal = serviceAddress.search(iQuery.search(mappedColumns), iAdapter);
		return retVal;
	}

	@Override
	public TransactionOutcome transaction(Address resource, BundleEntryRequestComponent request)
			throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}
}
