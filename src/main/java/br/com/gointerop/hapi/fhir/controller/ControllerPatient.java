package br.com.gointerop.hapi.fhir.controller;

import java.util.List;

import org.hl7.fhir.r4.model.Patient;

import br.com.gointerop.hapi.fhir.adapter.AdapterPatient;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.repository.IQuery;
import br.com.gointerop.hapi.fhir.repository.JdbcTemplateHikariDataSource;
import br.com.gointerop.hapi.fhir.repository.QueryPatient;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

public final class ControllerPatient extends Controller<Patient> {
	private IQuery iQuery = QueryPatient.getInstance();
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
}
