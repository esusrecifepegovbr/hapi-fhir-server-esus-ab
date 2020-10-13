package br.com.gointerop.hapi.fhir.mapper;

public class MapperOrganization extends Mapper {
	private static MapperOrganization instance;
	
	private static final String TABLE_NAME = "tb_unidade_saude";
	private static final String PRIMARY_KEY = "co_ator_papel";
	
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
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public String getPrimaryKey() {
		return PRIMARY_KEY;
	}
}
