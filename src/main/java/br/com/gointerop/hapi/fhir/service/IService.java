package br.com.gointerop.hapi.fhir.service;

import java.util.List;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;

public interface IService<T> {
	public List<T> readById(String sql, IAdapter iAdapter);
	
	public List<T> search(String sql, IAdapter iAdapter);
	
	public int create(String sql);

	public int update(String sql);

	public int delete(String sql);
}
