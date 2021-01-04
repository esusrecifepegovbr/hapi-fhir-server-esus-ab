package br.com.gointerop.hapi.fhir.repository;

import java.util.List;

import org.hl7.fhir.r4.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;

@Repository
public class RepositoryOrganization implements IRepository<Organization> {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public RepositoryOrganization(JdbcTemplate jdbcTemplate) {
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
	public List<Organization> readById(String sql, IAdapter iAdapter) {
		return jdbcTemplate.query(sql, iAdapter);
	}

	@Override
	public List<Organization> search(String sql, IAdapter iAdapter) {
		return jdbcTemplate.query(sql, iAdapter);
	}

}
