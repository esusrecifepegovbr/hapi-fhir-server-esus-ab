package br.com.gointerop.hapi.fhir.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.HelperCodeableConcept;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Identifier.IdentifierUse;

import br.gov.saude.semantic.BRCoreSystems;

public final class AdapterPatientIdentifier extends Adapter<List<Identifier>> {
	private static IAdapter<List<Identifier>> instance;
	
	private static final String COLUMN_TAX = "nu_cpf";
	private static final String COLUMN_HC = "nu_cns";

	public static IAdapter<List<Identifier>> getInstance() {
		if (AdapterPatientIdentifier.instance == null) {
			AdapterPatientIdentifier.instance = new AdapterPatientIdentifier();
		}

		return AdapterPatientIdentifier.instance;
	}

	@Override
	public List<Identifier> mapRow(ResultSet rs, int rownumber) throws SQLException {
		List<Identifier> retVal = new ArrayList<Identifier>();

		int indexTax = -1, indexHc = -1;
		String valueTax = null, valueHc = null;
		
		try {
			 indexTax = rs.findColumn(COLUMN_TAX);
			 indexHc = rs.findColumn(COLUMN_HC);
		} catch (SQLException e) {}
		
		if (indexTax > -1) valueTax = rs.getString(indexTax);
		
		if(valueTax != null) {
			Identifier identifier = new Identifier();
			identifier.setUse(IdentifierUse.OFFICIAL);
			identifier.setType(HelperCodeableConcept.create(BRCoreSystems.SYSTEM_BR_TIPO_DOCUMENTO_INDIVIDUO, BRCoreSystems.CODE_BR_TIPO_DOCUMENTO_INDIVIDUO_CPF));
			identifier.setValue(valueTax);
			retVal.add(identifier);
		}
		
		if (indexHc > -1) valueHc = rs.getString(indexHc);
		
		if(valueHc != null) {
			Identifier identifier = new Identifier();
			identifier.setUse(IdentifierUse.OFFICIAL);
			identifier.setType(HelperCodeableConcept.create(BRCoreSystems.SYSTEM_BR_TIPO_DOCUMENTO_INDIVIDUO, BRCoreSystems.CODE_BR_TIPO_DOCUMENTO_INDIVIDUO_CNS));
			identifier.setValue(valueHc);
			retVal.add(identifier);
		}
		
		return retVal;
	}
}
