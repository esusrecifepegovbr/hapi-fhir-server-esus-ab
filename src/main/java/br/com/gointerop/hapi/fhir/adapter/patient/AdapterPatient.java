package br.com.gointerop.hapi.fhir.adapter.patient;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hl7.fhir.helper.HelperMeta;
import org.hl7.fhir.r4.model.Patient;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.gov.pe.recife.esus.Profile;

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
		
		retVal.setMeta(HelperMeta.create(Profile.PROFILE_PATIENT));
		retVal.setId(AdapterPatientId.getInstance().mapRow(rs, rownumber));
		retVal.setIdentifier(AdapterPatientIdentifier.getInstance().mapRow(rs, rownumber));
		retVal.setName(AdapterPatientHumanName.getInstance().mapRow(rs, rownumber));
		retVal.setActive(Boolean.parseBoolean(AdapterPatientActive.getInstance().mapRow(rs, rownumber)));
		retVal.setGender(AdapterPatientGender.getInstance().mapRow(rs, rownumber));
		retVal.setBirthDate(AdapterPatientBirthdate.getInstance().mapRow(rs, rownumber));
		
		return retVal;
	}
}
