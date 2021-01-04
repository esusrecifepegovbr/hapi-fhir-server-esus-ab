package br.com.gointerop.hapi.fhir.controller;

import java.util.HashMap;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryRequestComponent;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.OperationOutcome.IssueSeverity;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Resource;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import com.mysql.cj.xdevapi.SessionFactory;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.adapter.patient.AdapterPatient;
import br.com.gointerop.hapi.fhir.mapper.IMapper;
import br.com.gointerop.hapi.fhir.mapper.MapperPatient;
import br.com.gointerop.hapi.fhir.repository.IQuery;
import br.com.gointerop.hapi.fhir.repository.Query;
import br.com.gointerop.hapi.fhir.service.IService;
import br.com.gointerop.hapi.fhir.service.IServicePatient;
import br.com.gointerop.hapi.fhir.service.ServicePatient;
import br.com.gointerop.hapi.fhir.translator.ITranslator;
import br.com.gointerop.hapi.fhir.translator.TranslatorFactory;
import br.com.gointerop.hapi.fhir.translator.TranslatorResource.TranslatorCatalog;
import br.com.gointerop.hapi.fhir.util.TransactionOutcome;
import br.com.gointerop.hapi.fhir.util.UtilBaseParam;
import br.com.gointerop.hapi.fhir.util.UtilSecurity;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.BaseParam;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

@Controller
public final class ControllerPatient extends br.com.gointerop.hapi.fhir.controller.Controller<Patient> {
	private IMapper iMapper = MapperPatient.getInstance();
	private IQuery iQuery = new Query(MapperPatient.TABLE_NAME, MapperPatient.PRIMARY_KEY);
	private ITranslator iTranslator = TranslatorFactory.createInstance(TranslatorCatalog.PATIENT);
	private IAdapter<Patient> iAdapter = AdapterPatient.getInstance();
	
	@Autowired
	private IService<Patient> servicePatient;

	@Override
	public Patient readById(String id) {
		List<Patient> retVal = servicePatient.readById(iQuery.readById(id.toString()), iAdapter);
		
		if(retVal.size() < 1) throw new ResourceNotFoundException(id.toString());
		
		return retVal.get(0);
	}

	@Override
	public List<Patient> search(HashMap<String, BaseParam> params) {
		List<Patient> retVal = null;
		HashMap<String, BaseParam> translatedValues = iTranslator.translate(params);
		HashMap<String, BaseParam> mappedColumns = iMapper.map(translatedValues);
		retVal = servicePatient.search(iQuery.search(mappedColumns), iAdapter);
		return retVal;
	}
	
	@Override
	public TransactionOutcome transaction(Resource theResource, BundleEntryRequestComponent request) throws IllegalArgumentException, IllegalAccessException {
		MethodOutcome theMethodOutcome = new MethodOutcome();
		OperationOutcome theOperationOutcome = new OperationOutcome();				
		HashMap<String, BaseParam> mappedColumns = iMapper.map(theResource);		
		Bundle.HTTPVerb httpVerb = request.getMethod();
		
		String primaryValue = UtilBaseParam.toValue(mappedColumns.get(MapperPatient.PRIMARY_KEY));
		mappedColumns.remove(MapperPatient.PRIMARY_KEY);
		
		try {
			switch(httpVerb) {
			case POST:
				servicePatient.create(iQuery.create(mappedColumns, "nextval('"+MapperPatient.SEQUENCE_NAME+"')"));
				break;
			case PUT:
				servicePatient.update(iQuery.update(mappedColumns, primaryValue));
				break;
			case DELETE:
				servicePatient.update(iQuery.update(mappedColumns, primaryValue));
				break;
			}
		} catch (Exception e1) {
			theOperationOutcome.addIssue().setSeverity(IssueSeverity.ERROR).setDiagnostics(e1.getMessage());
		}
		
		theMethodOutcome.setOperationOutcome(theOperationOutcome);  
		theMethodOutcome.setId(new IdType("Patient", UtilBaseParam.toValue(mappedColumns.get(MapperPatient._id)), "1"));
		
		return new TransactionOutcome(theResource, theMethodOutcome);
	}
}
