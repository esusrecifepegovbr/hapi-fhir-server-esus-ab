package br.com.gointerop.hapi.fhir.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hl7.fhir.r4.model.Patient;

public final class AdapterPatient extends Adapter<Patient> implements IAdapter<Patient> {
	private static IAdapter<Patient> instance;
	
	public static IAdapter<Patient> getInstance() {
		if(AdapterPatient.instance == null) {
			AdapterPatient.instance = new AdapterPatient();
		}
		
		return AdapterPatient.instance;
	}
	
	@Override
	public Patient mapRow(ResultSet rs, int rownumber) throws SQLException {
		Patient retVal = new Patient();
		
		retVal.setName(AdapterHumanName.getInstance().mapRow(rs, rownumber));
		
		return retVal;
	}
}
