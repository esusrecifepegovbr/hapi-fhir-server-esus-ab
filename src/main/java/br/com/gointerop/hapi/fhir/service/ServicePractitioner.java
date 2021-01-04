package br.com.gointerop.hapi.fhir.service;

import java.util.List;

import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.repository.RepositoryPatient;

public class ServicePractitioner implements IServicePatient {

    @Autowired
    private RepositoryPatient repository;

	@Override
	public int create(String sql) {
		return repository.create(sql);
	}

	@Override
	public int update(String sql) {
		return repository.update(sql);
	}

	@Override
	public int delete(String sql) {
		return repository.delete(sql);
	}

	@Override
	public List<Patient> readById(String sql, IAdapter iAdapter) {
		return repository.readById(sql, iAdapter);
	}

	@Override
	public List<Patient> search(String sql, IAdapter iAdapter) {
		return repository.search(sql, iAdapter);
	}
}