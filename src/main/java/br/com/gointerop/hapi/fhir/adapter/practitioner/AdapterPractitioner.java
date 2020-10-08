package br.com.gointerop.hapi.fhir.adapter.practitioner;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hl7.fhir.helper.HelperMeta;
import org.hl7.fhir.r4.model.Practitioner;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.gov.pe.recife.esus.Profile;

public final class AdapterPractitioner extends Adapter<Practitioner> implements IAdapter<Practitioner> {
	private static IAdapter<Practitioner> instance;
	
	public static IAdapter<Practitioner> getInstance() {
		if(AdapterPractitioner.instance == null) {
			AdapterPractitioner.instance = new AdapterPractitioner();
		}
		
		return AdapterPractitioner.instance;
	}
	
	@Override
	public Practitioner mapRow(ResultSet rs, int rownumber) throws SQLException {
		Practitioner retVal = new Practitioner();
		
		retVal.setMeta(HelperMeta.create(Profile.PROFILE_PRACTITIONER));
		retVal.setId(AdapterPractitionerId.getInstance().mapRow(rs, rownumber));
		retVal.setIdentifier(AdapterPractitionerIdentifier.getInstance().mapRow(rs, rownumber));
		retVal.setName(AdapterPractitionerHumanName.getInstance().mapRow(rs, rownumber));
		retVal.setGender(AdapterPractitionerGender.getInstance().mapRow(rs, rownumber));
		retVal.setBirthDate(AdapterPractitionerBirthdate.getInstance().mapRow(rs, rownumber));
		
		return retVal;
	}
}
