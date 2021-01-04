package br.com.gointerop.hapi.fhir.repository;

import java.util.List;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;

public interface IRepository<T> {
	public List<T> readById(String sql, IAdapter iAdapter);
	
	public List<T> search(String sql, IAdapter iAdapter);
	
	public abstract int create(String sql);
	
	public abstract int update(String sql);
	
	public abstract int delete(String sql);
}
