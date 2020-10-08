package br.com.gointerop.hapi.fhir.repository;

import java.util.HashMap;

import br.com.gointerop.hapi.fhir.util.UtilBaseParam;
import ca.uhn.fhir.rest.param.BaseParam;

public class Query implements IQuery {
	private String tableName;
	private String primaryKey;
	
	public Query(String tableName, String primaryKey) {
		this.tableName = tableName;
		this.primaryKey = primaryKey;
	}
	
	@Override
	public String readById(Long id) {
		return "select * from "+this.tableName+" where "+this.primaryKey+" = " + id.toString();
	}
	
	@Override
	public String search(HashMap<String, BaseParam> params) {
		StringBuilder sb = new StringBuilder("select * from "+this.tableName);
		
		sb.append(" WHERE ");
		
		sb.append("TRUE AND ");
		
		for (String key : params.keySet()) {
			BaseParam value = params.get(key);

			if (value != null) {
				String filterValue = UtilBaseParam.toFilterValue(value);
				
				if (filterValue != null) {
					sb.append(key + filterValue);
					sb.append(" AND ");
				}
			}
		}
		
		sb.append("TRUE");

		return sb.toString();
	}
}
