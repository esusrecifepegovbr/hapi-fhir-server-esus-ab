package br.com.gointerop.hapi.fhir.controller;

import java.util.HashMap;
import java.util.List;

import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.rest.param.BaseParam;

public interface IController<T> {

	T readById(Long id);

	List<Patient> search(HashMap<String, BaseParam> params);
}
