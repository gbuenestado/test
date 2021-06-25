package es.bnext.repository;

import java.util.List;

import es.bnext.entity.Contact;

public interface ContactRepository {

	/**
	 * Devuelve el contacto
	 * @param phone
	 * @return
	 */
	public Contact find(String phone);

	/**
	 * Crea o actualiar el contacto
	 * @param contact
	 * @return
	 */
	public Contact saveOrUpdate(Contact contact);

	/**
	 * Devuelve los contactos comunes de userIdA y userIdB
	 * @param userIdA
	 * @param userIdB
	 * @return
	 */
	public List<Contact> getCommonContacts(Long userIdA, Long userIdB);

}
