package br.com.gointerop.hapi.fhir.util;

import java.util.Date;

import br.com.gointerop.hapi.fhir.repository.QueryParam;
import ca.uhn.fhir.rest.param.BaseParam;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.NumberParam;
import ca.uhn.fhir.rest.param.StringParam;

public class UtilBaseParam {
	public static String toValue(Object value) {
		String retVal = null;

		if (value instanceof NumberParam) {
			retVal = ((NumberParam) value).getValue() + "";
		} else if (value instanceof StringParam) {
			retVal = ((StringParam) value).getValue();
	    } else if (value instanceof DateParam) {
			retVal = UtilDate.toISOString(((DateParam) value).getValue());
	    } else if (value instanceof QueryParam) {
	    	retVal = ((QueryParam) value).getValue();
	    }
		
		return retVal;
	}
	
	public static BaseParam toBaseParam(Object value) {
		BaseParam retVal = null;
		
		if (value instanceof String) {
			retVal = new StringParam(value.toString());
		} else if (value instanceof Date) {
			retVal = new DateParam(UtilDate.toDateTypeString((Date) value));
		} else if (value instanceof Integer) {
			retVal = new NumberParam(value.toString());
		}	
		
		return retVal;
	}
	
	public static String toFilterValue(BaseParam value) {
		String retVal = null;

		if (value instanceof NumberParam)
			retVal = " = " + UtilBaseParam.toValue(value) + "";

		if (value instanceof StringParam)
			retVal = " LIKE '%" + UtilBaseParam.toValue(value) + "%'";

		if (value instanceof DateParam)
			retVal = " = TO_DATE('" + UtilBaseParam.toValue(value) + "', 'YYYY-MM-DD HH24:MI:SS')";

		return retVal;
	}
	
	public static String toAssignmentValue(BaseParam value) {
		String retVal = null;

		if (value instanceof NumberParam)
			retVal = UtilBaseParam.toValue(value);

		if (value instanceof StringParam)
			retVal = "'" + UtilBaseParam.toValue(value) + "'";

		if (value instanceof DateParam)
			retVal = "TO_DATE('" + UtilBaseParam.toValue(value) + "', 'YYYY-MM-DD HH24:MI:SS')";

		return retVal;
	}
}
