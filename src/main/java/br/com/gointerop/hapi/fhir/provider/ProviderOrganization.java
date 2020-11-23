package br.com.gointerop.hapi.fhir.provider;

import java.util.HashMap;
import java.util.List;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Organization;

import br.com.gointerop.hapi.fhir.controller.ControllerOrganization;
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

public class ProviderOrganization implements IResourceProvider {
	private IController<Organization> iControllerOrganization = ControllerOrganization.getInstance();

	FhirContext fhirContext;

	public ProviderOrganization(FhirContext fhirContext) {
		this.fhirContext = fhirContext;
	}

	@Override
	public Class<? extends IBaseResource> getResourceType() {
		return Organization.class;
	}

	@Read(version = true)
	public Organization read(@IdParam IIdType theId, RequestDetails theRequestDetails) {
		return iControllerOrganization.readById(theId.getIdPartAsLong());
	}

	@Search
	public List<Organization> search(
			@OptionalParam(name = Organization.SP_RES_ID) NumberParam _id, 
			@OptionalParam(name = Organization.SP_RES_LANGUAGE) StringParam _language, 
			@OptionalParam(name = Organization.SP_ADDRESS_STATE) StringParam addressState,
			@OptionalParam(name = Organization.SP_ADDRESS_COUNTRY) StringParam addressCountry,
			@OptionalParam(name = Organization.SP_PHONETIC) StringParam phonetic,
			@OptionalParam(name = Organization.SP_ADDRESS_CITY) StringParam addressCity,
			@OptionalParam(name = Organization.SP_IDENTIFIER) StringParam identifier,
			@OptionalParam(name = Organization.SP_ADDRESS) StringParam address,
			@OptionalParam(name = Organization.SP_ACTIVE) StringParam active,
			@OptionalParam(name = Organization.SP_ADDRESS_POSTALCODE) StringParam addressPostalCode,
			@OptionalParam(name = Organization.SP_ADDRESS_USE) StringParam addressUse,
			@OptionalParam(name = Organization.SP_NAME) StringParam name
			) {
		
		HashMap<String, BaseParam> params = new HashMap<String, BaseParam>();		
		
		params.put(Organization.SP_RES_ID, _id); 
		params.put(Organization.SP_RES_LANGUAGE, _language);
		params.put(Organization.SP_ADDRESS_STATE, addressState);
		params.put(Organization.SP_ADDRESS_COUNTRY, addressCountry);
		params.put(Organization.SP_PHONETIC, phonetic);
		params.put(Organization.SP_ADDRESS_CITY, addressCity);
		params.put(Organization.SP_IDENTIFIER, identifier);
		params.put(Organization.SP_ADDRESS, address);
		params.put(Organization.SP_ACTIVE, active);
		params.put(Organization.SP_ADDRESS_POSTALCODE, addressPostalCode);
		params.put(Organization.SP_ADDRESS_USE, addressUse);
		params.put(Organization.SP_NAME, name);
		
		return iControllerOrganization.search(params);
	}

}