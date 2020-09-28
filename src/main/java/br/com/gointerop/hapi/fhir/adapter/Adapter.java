package br.com.gointerop.hapi.fhir.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Adapter<T> implements IAdapter<T> {
	public abstract T mapRow(ResultSet rs, int rownumber) throws SQLException;
}
