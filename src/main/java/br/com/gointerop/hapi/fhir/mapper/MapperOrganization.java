package br.com.gointerop.hapi.fhir.mapper;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.hl7.fhir.r4.model.Organization;
import br.com.gointerop.hapi.fhir.util.UtilBaseParam;
import ca.uhn.fhir.rest.param.BaseParam;

public class MapperOrganization extends Mapper<Organization> {
	private static MapperOrganization instance;
	
	public static final String TABLE_NAME = "tb_unidade_saude";
	public static final String PRIMARY_KEY = "co_ator_papel";
	
	public static final String _id = "co_ator_papel";
	public static final String _language = null;
	public static final String addressState = "co_uf";
	public static final String language = null;
	public static final String addressCountry = null;
	public static final String phonetic = null;
	public static final String addressCity = "co_localidade";
	public static final String identifier = "nu_cnes";
	public static final String address = "ds_logradouro";
	public static final String active = "st_ativo";
	public static final String addressPostalCode = "ds_cep";
	public static final String addressUse = "ds_logradouro";
	public static final String name = "no_unidade_saude";
	
	public static final MapperOrganization getInstance() {
		if(MapperOrganization.instance == null) MapperOrganization.instance = new MapperOrganization();
		
		return MapperOrganization.instance;
	}

	@Override
	public HashMap<String, BaseParam> map(Organization resource) throws IllegalArgumentException, IllegalAccessException {
		HashMap<String, BaseParam> retVal = new HashMap<String, BaseParam>();
		
		for (Field field : resource.getClass().getFields()) {
			String resourceFieldName = field.getName();
			BaseParam resourceFieldValue = UtilBaseParam.toBaseParam(field.get(null));
			
			for (Field mapperField : this.getClass().getFields()) {
				String mapperFieldName = mapperField.getName();
				String mapperFieldValue = mapperField.get(null).toString();
				
				if(resourceFieldName.equals(mapperFieldName)) {
					retVal.put(mapperFieldValue, UtilBaseParam.toBaseParam(resourceFieldValue));
				}
			}
		}

		return retVal;
	}
}
