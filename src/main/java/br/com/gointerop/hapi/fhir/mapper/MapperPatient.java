package br.com.gointerop.hapi.fhir.mapper;

import java.util.HashMap;

import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.codesystems.ContactPointSystem;

import br.com.gointerop.hapi.fhir.util.UtilBaseParam;
import br.com.gointerop.hapi.fhir.util.UtilSecurity;
import br.gov.pe.recife.esus.system.PatientIdentifier;
import ca.uhn.fhir.rest.param.BaseParam;

public class MapperPatient extends Mapper<Patient> {
	private static MapperPatient instance;

	public static final String TABLE_NAME = "tb_cidadao";
	public static final String PRIMARY_KEY = "co_seq_cidadao";
	public static final String SEQUENCE_NAME = "sq_cidadao_coseqcidadao";

	public static final String _id = "co_seq_cidadao";
	public static final String _language = null;
	public static final String birthdate = "dt_nascimento";
	public static final String deceased = "st_faleceu";
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
	public static final String addressUse = null;
	public static final String name = "no_cidadao";
	public static final String family = null;

	public static final MapperPatient getInstance() {
		if (MapperPatient.instance == null)
			MapperPatient.instance = new MapperPatient();

		return MapperPatient.instance;
	}

	@Override
	public HashMap<String, BaseParam> map(Patient resource) throws IllegalArgumentException, IllegalAccessException {
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

		retVal.put(birthdate, UtilBaseParam.toBaseParam(resource.getBirthDate()));
		retVal.put(deceased, UtilBaseParam.toBaseParam(resource.getDeceased()));
		retVal.put(deathDate, UtilBaseParam.toBaseParam(resource.getDeceasedDateTimeType()));

		if (resource.getContact() != null && resource.getContact().size() > 0 && resource.getContact().get(0) != null
				&& resource.getContact().get(0).getTelecom() != null
				&& resource.getContact().get(0).getTelecom().size() > 0
				&& resource.getContact().get(0).getTelecom().get(0) != null
				&& resource.getContact().get(0).getTelecom().get(0).getSystem() != null
				&& resource.getContact().get(0).getTelecom().get(0).getSystem().equals(ContactPointSystem.PHONE))
			retVal.put(phone, UtilBaseParam.toBaseParam(resource.getContact().get(0).getTelecom().get(0).getValue()));

		if (resource.getContact() != null && resource.getContact().size() > 0 && resource.getContact().get(0) != null
				&& resource.getContact().get(0).getTelecom() != null
				&& resource.getContact().get(0).getTelecom().size() > 0
				&& resource.getContact().get(0).getTelecom().get(0) != null
				&& resource.getContact().get(0).getTelecom().get(0).getSystem() != null
				&& resource.getContact().get(0).getTelecom().get(0).getSystem().equals(ContactPointSystem.EMAIL))
			retVal.put(email, UtilBaseParam.toBaseParam(resource.getContact().get(0).getTelecom().get(0).getValue()));

		if (resource.getName() != null && resource.getName().size() > 0 && resource.getName().get(0) != null
				&& resource.getName().get(0).getText() != null) {
			retVal.put(given, UtilBaseParam.toBaseParam(resource.getName().get(0).getText()));
			retVal.put("no_cidadao_filtro", UtilBaseParam.toBaseParam("NOME COMPLETO DO PACIENTE"));
		}

		if (resource.getIdentifier() != null && resource.getIdentifier().size() > 0) {

			for (Identifier identifierItem : resource.getIdentifier()) {

				if (identifierItem.getSystem() != null && identifierItem.getSystem().equals(PatientIdentifier.URL)
						&& identifierItem.getType() != null && identifierItem.getType().getCoding() != null
						&& identifierItem.getType().getCoding().size() > 0) {

					for (Coding coding : identifierItem.getType().getCoding()) {
						if (coding.getCode().equals(PatientIdentifier.CODE_TAX)) {
							retVal.put(identifier,
									UtilBaseParam.toBaseParam(resource.getIdentifier().get(0).getValue()));
						}
					}
				}
			}

		}
		
		retVal.put("co_nacionalidade", UtilBaseParam.toBaseParam(Integer.valueOf(1)));
		retVal.put("co_localidade", UtilBaseParam.toBaseParam(Integer.valueOf("5727")));
		retVal.put("co_unico_cidadao", UtilBaseParam.toBaseParam(UtilSecurity.createHash(retVal)));
		
		return retVal;
	}
}