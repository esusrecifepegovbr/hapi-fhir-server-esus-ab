package br.com.gointerop.hapi.fhir.service;

import java.util.List;

import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.repository.IRepository;

@Service
public class ServicePatient implements IServicePatient {

	@Autowired
	private IRepository<Patient> repositoryPatient;

	@Override
	public int create(String sql) {
		return repositoryPatient.create(sql);
	}

	@Override
	public int update(String sql) {
		return repositoryPatient.update(sql);
	}

	@Override
	public int delete(String sql) {
		return repositoryPatient.delete(sql);
	}

	@Override
	public List<Patient> readById(String sql, IAdapter iAdapter) {
		return repositoryPatient.readById(sql, iAdapter);
	}

	@Override
	public List<Patient> search(String sql, IAdapter iAdapter) {
		return repositoryPatient.search(sql, iAdapter);
	}
}