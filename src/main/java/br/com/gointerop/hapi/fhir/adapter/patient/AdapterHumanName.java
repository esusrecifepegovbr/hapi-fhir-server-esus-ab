package br.com.gointerop.hapi.fhir.adapter.patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.HumanName.NameUse;

import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.gov.pe.recife.esus.mappings.MappingPatient;

public final class AdapterHumanName implements IAdapter<List<HumanName>> {
	private static IAdapter<List<HumanName>> instance;

	public static IAdapter<List<HumanName>> getInstance() {
		if(AdapterHumanName.instance == null) {
			AdapterHumanName.instance = new AdapterHumanName();
		}
		
		return AdapterHumanName.instance;
	}
	
	@Override
	public List<HumanName> mapRow(ResultSet rs, int rownumber) throws SQLException {
		List<HumanName> retVal = new ArrayList<HumanName>();
		HumanName humanName = new HumanName();
		int indexName = -1;
		String valueName = null;
		
		try {
			 indexName = rs.findColumn(MappingPatient.name);
		} catch (SQLException e) {}
		
		if (indexName > -1) valueName = rs.getString(indexName);
		
		humanName.setUse(NameUse.OFFICIAL);
		humanName.setText(valueName);
		retVal.add(humanName);
		
		return retVal;
	}
	
}
