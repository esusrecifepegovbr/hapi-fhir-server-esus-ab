package br.com.gointerop.hapi.fhir.adapter.practitioner;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.mapper.MapperPractitioner;

public class AdapterPractitionerBirthdate extends Adapter<Date> {
	private static IAdapter<Date> instance;
	
	public static IAdapter<Date> getInstance() {
		if(AdapterPractitionerBirthdate.instance == null) AdapterPractitionerBirthdate.instance = new AdapterPractitionerBirthdate();
		
		return AdapterPractitionerBirthdate.instance;
	}
	
	@Override
	public Date mapRow(ResultSet rs, int rownumber) throws SQLException {
		Date retVal = null;
		
		int indexColumn = -1;
		Date valueColumn = null;

		try {
			indexColumn = rs.findColumn(MapperPractitioner.birthdate);
		} catch (SQLException e) {
		}

		if (indexColumn > -1)
			valueColumn = rs.getDate(indexColumn);
		
		if (valueColumn != null) retVal = valueColumn;
		
		return retVal;
	}
	
}
