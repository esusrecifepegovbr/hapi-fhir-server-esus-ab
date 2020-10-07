package br.com.gointerop.hapi.fhir.adapter.patient;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.mapping.MappingPatient;

public class AdapterPatientBirthdate extends Adapter<Date> {
	private static IAdapter<Date> instance;
	
	public static IAdapter<Date> getInstance() {
		if(AdapterPatientBirthdate.instance == null) AdapterPatientBirthdate.instance = new AdapterPatientBirthdate();
		
		return AdapterPatientBirthdate.instance;
	}
	
	@Override
	public Date mapRow(ResultSet rs, int rownumber) throws SQLException {
		Date retVal = null;
		
		int indexColumn = -1;
		Date valueColumn = null;

		try {
			indexColumn = rs.findColumn(MappingPatient.birthdate);
		} catch (SQLException e) {
		}

		if (indexColumn > -1)
			valueColumn = rs.getDate(indexColumn);
		
		if (valueColumn != null) retVal = valueColumn;
		
		return retVal;
	}
	
}
