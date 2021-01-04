package br.com.gointerop.hapi.fhir.repository;

import java.util.HashMap;

import ca.uhn.fhir.rest.param.BaseParam;

public interface IQuery {
	public String readById(String primaryVal);

	public String search(HashMap<String, BaseParam> params);
	
	public String create(HashMap<String, BaseParam> params, String primaryVal);
	
	public String update(HashMap<String, BaseParam> params, String primaryVal);
	
	public String delete(HashMap<String, BaseParam> params, String primaryVal);
	
}
