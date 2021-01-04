package br.com.gointerop.hapi.fhir.repository;

import java.util.List;

import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;

@org.springframework.stereotype.Repository
public class RepositoryPatient extends Repository<Patient> {
	private final JdbcTemplate jdbcTemplateESUS;
	
	@Autowired
	public RepositoryPatient(JdbcTemplate jdbcTemplateESUS) {
		this.jdbcTemplateESUS = jdbcTemplateESUS;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED) 
	public int create(String sql) {
		return jdbcTemplateESUS.update(sql);
	}

	@Override
	@Transactional 
	public int update(String sql) {
		return jdbcTemplateESUS.update(sql);
	}

	@Override
	@Transactional 
	public int delete(String sql) {
		return jdbcTemplateESUS.update(sql);
	}

	@Override
	public List<Patient> readById(String sql, IAdapter iAdapter) {
		return jdbcTemplateESUS.query(sql, iAdapter);
	}

	@Override
	public List<Patient> search(String sql, IAdapter iAdapter) {
		return jdbcTemplateESUS.query(sql, iAdapter);
	}

}
