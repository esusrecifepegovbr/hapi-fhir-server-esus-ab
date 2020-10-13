package br.com.gointerop.hapi.fhir.adapter.organization;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.helper.HelperCodeableConcept;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Identifier.IdentifierUse;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.coding.CodingOrganization;
import br.com.gointerop.hapi.fhir.coding.ICoding;
import br.com.gointerop.hapi.fhir.mapper.MapperOrganization;
import br.gov.pe.recife.esus.system.OrganizationIdentifier;

public final class AdapterOrganizationIdentifier extends Adapter<List<Identifier>> {
	private static IAdapter<List<Identifier>> instance;
	
	public static IAdapter<List<Identifier>> getInstance() {
		if (AdapterOrganizationIdentifier.instance == null) {
			AdapterOrganizationIdentifier.instance = new AdapterOrganizationIdentifier();
		}

		return AdapterOrganizationIdentifier.instance;
	}

	@Override
	public List<Identifier> mapRow(ResultSet rs, int rownumber) throws SQLException {
		List<Identifier> retVal = new ArrayList<Identifier>();
		ICoding iCoding = new CodingOrganization(OrganizationIdentifier.URL, org.hl7.fhir.valueset.Identifier.value.HC);

		int indexTax = -1;
		String valueTax = null;
		
		try {
			 indexTax = rs.findColumn(MapperOrganization.identifier);
		} catch (SQLException e) {}
		
		if (indexTax > -1) valueTax = rs.getString(indexTax);
		
		if(valueTax != null) {
			Identifier identifier = new Identifier();
			identifier.setUse(IdentifierUse.OFFICIAL);
			identifier.setType(HelperCodeableConcept.create(iCoding.getSystem(), iCoding.getValue()));
			identifier.setValue(valueTax);
			retVal.add(identifier);
		}
		
		return retVal;
	}
}
