package br.com.gointerop.hapi.fhir.adapter.patient.address;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.mapper.patient.MapperAddress;

public class AdapterAddressId extends Adapter<String> {
	private static IAdapter<String> instance;

	public static IAdapter<String> getInstance() {
		if (AdapterAddressId.instance == null) {
			AdapterAddressId.instance = new AdapterAddressId();
		}

		return AdapterAddressId.instance;
	}

	@Override
	public String mapRow(ResultSet rs, int rownumber) throws SQLException {
		String retVal = "";
		int indexId = -1;
		String valueId = null;
		
		try {
			indexId = rs.findColumn(MapperAddress._id);
		} catch (SQLException e) {}
		
		if (indexId > -1) valueId = rs.getString(indexId);
		
		if (valueId != null) retVal = valueId;
		
		return retVal;
	}
}
