package br.com.gointerop.hapi.fhir.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public interface IAdapter<T> extends RowMapper<T> {
	public T mapRow(ResultSet rs, int rownumber) throws SQLException;
}
