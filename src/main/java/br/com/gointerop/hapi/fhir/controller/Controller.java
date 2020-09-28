package br.com.gointerop.hapi.fhir.controller;

public abstract class Controller<T> implements IController<T> {
	public abstract T readById(Long id);
}
