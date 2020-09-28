package br.com.gointerop.hapi.fhir.repository;

public abstract class Query implements IQuery {
	public abstract String readById(Long id);
}
