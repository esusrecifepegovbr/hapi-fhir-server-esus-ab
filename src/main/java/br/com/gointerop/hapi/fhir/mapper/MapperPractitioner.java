package br.com.gointerop.hapi.fhir.mapper;

public class MapperPractitioner extends Mapper {
	private static MapperPractitioner instance;

	private static final String TABLE_NAME = "tb_prof";
	private static final String PRIMARY_KEY = "co_ator_papel";

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
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public String getPrimaryKey() {
		return PRIMARY_KEY;
	}

}
