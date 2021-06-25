package es.bnext.dto.in;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "User", description = "User Request")
@Introspected
public class UserRequest {

	@Schema(description="Name of user", maximum = "255")
	@NonNull
	@NotBlank
	private String name;

	@Schema(description="Last name of user", maximum = "255")
	@NonNull
	@NotBlank
	private String lastName;

	@Schema(description="Phone of user", maximum = "15")
	@JsonProperty("Phone")
	@NonNull
	@NotBlank
	private String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
		sb.append(" \"name\": \"").append(getName()).append("\"");
		sb.append(", \"lastName\": \"").append(getLastName()).append("\"");
		sb.append(", \"phone\": \"").append(getPhone()).append("\"");
		sb.append("}");
		return sb.toString();
	}

}
