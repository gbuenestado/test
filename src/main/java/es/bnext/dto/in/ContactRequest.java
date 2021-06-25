package es.bnext.dto.in;

import javax.validation.constraints.NotBlank;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Contact", description = "Contact Request")
@Introspected
public class ContactRequest {

	@Schema(description="Name of contact", maximum = "255") 
	@NonNull
	@NotBlank
	private String contactName;

	@Schema(description="Phone of contact", maximum = "15")
	@NonNull
	@NotBlank
	private String phone;

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		sb.append(" \"contactName\": \"").append(getContactName()).append("\"");
		sb.append(", \"phone\": \"").append(getPhone()).append("\"");
		sb.append("}");
		return sb.toString();
	}
}
