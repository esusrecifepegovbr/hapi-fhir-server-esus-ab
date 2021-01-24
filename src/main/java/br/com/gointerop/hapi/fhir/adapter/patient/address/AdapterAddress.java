package br.com.gointerop.hapi.fhir.adapter.patient.address;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.Address.AddressUse;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;

public final class AdapterAddress extends Adapter<Address> implements IAdapter<Address> {
	private static IAdapter<Address> instance;
	
	public static IAdapter<Address> getInstance() {
		if(AdapterAddress.instance == null) {
			AdapterAddress.instance = new AdapterAddress();
		}
		
		return AdapterAddress.instance;
	}
	
	@Override
	public Address mapRow(ResultSet rs, int rownumber) throws SQLException {
		Address retVal = new Address();
		
		retVal.setId(AdapterAddressId.getInstance().mapRow(rs, rownumber));
		retVal.setUse(AdapterAddressUse.getInstance().mapRow(rs, rownumber));
		retVal.setCity(AdapterAddressCity.getInstance().mapRow(rs, rownumber));
		retVal.setPostalCode(AdapterAddressPostalCode.getInstance().mapRow(rs, rownumber));
		
		return retVal;
	}
}
