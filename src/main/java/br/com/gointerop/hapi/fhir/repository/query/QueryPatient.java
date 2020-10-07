package br.com.gointerop.hapi.fhir.repository.query;

import java.util.HashMap;

import br.com.gointerop.hapi.fhir.mapping.IMapping;
import br.com.gointerop.hapi.fhir.mapping.MappingPatient;
import br.com.gointerop.hapi.fhir.util.UtilBaseParam;
import ca.uhn.fhir.rest.param.BaseParam;

public final class QueryPatient extends Query {
	private static IQuery instance;
	private static IMapping iMapping = MappingPatient.getInstance();

	public static IQuery getInstance() {
		if (QueryPatient.instance == null) {
			QueryPatient.instance = new QueryPatient();
		}

		return QueryPatient.instance;
	}

	@Override
	public String readById(Long id) {
		return "select * from "+iMapping.getTableName()+" where co_seq_cidadao = " + id.toString();
	}

	@Override
	public String search(HashMap<String, BaseParam> params) {
		StringBuilder sb = new StringBuilder("select * from "+iMapping.getTableName());

		for (String key : params.keySet()) {
			BaseParam value = params.get(key);

			if (value != null) {
				String filterValue = UtilBaseParam.toFilterValue(value);

				if (filterValue != null) {
					sb.append(" WHERE ");
					break;
				}
			}
		}

		for (String key : params.keySet()) {
			BaseParam value = params.get(key);

			if (value != null) {
				String filterValue = UtilBaseParam.toFilterValue(value);
				
				if (filterValue != null)
					sb.append(key + filterValue);
			}
		}

		return sb.toString();
	}

}
