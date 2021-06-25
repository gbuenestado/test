package es.bnext.facade;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bnext.config.NeutrinoapiConfig;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;

@Singleton
public class PhoneValidatorFacade {

	private final static Logger LOG = LoggerFactory.getLogger(PhoneValidatorFacade.class);

	private final RxHttpClient httpClient;
	private final NeutrinoapiConfig neutrinoapiConfig;

	public PhoneValidatorFacade(@Client RxHttpClient httpClient, NeutrinoapiConfig neutrinoapiConfig) {  
		this.httpClient = httpClient;
		this.neutrinoapiConfig = neutrinoapiConfig;
	}

	public boolean validate(String numberPhone) {
		LOG.info("Validating phone: " + numberPhone);
		boolean result = false;

		try {
			PhoneRequest phone = new PhoneRequest();
			phone.setNumber(numberPhone);

			HttpRequest<PhoneRequest> req = HttpRequest.POST(neutrinoapiConfig.getUrl(), phone)
					.header("api-key", neutrinoapiConfig.getApikey())
					.header("user-id", neutrinoapiConfig.getUserid());
			HttpResponse<PhoneResponse> resp = httpClient.toBlocking().exchange(req, PhoneResponse.class);
			if(resp != null && resp.body() != null && resp.body().isValid()) {
				result = true;
			}

		} catch (Exception e) {
			LOG.error("ERROR: " + e.getMessage(), e);
		}
		
		return result;
	}

}
