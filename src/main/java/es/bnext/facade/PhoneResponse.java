package es.bnext.facade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneResponse {

	private boolean valid;

	private String type;

	@JsonProperty("international-calling-code")
	private Integer internationalCallingCode;

	@JsonProperty("international-number")
	private String internationalNumber;

	@JsonProperty("local-number")
	private String localNumber;

	private String location;

	private String country;

	@JsonProperty("country-code")
	private String countryCode;

	@JsonProperty("country-code3")
	private String countryCode3;

	@JsonProperty("currency-code")
	private String currencyCode;

	@JsonProperty("is-mobile")
	private boolean isMobile;

	@JsonProperty("prefix-network")	
	private String prefixNetwork;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getInternationalCallingCode() {
		return internationalCallingCode;
	}

	public void setInternationalCallingCode(Integer internationalCallingCode) {
		this.internationalCallingCode = internationalCallingCode;
	}

	public String getInternationalNumber() {
		return internationalNumber;
	}

	public void setInternationalNumber(String internationalNumber) {
		this.internationalNumber = internationalNumber;
	}

	public String getLocalNumber() {
		return localNumber;
	}

	public void setLocalNumber(String localNumber) {
		this.localNumber = localNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryCode3() {
		return countryCode3;
	}

	public void setCountryCode3(String countryCode3) {
		this.countryCode3 = countryCode3;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public boolean isMobile() {
		return isMobile;
	}

	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}

	public String getPrefixNetwork() {
		return prefixNetwork;
	}

	public void setPrefixNetwork(String prefixNetwork) {
		this.prefixNetwork = prefixNetwork;
	}

}
