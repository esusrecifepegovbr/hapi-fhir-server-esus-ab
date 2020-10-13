package br.com.gointerop.hapi.fhir.adapter.organization;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.HumanName.NameUse;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.mapper.MapperOrganization;

public final class AdapterOrganizationName implements IAdapter<String> {
	private static IAdapter<String> instance;

	public static IAdapter<String> getInstance() {
		if(AdapterOrganizationName.instance == null) {
			AdapterOrganizationName.instance = new AdapterOrganizationName();
		}
		
		return AdapterOrganizationName.instance;
	}
	
	@Override
	public String mapRow(ResultSet rs, int rownumber) throws SQLException {
		String retVal = null;
		int indexName = -1;
		String valueName = null;
		
		try {
			 indexName = rs.findColumn(MapperOrganization.name);
		} catch (SQLException e) {}
		
		if (indexName > -1) valueName = rs.getString(indexName);
		
		if (valueName != null) retVal = valueName;
		
		return retVal;
	}
	
}
