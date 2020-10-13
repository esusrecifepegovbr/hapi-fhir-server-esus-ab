package br.com.gointerop.hapi.fhir.adapter.organization;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.mapper.MapperOrganization;

public class AdapterOrganizationId extends Adapter<String> {
	private static Adapter<String> instance;

	public static Adapter<String> getInstance() {
		if (AdapterOrganizationId.instance == null)
			AdapterOrganizationId.instance = new AdapterOrganizationId();

		return AdapterOrganizationId.instance;
	}

	@Override
	public String mapRow(ResultSet rs, int rownumber) throws SQLException {
		String retVal = null;

		int indexColumn = -1;
		int valueColumn = -1;

		try {
			indexColumn = rs.findColumn(MapperOrganization._id);
		} catch (SQLException e) {
		}

		if (indexColumn > -1)
			valueColumn = rs.getInt(indexColumn);

		if (valueColumn > -1)
			retVal = valueColumn+"";

		return retVal;
	}

}
