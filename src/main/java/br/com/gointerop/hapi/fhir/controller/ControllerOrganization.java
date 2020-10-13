package br.com.gointerop.hapi.fhir.controller;

import java.util.HashMap;
import java.util.List;

import org.hl7.fhir.r4.model.Organization;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.adapter.organization.AdapterOrganization;
import br.com.gointerop.hapi.fhir.mapper.IMapper;
import br.com.gointerop.hapi.fhir.mapper.MapperOrganization;
import br.com.gointerop.hapi.fhir.repository.IQuery;
import br.com.gointerop.hapi.fhir.repository.JdbcTemplateHikariDataSource;
import br.com.gointerop.hapi.fhir.repository.Query;
import br.com.gointerop.hapi.fhir.translator.ITranslator;
import br.com.gointerop.hapi.fhir.translator.TranslatorFactory;
import br.com.gointerop.hapi.fhir.translator.TranslatorResource.TranslatorCatalog;
import ca.uhn.fhir.rest.param.BaseParam;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

public final class ControllerOrganization extends Controller<Organization> {
	private IQuery iQuery = new Query(MapperOrganization.getInstance().getTableName(), MapperOrganization.getInstance().getPrimaryKey());
	private IMapper iMapping = MapperOrganization.getInstance();
	private ITranslator iTranslator = TranslatorFactory.createInstance(TranslatorCatalog.ORGANIZATION);
	private IAdapter<Organization> iAdapter = AdapterOrganization.getInstance();
	
	public static IController<Organization> getInstance() {
		return new ControllerOrganization();
	}
	
	@Override
	public Organization readById(Long id) {
		List<Organization> retVal = JdbcTemplateHikariDataSource.getJdbcTemplate().query(iQuery.readById(id), iAdapter);
		
		if(retVal.size() < 1) throw new ResourceNotFoundException(id.toString());
		
		return retVal.get(0);
	}

	@Override
	public List<Organization> search(HashMap<String, BaseParam> params) {
		List<Organization> retVal = null;
		HashMap<String, BaseParam> translatedValues = iTranslator.translate(params);
		HashMap<String, BaseParam> mappedColumns = iMapping.map(translatedValues);
		retVal = JdbcTemplateHikariDataSource.getJdbcTemplate().query(iQuery.search(mappedColumns), iAdapter);
		return retVal;
	}
}
