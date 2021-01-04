package br.com.gointerop.hapi.fhir.repository;

public class QueryParam {
	String value;

	public QueryParam(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
