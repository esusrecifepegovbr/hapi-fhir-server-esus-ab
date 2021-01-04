package br.com.gointerop.hapi.fhir.mapper;

import java.util.HashMap;

import ca.uhn.fhir.rest.param.BaseParam;

public interface IMapper<T> {
	public HashMap<String, BaseParam> map(HashMap<String, BaseParam> params);
	public HashMap<String, BaseParam> map(T resource) throws IllegalArgumentException, IllegalAccessException;
}
