package es.bnext.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

@ConfigurationProperties(NeutrinoapiConfig.PREFIX)
@Requires(property = NeutrinoapiConfig.PREFIX)
public class NeutrinoapiConfig {
	
	protected final static String PREFIX = "neutrinoapi";
	
	private String apikey;
	private String userid;
	private String url;

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
