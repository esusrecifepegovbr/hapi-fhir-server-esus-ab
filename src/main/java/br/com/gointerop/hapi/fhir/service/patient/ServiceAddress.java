package br.com.gointerop.hapi.fhir.service.patient;

import java.util.List;

import org.hl7.fhir.r4.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.repository.IRepository;
import br.com.gointerop.hapi.fhir.service.IServiceAddress;

@Service
public class ServiceAddress implements IServiceAddress {

	@Autowired
	private IRepository<Address> repositoryAddress;

	@Override
	public int create(String sql) {
		return repositoryAddress.create(sql);
	}

	@Override
	public int update(String sql) {
		return repositoryAddress.update(sql);
	}

	@Override
	public int delete(String sql) {
		return repositoryAddress.delete(sql);
	}

	@Override
	public List<Address> readById(String sql, IAdapter iAdapter) {
		return repositoryAddress.readById(sql, iAdapter);
	}

	@Override
	public List<Address> search(String sql, IAdapter iAdapter) {
		return repositoryAddress.search(sql, iAdapter);
	}
}