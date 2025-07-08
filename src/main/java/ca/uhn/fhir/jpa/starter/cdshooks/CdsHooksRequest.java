package ca.uhn.fhir.jpa.starter.cdshooks;

import ca.uhn.fhir.rest.api.server.cdshooks.CdsServiceRequestJson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hl7.fhir.r4.model.Address;
import org.junit.Test;

@JsonIgnoreProperties({"extension"})
public class CdsHooksRequest extends CdsServiceRequestJson {
//	@Test
//	public void test123(){
//		Address address = new Address();
//		Address.AddressUse.fromCode("home");
//		address.setUse();
//	}

}
