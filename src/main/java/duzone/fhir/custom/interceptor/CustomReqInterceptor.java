package duzone.fhir.custom.interceptor;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.api.Constants;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.api.server.ResponseDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import ca.uhn.fhir.util.ParametersUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hl7.fhir.instance.model.api.IBaseParameters;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
public class CustomReqInterceptor {
	private static final Logger ourLog = LoggerFactory.getLogger(CustomReqInterceptor.class);

	ObjectMapper mapper = new ObjectMapper();

	@Hook(Pointcut.SERVER_INCOMING_REQUEST_POST_PROCESSED)
	public void incomingRequestPostProcessed(RequestDetails theRequestDetails, HttpServletRequest theRequest, HttpServletResponse theResponse) throws AuthenticationException {
		try {
			String method = theRequest.getMethod();
			System.out.println(theRequest.getHeader("header-agent"));
			ourLog.info(" CustomRequestInterceptor http-method ::: {}",method);
			IBaseResource resource = theRequestDetails.getResource();
			if (resource instanceof Parameters) {
				Parameters parameters = (Parameters) resource;

				for (Parameters.ParametersParameterComponent param : parameters.getParameter()) {
					String name = param.getName();
					if (param.hasValue()) {
						String value = param.getValue().primitiveValue();
						System.out.println("Param name: " + name + ", value: " + value);
					}
				}
			}
			ourLog.info(" incomingRequestPostProcessed finish ::: ");
		} catch (Exception e) {
			ourLog.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Hook(Pointcut.SERVER_OUTGOING_RESPONSE)
	public void incomingResponsePostProcessed(RequestDetails theRequestDetails, ResponseDetails theResponseDetails, HttpServletRequest theRequest) throws AuthenticationException {
		try { IBaseResource responseResource = theResponseDetails.getResponseResource();
			if (responseResource != null) {
				FhirContext ctx = theRequestDetails.getFhirContext();
				String result = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(theResponseDetails.getResponseResource());
				System.out.println(result);
			}
			ourLog.info(" incomingRequestPostProcessed finish ::: ");
		} catch (Exception e) {
			ourLog.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}


}
