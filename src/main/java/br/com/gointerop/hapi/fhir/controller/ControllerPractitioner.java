package br.com.gointerop.hapi.fhir.controller;

import java.util.HashMap;
import java.util.List;

import org.hl7.fhir.r4.model.Practitioner;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.adapter.practitioner.AdapterPractitioner;
import br.com.gointerop.hapi.fhir.mapper.IMapper;
import br.com.gointerop.hapi.fhir.mapper.MapperPractitioner;
import br.com.gointerop.hapi.fhir.repository.IQuery;
import br.com.gointerop.hapi.fhir.repository.JdbcTemplateHikariDataSource;
import br.com.gointerop.hapi.fhir.repository.Query;
import br.com.gointerop.hapi.fhir.translator.ITranslator;
import br.com.gointerop.hapi.fhir.translator.TranslatorFactory;
import br.com.gointerop.hapi.fhir.translator.TranslatorResource.TranslatorCatalog;
import ca.uhn.fhir.rest.param.BaseParam;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

public final class ControllerPractitioner extends Controller<Practitioner> {
	private IQuery iQuery = new Query(MapperPractitioner.getInstance().getTableName(), MapperPractitioner.getInstance().getPrimaryKey());
	private IMapper iMapping = MapperPractitioner.getInstance();
	private ITranslator iTranslator = TranslatorFactory.createInstance(TranslatorCatalog.PRACTITIONER);
	private IAdapter<Practitioner> iAdapter = AdapterPractitioner.getInstance();
	
	public static IController<Practitioner> getInstance() {
		return new ControllerPractitioner();
	}
	
	@Override
	public Practitioner readById(Long id) {
		List<Practitioner> retVal = JdbcTemplateHikariDataSource.getJdbcTemplate().query(iQuery.readById(id), iAdapter);
		
		if(retVal.size() < 1) throw new ResourceNotFoundException(id.toString());
		
		return retVal.get(0);
	}

	@Override
	public List<Practitioner> search(HashMap<String, BaseParam> params) {
		List<Practitioner> retVal = null;
		HashMap<String, BaseParam> translatedValues = iTranslator.translate(params);
		HashMap<String, BaseParam> mappedColumns = iMapping.map(translatedValues);
		retVal = JdbcTemplateHikariDataSource.getJdbcTemplate().query(iQuery.search(mappedColumns), iAdapter);
		return retVal;
	}
}
