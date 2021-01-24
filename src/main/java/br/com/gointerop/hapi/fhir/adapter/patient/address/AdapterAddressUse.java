package br.com.gointerop.hapi.fhir.adapter.patient.address;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hl7.fhir.r4.model.Address.AddressUse;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.mapper.patient.MapperAddress;

public class AdapterAddressUse extends Adapter<AddressUse> {
	private static IAdapter<AddressUse> instance;

	public static IAdapter<AddressUse> getInstance() {
		if (AdapterAddressUse.instance == null) {
			AdapterAddressUse.instance = new AdapterAddressUse();
		}

		return AdapterAddressUse.instance;
	}

	@Override
	public AddressUse mapRow(ResultSet rs, int rownumber) throws SQLException {
		AddressUse retVal = AddressUse.HOME;
		int indexuse = -1, valueUse = -1;

		try {
			indexuse = rs.findColumn(MapperAddress.use);
		} catch (SQLException e) {
		}

		if (indexuse > -1)
			valueUse = rs.getInt(indexuse);

		switch (valueUse) {
			case 1:
				retVal = AddressUse.HOME;
				break;
			default:
				retVal = AddressUse.HOME;
		}

		return retVal;
	}
}
