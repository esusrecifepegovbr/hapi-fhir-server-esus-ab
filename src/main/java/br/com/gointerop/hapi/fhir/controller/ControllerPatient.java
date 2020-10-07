package br.com.gointerop.hapi.fhir.controller;

import java.util.HashMap;
import java.util.List;

import org.hl7.fhir.r4.model.Patient;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.adapter.patient.AdapterPatient;
import br.com.gointerop.hapi.fhir.mapping.IMapping;
import br.com.gointerop.hapi.fhir.mapping.MappingPatient;
import br.com.gointerop.hapi.fhir.repository.JdbcTemplateHikariDataSource;
import br.com.gointerop.hapi.fhir.repository.query.IQuery;
import br.com.gointerop.hapi.fhir.repository.query.QueryPatient;
import br.com.gointerop.hapi.fhir.translator.ITranslator;
import br.com.gointerop.hapi.fhir.translator.TranslatorFactory;
import br.com.gointerop.hapi.fhir.translator.TranslatorResource.TranslatorCatalog;
import ca.uhn.fhir.rest.param.BaseParam;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

public final class ControllerPatient extends Controller<Patient> {
	private IQuery iQuery = QueryPatient.getInstance();
	private IMapping iMapping = MappingPatient.getInstance();
	private ITranslator iTranslator = TranslatorFactory.createInstance(TranslatorCatalog.PATIENT);
	private IAdapter<Patient> iAdapter = AdapterPatient.getInstance();
	
	public static IController<Patient> getInstance() {
		return new ControllerPatient();
	}
	
	@Override
	public Patient readById(Long id) {
		List<Patient> retVal = JdbcTemplateHikariDataSource.getJdbcTemplate().query(iQuery.readById(id), iAdapter);
		
		if(retVal.size() < 1) throw new ResourceNotFoundException(id.toString());
		
		return retVal.get(0);
	}

	@Override
	public List<Patient> search(HashMap<String, BaseParam> params) {
		HashMap<String, BaseParam> translatedValues = iTranslator.translate(params);
		HashMap<String, BaseParam> mappedColumns = iMapping.map(translatedValues);
		return JdbcTemplateHikariDataSource.getJdbcTemplate().query(iQuery.search(mappedColumns), iAdapter);
	}
}
