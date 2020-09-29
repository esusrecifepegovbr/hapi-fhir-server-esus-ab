package br.com.gointerop.hapi.fhir.adapter.patient;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.gov.pe.recife.esus.mappings.MappingPatient;

public class AdapterPatientId extends Adapter<String> {
	private static Adapter<String> instance;

	public static Adapter<String> getInstance() {
		if (AdapterPatientId.instance == null)
			AdapterPatientId.instance = new AdapterPatientId();

		return AdapterPatientId.instance;
	}

	@Override
	public String mapRow(ResultSet rs, int rownumber) throws SQLException {
		String retVal = null;

		int indexColumn = -1;
		int valueColumn = -1;

		try {
			indexColumn = rs.findColumn(MappingPatient._id);
		} catch (SQLException e) {
		}

		if (indexColumn > -1)
			valueColumn = rs.getInt(indexColumn);

		if (valueColumn > -1)
			retVal = valueColumn+"";

		return retVal;
	}

}
