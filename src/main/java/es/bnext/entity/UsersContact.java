package es.bnext.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BNEXT_USER_CONTACT")
public class UsersContact {

	@EmbeddedId
	private UsersContactPk id;

	@ManyToOne
	@JoinColumn(name = "BNEXT_USER_ID", insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "PHONE", insertable = false, updatable = false)
	private Contact contact;

	public UsersContactPk getId() {
		return id;
	}

	public void setId(UsersContactPk id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		UsersContact other = (UsersContact) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
