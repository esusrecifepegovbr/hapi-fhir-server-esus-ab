package br.gov.pe.recife.esus.mappings;

import java.lang.reflect.Field;

public abstract class Mapping implements IMapping {
	public String translate(String code) {
		String retVal = null;

		for (Field field : this.getClass().getFields()) {
			boolean isPropertyEqual = false;

			try {
				String fieldName = field.getName();
				String fieldValue = field.get(null).toString();
				isPropertyEqual = fieldName.equals(code);
				
				if (isPropertyEqual)
					return fieldValue;
			} catch (Exception e) {}
		}
		
		return retVal;
	}
}
