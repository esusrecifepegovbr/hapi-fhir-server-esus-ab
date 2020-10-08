package br.com.gointerop.hapi.fhir.provider;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Practitioner;

import br.com.gointerop.hapi.fhir.controller.ControllerPractitioner;
import br.com.gointerop.hapi.fhir.controller.IController;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.param.BaseParam;
import ca.uhn.fhir.rest.param.NumberParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;

public class ProviderPractitioner implements IResourceProvider {
	private IController<Practitioner> iControllerPractitioner = ControllerPractitioner.getInstance();

	FhirContext fhirContext;

	protected Map<String, TreeMap<Long, Practitioner>> myIdToVersionToResourceMap = Collections
			.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, LinkedList<Practitioner>> myIdToHistory = Collections.synchronizedMap(new LinkedHashMap<>());
	protected LinkedList<Practitioner> myTypeHistory = new LinkedList<>();

	public ProviderPractitioner(FhirContext fhirContext) {
		this.fhirContext = fhirContext;
	}

	@Override
	public Class<? extends IBaseResource> getResourceType() {
		return Practitioner.class;
	}

	@Read(version = true)
	public Practitioner read(@IdParam IIdType theId, RequestDetails theRequestDetails) {
		return iControllerPractitioner.readById(theId.getIdPartAsLong());
	}

	@Search
	public List<Practitioner> search(
			@OptionalParam(name = Practitioner.SP_RES_ID) NumberParam _id, 
			@OptionalParam(name = Practitioner.SP_RES_LANGUAGE) StringParam _language, 
			@OptionalParam(name = Practitioner.SP_ADDRESS_STATE) StringParam addressState,
			@OptionalParam(name = Practitioner.SP_GENDER) StringParam gender,
			@OptionalParam(name = Practitioner.SP_ADDRESS_COUNTRY) StringParam addressCountry,
			@OptionalParam(name = Practitioner.SP_PHONETIC) StringParam phonetic,
			@OptionalParam(name = Practitioner.SP_TELECOM) StringParam telecom,
			@OptionalParam(name = Practitioner.SP_ADDRESS_CITY) StringParam addressCity,
			@OptionalParam(name = Practitioner.SP_EMAIL) StringParam email,
			@OptionalParam(name = Practitioner.SP_GIVEN) StringParam given,
			@OptionalParam(name = Practitioner.SP_IDENTIFIER) StringParam identifier,
			@OptionalParam(name = Practitioner.SP_ADDRESS) StringParam address,
			@OptionalParam(name = Practitioner.SP_ACTIVE) StringParam active,
			@OptionalParam(name = Practitioner.SP_ADDRESS_POSTALCODE) StringParam addressPostalCode,
			@OptionalParam(name = Practitioner.SP_PHONE) StringParam phone,
			@OptionalParam(name = Practitioner.SP_ADDRESS_USE) StringParam addressUse,
			@OptionalParam(name = Practitioner.SP_NAME) StringParam name,
			@OptionalParam(name = Practitioner.SP_FAMILY) StringParam family
			) {
		
		HashMap<String, BaseParam> params = new HashMap<String, BaseParam>();		
		
		params.put(Practitioner.SP_RES_ID, _id); 
		params.put(Practitioner.SP_RES_LANGUAGE, _language);
		params.put(Practitioner.SP_ADDRESS_STATE, addressState);
		params.put(Practitioner.SP_GENDER, gender);
		params.put(Practitioner.SP_ADDRESS_COUNTRY, addressCountry);
		params.put(Practitioner.SP_PHONETIC, phonetic);
		params.put(Practitioner.SP_TELECOM, telecom);
		params.put(Practitioner.SP_ADDRESS_CITY, addressCity);
		params.put(Practitioner.SP_EMAIL, email);
		params.put(Practitioner.SP_GIVEN, given);
		params.put(Practitioner.SP_IDENTIFIER, identifier);
		params.put(Practitioner.SP_ADDRESS, address);
		params.put(Practitioner.SP_ACTIVE, active);
		params.put(Practitioner.SP_ADDRESS_POSTALCODE, addressPostalCode);
		params.put(Practitioner.SP_PHONE, phone);
		params.put(Practitioner.SP_ADDRESS_USE, addressUse);
		params.put(Practitioner.SP_NAME, name);
		params.put(Practitioner.SP_FAMILY, family);
		
		return iControllerPractitioner.search(params);
	}
	
}