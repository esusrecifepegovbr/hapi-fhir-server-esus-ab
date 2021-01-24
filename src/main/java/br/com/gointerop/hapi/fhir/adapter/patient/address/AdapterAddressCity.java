package br.com.gointerop.hapi.fhir.adapter.patient.address;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.mapper.patient.MapperAddress;

public class AdapterAddressCity extends Adapter<String> {
	private static IAdapter<String> instance;

	public static IAdapter<String> getInstance() {
		if (AdapterAddressCity.instance == null) {
			AdapterAddressCity.instance = new AdapterAddressCity();
		}

		return AdapterAddressCity.instance;
	}

	@Override
	public String mapRow(ResultSet rs, int rownumber) throws SQLException {
		String retVal = "";
		int indexCity = -1;
		String valueCity = null;
		
		try {
			indexCity = rs.findColumn(MapperAddress.addressCity);
		} catch (SQLException e) {}
		
		if (indexCity > -1) valueCity = rs.getString(indexCity);
		
		if (valueCity != null) retVal = valueCity;
		
		return retVal;
	}
}
