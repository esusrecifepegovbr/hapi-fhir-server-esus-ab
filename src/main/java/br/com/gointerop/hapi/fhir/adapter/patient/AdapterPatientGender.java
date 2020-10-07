package br.com.gointerop.hapi.fhir.adapter.patient;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.mapping.MappingPatient;

public class AdapterPatientGender extends Adapter<AdministrativeGender> {
	private static IAdapter<AdministrativeGender> instance;

	public static IAdapter<AdministrativeGender> getInstance() {
		if (AdapterPatientGender.instance == null) {
			AdapterPatientGender.instance = new AdapterPatientGender();
		}

		return AdapterPatientGender.instance;
	}

	@Override
	public AdministrativeGender mapRow(ResultSet rs, int rownumber) throws SQLException {
		AdministrativeGender retVal = AdministrativeGender.UNKNOWN;
		int indexGender = -1, valueGender = -1;

		try {
			indexGender = rs.findColumn(MappingPatient.gender);
		} catch (SQLException e) {
		}

		if (indexGender > -1)
			valueGender = rs.getInt(indexGender);

		switch (valueGender) {
		case br.gov.saude.esusab.valueset.AdministrativeGender.MALE:
			retVal = AdministrativeGender.MALE;
			break;
		case br.gov.saude.esusab.valueset.AdministrativeGender.FEMALE:
			retVal = AdministrativeGender.FEMALE;
			break;
		default:
			retVal = AdministrativeGender.UNKNOWN;
		}

		return retVal;
	}
}
