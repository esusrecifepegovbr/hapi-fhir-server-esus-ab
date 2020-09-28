package br.com.gointerop.hapi.fhir.controller;

public interface IController<T> {

	T readById(Long id);
}
