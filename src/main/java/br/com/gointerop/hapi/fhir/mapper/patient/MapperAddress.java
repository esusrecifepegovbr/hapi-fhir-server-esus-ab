package br.com.gointerop.hapi.fhir.mapper.patient;

import java.util.HashMap;

import org.hl7.fhir.r4.model.Address;

import br.com.gointerop.hapi.fhir.mapper.Mapper;
import br.com.gointerop.hapi.fhir.util.UtilBaseParam;
import ca.uhn.fhir.rest.param.BaseParam;

public class MapperAddress extends Mapper<Address> {
	private static MapperAddress instance;

	public static final String TABLE_NAME = "tb_localidade";
	public static final String PRIMARY_KEY = "co_localidade";
	public static final String SEQUENCE_NAME = null;

	public static final String _id = "co_localidade";
	public static final String _language = null;
	public static final String addressState = "co_uf";
	public static final String link = null;
	public static final String language = null;
	public static final String addressCountry = null;
	public static final String addressCity = "co_ibge";
	public static final String address = "ds_logradouro";
	public static final String addressPostalCode = "ds_cep";
	public static final String use = "tp_localidade";

	public static final MapperAddress getInstance() {
		if (MapperAddress.instance == null)
			MapperAddress.instance = new MapperAddress();

		return MapperAddress.instance;
	}

	@Override
	public HashMap<String, BaseParam> map(Address resource) throws IllegalArgumentException, IllegalAccessException {
		HashMap<String, BaseParam> retVal = new HashMap<String, BaseParam>();

		String idParamString = null;
		int idParamInteger = -1;
		
		try {
			String[] idParamStructure = resource.getId().split("/");
			
			if (idParamStructure.length > 0) {
				idParamString = idParamStructure[idParamStructure.length - 1];
			}
			
			idParamInteger = Integer.valueOf(idParamString);
				
		} catch (Exception e) {
		}
		
		if (idParamInteger != -1) {
			retVal.put(_id, UtilBaseParam.toBaseParam(idParamInteger));
		} 

		retVal.put("co_uf", UtilBaseParam.toBaseParam(Integer.valueOf("17")));
		retVal.put("nu_dne", UtilBaseParam.toBaseParam("00005406"));
		retVal.put("no_localidade", UtilBaseParam.toBaseParam("RECIFE"));
		retVal.put("nu_cep", null);
		retVal.put("tp_localidade", UtilBaseParam.toBaseParam(Integer.valueOf("1")));
		retVal.put("co_situacao_localidade", UtilBaseParam.toBaseParam("1"));
		retVal.put("co_ibge", UtilBaseParam.toBaseParam("2611606"));
		retVal.put("no_localidade_filtro", UtilBaseParam.toBaseParam("recife"));
		retVal.put("st_ativo", UtilBaseParam.toBaseParam(Integer.valueOf("1")));
		
		return retVal;
	}
}
