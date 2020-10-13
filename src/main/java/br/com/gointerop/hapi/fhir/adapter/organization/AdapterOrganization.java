package br.com.gointerop.hapi.fhir.adapter.organization;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hl7.fhir.helper.HelperMeta;
import org.hl7.fhir.r4.model.Organization;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.gov.pe.recife.esus.Profile;

public final class AdapterOrganization extends Adapter<Organization> implements IAdapter<Organization> {
	private static IAdapter<Organization> instance;
	
	public static IAdapter<Organization> getInstance() {
		if(AdapterOrganization.instance == null) {
			AdapterOrganization.instance = new AdapterOrganization();
		}
		
		return AdapterOrganization.instance;
	}
	
	@Override
	public Organization mapRow(ResultSet rs, int rownumber) throws SQLException {
		Organization retVal = new Organization();
		
		retVal.setMeta(HelperMeta.create(Profile.PROFILE_ORGANIZATION));
		retVal.setId(AdapterOrganizationId.getInstance().mapRow(rs, rownumber));
		retVal.setIdentifier(AdapterOrganizationIdentifier.getInstance().mapRow(rs, rownumber));
		retVal.setName(AdapterOrganizationName.getInstance().mapRow(rs, rownumber));
		retVal.setActive(Boolean.parseBoolean(AdapterOrganizationActive.getInstance().mapRow(rs, rownumber)));
		
		return retVal;
	}
}
