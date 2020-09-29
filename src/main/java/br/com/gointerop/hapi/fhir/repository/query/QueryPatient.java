package br.com.gointerop.hapi.fhir.repository.query;

import java.util.HashMap;

import br.com.gointerop.hapi.fhir.util.UtilBaseParam;
import br.gov.pe.recife.esus.mappings.MappingPatient;
import br.gov.pe.recife.esus.translator.TranslatorResource;
import ca.uhn.fhir.rest.param.BaseParam;

public final class QueryPatient extends Query {
	private static IQuery instance;

	public static IQuery getInstance() {
		if (QueryPatient.instance == null) {
			QueryPatient.instance = new QueryPatient();
		}

		return QueryPatient.instance;
	}

	@Override
	public String readById(Long id) {
		return "select * from tb_cidadao where co_seq_cidadao = " + id.toString();
	}

	@Override
	public String search(HashMap<String, BaseParam> params) {
		StringBuilder sb = new StringBuilder("select * from tb_cidadao");

		for (String key : params.keySet()) {
			Object value = params.get(key);

			if (value != null) {
				String translatedKey = MappingPatient.getInstance().translate(key);

				if (translatedKey != null) {
					sb.append(" WHERE ");
					break;
				}
			}
		}

		for (String key : params.keySet()) {
			BaseParam value = params.get(key);

			if (value != null) {
				String translatedKey = MappingPatient.getInstance().translate(key);
				
				String filterValue = UtilBaseParam.toFilterValue(value);
				String translatedFilterValue = null;
				
				BaseParam translatedValue = UtilBaseParam.toTranslatedValue(TranslatorResource.TranslatorCatalog.PATIENT, key, value);
				
				if(translatedValue != null) translatedFilterValue = UtilBaseParam.toFilterValue(translatedValue);

				if (translatedKey != null && translatedFilterValue != null)
					sb.append(" " + translatedKey + translatedFilterValue);

				else if (translatedKey != null && filterValue != null)
					sb.append(" " + translatedKey + filterValue);
			}
		}

		return sb.toString();
	}

}
