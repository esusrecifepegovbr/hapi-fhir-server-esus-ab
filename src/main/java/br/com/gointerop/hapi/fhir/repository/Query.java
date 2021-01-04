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
	public String readById(String primaryVal) {
		return "select * from " + this.tableName + " where " + this.primaryKey + " = " + primaryVal;
	}

	@Override
	public String search(HashMap<String, BaseParam> params) {
		StringBuilder sb = new StringBuilder("select * from " + this.tableName);

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

	@Override
	public String create(HashMap<String, BaseParam> params, String primaryVal) {
		StringBuilder sb = new StringBuilder("insert into " + this.tableName);

		sb.append(" (");
		
		sb.append(this.primaryKey+",");

		for (String key : params.keySet()) {
			BaseParam value = params.get(key);

			if (value != null) {
				String createValue = UtilBaseParam.toAssignmentValue(value);

				if (createValue != null) {
					sb.append(key + ",");
				}
			}
		}

		sb.delete(sb.length() - 1, sb.length());

		sb.append(")");

		sb.append(" VALUES (");
		
		sb.append(primaryVal +",");
		
		for (String key : params.keySet()) {
			BaseParam value = params.get(key);

			if (value != null) {
				String createValue = UtilBaseParam.toAssignmentValue(value);

				if (createValue != null) {
					sb.append(createValue + ",");
				}
			}
		}

		sb.delete(sb.length() - 1, sb.length());

		sb.append(")");

		return sb.toString();
	}

	@Override
	public String update(HashMap<String, BaseParam> params, String primaryVal) {
		StringBuilder sb = new StringBuilder("update " + this.tableName);

		sb.append(" set ");

		for (String key : params.keySet()) {
			BaseParam value = params.get(key);

			if (value != null) {
				String updateValue = UtilBaseParam.toAssignmentValue(value);

				if (updateValue != null) {
					sb.append(key+" = "+updateValue+",");
				}
			}
		}

		sb.delete(sb.length() - 1, sb.length());

		sb.append(" WHERE ");
		
		sb.append(this.primaryKey+" = "+primaryVal);

		return sb.toString();
	}
	
	@Override
	public String delete(HashMap<String, BaseParam> params, String primaryVal) {
		StringBuilder sb = new StringBuilder("delete from " + this.tableName);

		sb.append(" WHERE ");

		sb.append(this.primaryKey+" = "+primaryVal);

		return sb.toString();
	}
}
