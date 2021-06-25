package es.bnext.facade;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class PhoneRequest {
	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
