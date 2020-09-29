package br.com.gointerop.hapi.fhir.repository.query;

import java.util.HashMap;

import ca.uhn.fhir.rest.param.BaseParam;

public interface IQuery {
	public String readById(Long id);

	public String search(HashMap<String, BaseParam> params);
}
