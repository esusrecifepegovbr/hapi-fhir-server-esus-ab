package br.com.gointerop.hapi.fhir.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hl7.fhir.HelperMeta;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.Patient;

import br.gov.pe.recife.esus.profile.ESUSRecifePEGovBRProfiles;
import br.gov.saude.semantic.ESUSABSystems;

public class AdapterPatientGender extends Adapter<AdministrativeGender> {
	private static IAdapter<AdministrativeGender> instance;

	private static final String COLUMN_NAME = "co_sexo";

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
			indexGender = rs.findColumn(COLUMN_NAME);
		} catch (SQLException e) {
		}

		if (indexGender > -1)
			valueGender = rs.getInt(indexGender);

		switch (valueGender) {
		case ESUSABSystems.CODE_PATIENT_GENDER_MALE:
			retVal = AdministrativeGender.MALE;
			break;
		case ESUSABSystems.CODE_PATIENT_GENDER_FEMALE:
			retVal = AdministrativeGender.FEMALE;
			break;
		default:
			retVal = AdministrativeGender.UNKNOWN;
		}

		return retVal;
	}
}
