package br.com.gointerop.hapi.fhir.adapter.patient.address;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.mapper.patient.MapperAddress;

public class AdapterAddressPostalCode extends Adapter<String> {
	private static IAdapter<String> instance;

	public static IAdapter<String> getInstance() {
		if (AdapterAddressPostalCode.instance == null) {
			AdapterAddressPostalCode.instance = new AdapterAddressPostalCode();
		}

		return AdapterAddressPostalCode.instance;
	}

	@Override
	public String mapRow(ResultSet rs, int rownumber) throws SQLException {
		String retVal = "";
		int indexPostalCode = -1;
		String valuePostalCode = null;
		
		try {
			indexPostalCode = rs.findColumn(MapperAddress.addressPostalCode);
		} catch (SQLException e) {}
		
		if (indexPostalCode > -1) valuePostalCode = rs.getString(indexPostalCode);
		
		if (valuePostalCode != null) retVal = valuePostalCode;
		
		return retVal;
	}
}
