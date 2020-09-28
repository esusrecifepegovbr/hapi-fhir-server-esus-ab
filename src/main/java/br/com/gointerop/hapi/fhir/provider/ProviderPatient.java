package br.com.gointerop.hapi.fhir.provider;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

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
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.annotation.Update;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.api.server.storage.TransactionDetails;
import ca.uhn.fhir.rest.param.TokenAndListParam;
import ca.uhn.fhir.rest.param.TokenOrListParam;
import ca.uhn.fhir.rest.param.TokenParam;
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

	

	@Search
	public List<Patient> searchAll(RequestDetails theRequestDetails) {
		List<Patient> retVal = getAllResources();
		return retVal;
	}

	@Nonnull
	protected List<Patient> getAllResources() {
		List<Patient> retVal = new ArrayList<>();

		for (TreeMap<Long, Patient> next : myIdToVersionToResourceMap.values()) {
			if (next.isEmpty() == false) {
				Patient nextResource = next.lastEntry().getValue();
				if (nextResource != null) {
					retVal.add(nextResource);
				}
			}
		}

		return retVal;
	}

	@Search
	public List<Patient> searchById(@RequiredParam(name = "_id") TokenAndListParam theIds,
			RequestDetails theRequestDetails) {

		List<Patient> retVal = new ArrayList<>();

		for (TreeMap<Long, Patient> next : myIdToVersionToResourceMap.values()) {
			if (next.isEmpty() == false) {
				Patient nextResource = next.lastEntry().getValue();

				boolean matches = true;
				if (theIds != null && theIds.getValuesAsQueryTokens().size() > 0) {
					for (TokenOrListParam nextIdAnd : theIds.getValuesAsQueryTokens()) {
						matches = false;
						for (TokenParam nextOr : nextIdAnd.getValuesAsQueryTokens()) {
							if (nextOr.getValue().equals(nextResource.getIdElement().getIdPart())) {
								matches = true;
							}
						}
						if (!matches) {
							break;
						}
					}
				}

				if (!matches) {
					continue;
				}

				retVal.add(nextResource);
			}
		}

		return retVal;
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
	
	private boolean updateInternal(@ResourceParam Patient theResource, RequestDetails theRequestDetails, TransactionDetails theTransactionDetails) {
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