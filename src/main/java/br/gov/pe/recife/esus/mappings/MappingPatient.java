package br.gov.pe.recife.esus.mappings;

import java.lang.reflect.Field;

public class MappingPatient extends Mapping {
	private static MappingPatient instance;
	
	public static final String _id = "co_seq_cidadao";
	public static final String _language = null;
	public static final String birthdate = "dt_nascimento";
	public static final String deceased = "dt_obito";
	public static final String addressState = "co_uf";
	public static final String gender = "co_sexo";
	public static final String link = null;
	public static final String language = null;
	public static final String addressCountry = null;
	public static final String deathDate = "dt_obito";
	public static final String phonetic = null;
	public static final String telecom = "nu_telefone_residencial";
	public static final String addressCity = "co_localidade";
	public static final String email = "ds_email";
	public static final String given = "no_cidadao";
	public static final String identifier = "nu_cpf";
	public static final String address = "ds_logradouro";
	public static final String generalPractitioner = null;
	public static final String active = "st_ativo_para_exibicao";
	public static final String addressPostalCode = "ds_cep";
	public static final String phone = "nu_telefone_celular";
	public static final String organization = null;
	public static final String addressUse = "ds_logradouro";
	public static final String name = "no_cidadao";
	public static final String family = "no_cidadao";
	
	public static final MappingPatient getInstance() {
		if(MappingPatient.instance == null) MappingPatient.instance = new MappingPatient();
		
		return MappingPatient.instance;
	}
}
