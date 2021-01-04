package br.com.gointerop.hapi.fhir.mapper;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Resource;

import br.com.gointerop.hapi.fhir.util.UtilBaseParam;
import ca.uhn.fhir.rest.param.BaseParam;

public class MapperPractitioner extends Mapper<Practitioner> {
	private static MapperPractitioner instance;

	public static final String TABLE_NAME = "tb_prof";
	public static final String PRIMARY_KEY = "co_ator_papel";

	public static final String _id = "co_ator_papel";
	public static final String _language = null;
	public static final String birthdate = "dt_nascimento";
	public static final String addressState = "co_uf";
	public static final String gender = "co_sexo";
	public static final String addressCountry = null;
	public static final String telecom = "nu_telefone_residencial";
	public static final String addressCity = "co_localidade_endereco";
	public static final String email = "ds_email";
	public static final String given = "no_profissional";
	public static final String identifier = "nu_cpf";
	public static final String address = "ds_logradouro";
	public static final String addressPostalCode = "ds_cep";
	public static final String addressUse = "ds_logradouro";
	public static final String name = "no_profissional";
	public static final String family = "no_profissional";

	public static final MapperPractitioner getInstance() {
		if (MapperPractitioner.instance == null)
			MapperPractitioner.instance = new MapperPractitioner();

		return MapperPractitioner.instance;
	}

	@Override
	public HashMap<String, BaseParam> map(Practitioner resource) throws IllegalArgumentException, IllegalAccessException {
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
