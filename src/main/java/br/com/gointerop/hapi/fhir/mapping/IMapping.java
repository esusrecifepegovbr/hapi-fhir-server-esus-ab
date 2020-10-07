package br.com.gointerop.hapi.fhir.mapping;

import java.util.HashMap;

import ca.uhn.fhir.rest.param.BaseParam;

public interface IMapping {
	public String getTableName();
	public HashMap<String, BaseParam> map(HashMap<String, BaseParam> params);
}
