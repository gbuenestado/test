package es.bnext.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsersContactPk implements Serializable {

	private static final long serialVersionUID = -5220898668983939628L;

	@Column(name = "BNEXT_USER_ID")
	private Long bnextUserId; 

	@Column(name = "PHONE")
	private String phone;

	public Long getBnextUserId() {
		return bnextUserId;
	}

	public void setBnextUserId(Long bnextUserId) {
		this.bnextUserId = bnextUserId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bnextUserId == null) ? 0 : bnextUserId.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsersContactPk other = (UsersContactPk) obj;
		if (bnextUserId == null) {
			if (other.bnextUserId != null)
				return false;
		} else if (!bnextUserId.equals(other.bnextUserId))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}

}
