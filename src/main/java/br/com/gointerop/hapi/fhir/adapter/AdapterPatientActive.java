package br.com.gointerop.hapi.fhir.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdapterPatientActive extends Adapter {
	private static IAdapter<String> instance;

	private static final String COLUMN_NAME = "st_ativo_para_exibicao";

	public static IAdapter<String> getInstance() {
		if (AdapterPatientActive.instance == null) {
			AdapterPatientActive.instance = new AdapterPatientActive();
		}

		return AdapterPatientActive.instance;
	}

	@Override
	public String mapRow(ResultSet rs, int rownumber) throws SQLException {
		String retVal = "false";
		int indexActive = -1, valueActive = -1;

		try {
			indexActive = rs.findColumn(COLUMN_NAME);
		} catch (SQLException e) {
		}

		if (indexActive > -1)
			valueActive = rs.getInt(indexActive);

		switch (valueActive) {
			case 1:
				retVal = Boolean.TRUE.toString();
				break;
			default:
				retVal = Boolean.FALSE.toString();
		}

		return retVal;
	}
}
