package br.com.gointerop.hapi.fhir.repository;

import java.util.List;

import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;

@Repository
public class RepositoryPractitioner implements IRepository<Practitioner> {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public RepositoryPractitioner(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional 
	public int create(String sql) {
		return jdbcTemplate.update(sql);
	}

	@Override
	@Transactional 
	public int update(String sql) {
		return jdbcTemplate.update(sql);
	}

	@Override
	@Transactional 
	public int delete(String sql) {
		return jdbcTemplate.update(sql);
	}

	@Override
	public List<Practitioner> readById(String sql, IAdapter iAdapter) {
		return jdbcTemplate.query(sql, iAdapter);
	}

	@Override
	public List<Practitioner> search(String sql, IAdapter iAdapter) {
		return jdbcTemplate.query(sql, iAdapter);
	}

}
