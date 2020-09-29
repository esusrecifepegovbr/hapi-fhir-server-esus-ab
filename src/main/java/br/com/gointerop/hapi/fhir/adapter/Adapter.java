package br.com.gointerop.hapi.fhir.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hl7.fhir.r4.model.Patient;

import br.com.gointerop.hapi.fhir.adapter.patient.AdapterPatient;

public abstract class Adapter<T> implements IAdapter<T> {
}
