package br.com.gointerop.hapi.fhir.adapter.practitioner;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.mapper.MapperPractitioner;

public class AdapterPractitionerId extends Adapter<String> {
	private static Adapter<String> instance;

	public static Adapter<String> getInstance() {
		if (AdapterPractitionerId.instance == null)
			AdapterPractitionerId.instance = new AdapterPractitionerId();

		return AdapterPractitionerId.instance;
	}

	@Override
	public String mapRow(ResultSet rs, int rownumber) throws SQLException {
		String retVal = null;

		int indexColumn = -1;
		int valueColumn = -1;

		try {
			indexColumn = rs.findColumn(MapperPractitioner._id);
		} catch (SQLException e) {
		}

		if (indexColumn > -1)
			valueColumn = rs.getInt(indexColumn);

		if (valueColumn > -1)
			retVal = valueColumn+"";

		return retVal;
	}

}
