package br.com.gointerop.hapi.fhir.repository.patient;

import java.util.List;

import org.hl7.fhir.r4.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.repository.Repository;

@org.springframework.stereotype.Repository
public class RepositoryAddress extends Repository<Address> {
	private final JdbcTemplate jdbcTemplateESUS;
	
	@Autowired
	public RepositoryAddress(JdbcTemplate jdbcTemplateESUS) {
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
	public List<Address> readById(String sql, IAdapter iAdapter) {
		return jdbcTemplateESUS.query(sql, iAdapter);
	}

	@Override
	public List<Address> search(String sql, IAdapter iAdapter) {
		return jdbcTemplateESUS.query(sql, iAdapter);
	}

}
