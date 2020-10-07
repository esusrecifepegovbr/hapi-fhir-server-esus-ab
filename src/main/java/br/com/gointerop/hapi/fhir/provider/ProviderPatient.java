package br.com.gointerop.hapi.fhir.provider;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.gointerop.hapi.fhir.controller.ControllerPatient;
import br.com.gointerop.hapi.fhir.controller.IController;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.interceptor.api.HookParams;
import ca.uhn.fhir.interceptor.api.IInterceptorBroadcaster;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.annotation.ConditionalUrlParam;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.Delete;
import ca.uhn.fhir.rest.annotation.History;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.annotation.Update;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.api.server.storage.TransactionDetails;
import ca.uhn.fhir.rest.param.BaseParam;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.NumberParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import ca.uhn.fhir.rest.server.provider.HashMapResourceProvider;
import ca.uhn.fhir.rest.server.servlet.ServletRequestDetails;
import ca.uhn.fhir.util.ValidateUtil;

public class ProviderPatient implements IResourceProvider {
	private IController<Patient> iControllerPatient = ControllerPatient.getInstance();

	FhirContext fhirContext;

	private static final Logger ourLog = LoggerFactory.getLogger(HashMapResourceProvider.class);

	private final String myResourceName;

	protected Map<String, TreeMap<Long, Patient>> myIdToVersionToResourceMap = Collections
			.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, LinkedList<Patient>> myIdToHistory = Collections.synchronizedMap(new LinkedHashMap<>());
	protected LinkedList<Patient> myTypeHistory = new LinkedList<>();

	private long myNextId;

	private AtomicLong myCreateCount = new AtomicLong(0);

	public ProviderPatient(FhirContext fhirContext) {
		this.fhirContext = fhirContext;
		myResourceName = this.fhirContext.getResourceType(Patient.class);
	}

	@Override
	public Class<? extends IBaseResource> getResourceType() {
		return Patient.class;
	}

	@Read(version = true)
	public Patient read(@IdParam IIdType theId, RequestDetails theRequestDetails) {
		return iControllerPatient.readById(theId.getIdPartAsLong());
	}

	@Search
	public List<Patient> search(
			@OptionalParam(name = Patient.SP_RES_ID) NumberParam _id, 
			@OptionalParam(name = Patient.SP_RES_LANGUAGE) StringParam _language, 
			@OptionalParam(name = Patient.SP_BIRTHDATE) DateParam birthDate, 
			@OptionalParam(name = Patient.SP_DECEASED) StringParam deceased, 
			@OptionalParam(name = Patient.SP_ADDRESS_STATE) StringParam addressState,
			@OptionalParam(name = Patient.SP_GENDER) StringParam gender,
			@OptionalParam(name = Patient.SP_LINK) StringParam link,
			@OptionalParam(name = Patient.SP_LANGUAGE) StringParam language,
			@OptionalParam(name = Patient.SP_ADDRESS_COUNTRY) StringParam addressCountry,
			@OptionalParam(name = Patient.SP_DEATH_DATE) StringParam deathDate,
			@OptionalParam(name = Patient.SP_PHONETIC) StringParam phonetic,
			@OptionalParam(name = Patient.SP_TELECOM) StringParam telecom,
			@OptionalParam(name = Patient.SP_ADDRESS_CITY) StringParam addressCity,
			@OptionalParam(name = Patient.SP_EMAIL) StringParam email,
			@OptionalParam(name = Patient.SP_GIVEN) StringParam given,
			@OptionalParam(name = Patient.SP_IDENTIFIER) StringParam identifier,
			@OptionalParam(name = Patient.SP_ADDRESS) StringParam address,
			@OptionalParam(name = Patient.SP_GENERAL_PRACTITIONER) StringParam generalPractitioner,
			@OptionalParam(name = Patient.SP_ACTIVE) StringParam active,
			@OptionalParam(name = Patient.SP_ADDRESS_POSTALCODE) StringParam addressPostalCode,
			@OptionalParam(name = Patient.SP_PHONE) StringParam phone,
			@OptionalParam(name = Patient.SP_ORGANIZATION) StringParam organization,
			@OptionalParam(name = Patient.SP_ADDRESS_USE) StringParam addressUse,
			@OptionalParam(name = Patient.SP_NAME) StringParam name,
			@OptionalParam(name = Patient.SP_FAMILY) StringParam family
			) {
		
		HashMap<String, BaseParam> params = new HashMap<String, BaseParam>();		
		
		params.put(Patient.SP_RES_ID, _id); 
		params.put(Patient.SP_RES_LANGUAGE, _language);
		params.put(Patient.SP_BIRTHDATE, birthDate);
		params.put(Patient.SP_DECEASED, deceased);
		params.put(Patient.SP_ADDRESS_STATE, addressState);
		params.put(Patient.SP_GENDER, gender);
		params.put(Patient.SP_LINK, link);
		params.put(Patient.SP_LANGUAGE, language);
		params.put(Patient.SP_ADDRESS_COUNTRY, addressCountry);
		params.put(Patient.SP_DEATH_DATE, deathDate);
		params.put(Patient.SP_PHONETIC, phonetic);
		params.put(Patient.SP_TELECOM, telecom);
		params.put(Patient.SP_ADDRESS_CITY, addressCity);
		params.put(Patient.SP_EMAIL, email);
		params.put(Patient.SP_GIVEN, given);
		params.put(Patient.SP_IDENTIFIER, identifier);
		params.put(Patient.SP_ADDRESS, address);
		params.put(Patient.SP_GENERAL_PRACTITIONER, generalPractitioner);
		params.put(Patient.SP_ACTIVE, active);
		params.put(Patient.SP_ADDRESS_POSTALCODE, addressPostalCode);
		params.put(Patient.SP_PHONE, phone);
		params.put(Patient.SP_ORGANIZATION, organization);
		params.put(Patient.SP_ADDRESS_USE, addressUse);
		params.put(Patient.SP_NAME, name);
		params.put(Patient.SP_FAMILY, family);
		
		return iControllerPatient.search(params);
	}

	@Create
	public MethodOutcome create(@ResourceParam Patient theResource, RequestDetails theRequestDetails) {
		TransactionDetails transactionDetails = new TransactionDetails();

		createInternal(theResource, theRequestDetails, transactionDetails);

		myCreateCount.incrementAndGet();

		return new MethodOutcome().setCreated(true).setResource(theResource).setId(theResource.getIdElement());
	}

	private void createInternal(@ResourceParam Patient theResource, RequestDetails theRequestDetails,
			TransactionDetails theTransactionDetails) {
		long idPart = myNextId++;
		String idPartAsString = Long.toString(idPart);
		Long versionIdPart = 1L;

		IIdType id = store(theResource, idPartAsString, versionIdPart, theRequestDetails, theTransactionDetails);
		theResource.setId(id);
	}

	private IIdType store(@ResourceParam Patient theResource, String theIdPart, Long theVersionIdPart,
			RequestDetails theRequestDetails, TransactionDetails theTransactionDetails) {
		IIdType id = fhirContext.getVersion().newIdType();
		String versionIdPart = Long.toString(theVersionIdPart);
		id.setParts(null, myResourceName, theIdPart, versionIdPart);
		if (theResource != null) {
			theResource.setId(id);
		}

		/*
		 * This is a bit of magic to make sure that the versionId attribute in the
		 * resource being stored accurately represents the version that was assigned by
		 * this provider
		 */
		//
		// TODO Implementar create aqui

		ourLog.info("Storing resource with ID: {}", id.getValue());

		// Store to ID->version->resource map
		TreeMap<Long, Patient> versionToResource = getVersionToResource(theIdPart);
		versionToResource.put(theVersionIdPart, theResource);

		if (theRequestDetails != null) {
			IInterceptorBroadcaster interceptorBroadcaster = theRequestDetails.getInterceptorBroadcaster();

			if (theResource != null) {
				if (!myIdToHistory.containsKey(theIdPart)) {

					// Interceptor call: STORAGE_PRESTORAGE_RESOURCE_CREATED
					HookParams params = new HookParams().add(RequestDetails.class, theRequestDetails)
							.addIfMatchesType(ServletRequestDetails.class, theRequestDetails)
							.add(IBaseResource.class, theResource).add(TransactionDetails.class, theTransactionDetails);
					interceptorBroadcaster.callHooks(Pointcut.STORAGE_PRESTORAGE_RESOURCE_CREATED, params);
					interceptorBroadcaster.callHooks(Pointcut.STORAGE_PRECOMMIT_RESOURCE_CREATED, params);

				} else {

					// Interceptor call: STORAGE_PRESTORAGE_RESOURCE_UPDATED
					HookParams params = new HookParams().add(RequestDetails.class, theRequestDetails)
							.addIfMatchesType(ServletRequestDetails.class, theRequestDetails)
							.add(IBaseResource.class, myIdToHistory.get(theIdPart).getFirst())
							.add(IBaseResource.class, theResource).add(TransactionDetails.class, theTransactionDetails);
					interceptorBroadcaster.callHooks(Pointcut.STORAGE_PRESTORAGE_RESOURCE_UPDATED, params);
					interceptorBroadcaster.callHooks(Pointcut.STORAGE_PRECOMMIT_RESOURCE_UPDATED, params);

				}
			}
		}

		// Store to type history map
		myTypeHistory.addFirst(theResource);

		// Store to ID history map
		myIdToHistory.computeIfAbsent(theIdPart, t -> new LinkedList<>());
		myIdToHistory.get(theIdPart).addFirst(theResource);

		// Return the newly assigned ID including the version ID
		return id;
	}

	private synchronized TreeMap<Long, Patient> getVersionToResource(String theIdPart) {
		myIdToVersionToResourceMap.computeIfAbsent(theIdPart, t -> new TreeMap<>());
		return myIdToVersionToResourceMap.get(theIdPart);
	}

	@Delete
	public MethodOutcome delete(@IdParam IIdType theId, RequestDetails theRequestDetails) {
		TransactionDetails transactionDetails = new TransactionDetails();

		TreeMap<Long, Patient> versions = myIdToVersionToResourceMap.get(theId.getIdPart());
		if (versions == null || versions.isEmpty()) {
			throw new ResourceNotFoundException(theId);
		}

		long nextVersion = versions.lastEntry().getKey() + 1L;
		IIdType id = store(null, theId.getIdPart(), nextVersion, theRequestDetails, transactionDetails);

		return new MethodOutcome().setId(id);
	}

	@History
	public List<Patient> historyInstance(@IdParam IIdType theId, RequestDetails theRequestDetails) {
		LinkedList<Patient> retVal = myIdToHistory.get(theId.getIdPart());
		if (retVal == null) {
			throw new ResourceNotFoundException(theId);
		}

		return retVal;
	}

	@History
	public List<Patient> historyType() {
		return myTypeHistory;
	}

	@Update
	public MethodOutcome update(@ResourceParam Patient theResource, @ConditionalUrlParam String theConditional,
			RequestDetails theRequestDetails) {
		TransactionDetails transactionDetails = new TransactionDetails();

		ValidateUtil.isTrueOrThrowInvalidRequest(isBlank(theConditional),
				"This server doesn't support conditional update");

		boolean created = updateInternal(theResource, theRequestDetails, transactionDetails);

		return new MethodOutcome().setCreated(created).setResource(theResource).setId(theResource.getIdElement());
	}

	private boolean updateInternal(@ResourceParam Patient theResource, RequestDetails theRequestDetails,
			TransactionDetails theTransactionDetails) {
		String idPartAsString = theResource.getIdElement().getIdPart();
		TreeMap<Long, Patient> versionToResource = getVersionToResource(idPartAsString);

		Long versionIdPart;
		boolean created;
		if (versionToResource.isEmpty()) {
			versionIdPart = 1L;
			created = true;
		} else {
			versionIdPart = versionToResource.lastKey() + 1L;
			created = false;
		}

		IIdType id = store(theResource, idPartAsString, versionIdPart, theRequestDetails, theTransactionDetails);
		theResource.setId(id);
		return created;
	}
}