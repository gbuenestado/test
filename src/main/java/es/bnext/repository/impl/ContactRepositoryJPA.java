package es.bnext.repository.impl;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import es.bnext.entity.Contact;
import es.bnext.repository.ContactRepository;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.TransactionalAdvice;

@Singleton
public class ContactRepositoryJPA implements ContactRepository {

	private final EntityManager entityManager;

	public ContactRepositoryJPA(EntityManager entityManager) { 
		this.entityManager = entityManager;
	}

	@ReadOnly
	@Override
	public Contact find(String phone) {
		return entityManager.find(Contact.class, phone);
	}

	@TransactionalAdvice
	@Override
	public Contact saveOrUpdate(Contact contact) {
		entityManager.merge(contact);
		return contact;
	}

	@ReadOnly
	@Override
	public List<Contact> getCommonContacts(Long userIdA, Long userIdB) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT c ");
		sb.append(" FROM Contact c ");
		sb.append(" JOIN c.usersContacts uc ");
		sb.append(" WHERE uc.id.phone IN ( ");
		sb.append("   SELECT uc2.id.phone ");
		sb.append("   FROM UsersContact uc2 ");
		sb.append("   WHERE uc2.id.bnextUserId = :userIdB ");
		sb.append(" ) ");
		sb.append(" AND uc.id.bnextUserId = :userIdA ");

		TypedQuery<Contact> query = entityManager.createQuery(sb.toString(), Contact.class);
		query.setParameter("userIdA", userIdA);
		query.setParameter("userIdB", userIdB);

		return query.getResultList();
	}

}
