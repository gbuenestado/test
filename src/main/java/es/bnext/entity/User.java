package es.bnext.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BNEXT_USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BNEXT_USER_ID", nullable = false, unique = true)
	private Long bnextUserId;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "PHONE", nullable = false)
	private String phone;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UsersContact> usersContacts;

	public Long getBnextUserId() {
		return bnextUserId;
	}

	public void setBnextUserId(Long bnextUserId) {
		this.bnextUserId = bnextUserId;
	}

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

	public Set<UsersContact> getUsersContacts() {
		return usersContacts;
	}

	public void setUsersContacts(Set<UsersContact> usersContacts) {
		this.usersContacts = usersContacts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bnextUserId == null) ? 0 : bnextUserId.hashCode());
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
		User other = (User) obj;
		if (bnextUserId == null) {
			if (other.bnextUserId != null)
				return false;
		} else if (!bnextUserId.equals(other.bnextUserId))
			return false;
		return true;
	}

}
