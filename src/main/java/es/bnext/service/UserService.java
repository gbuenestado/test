package es.bnext.service;

import java.util.List;

import es.bnext.dto.in.ContactRequest;
import es.bnext.dto.in.UserRequest;
import es.bnext.dto.out.ContactResponse;
import es.bnext.dto.out.UserResponse;
import es.bnext.exception.ContactNotExist;
import es.bnext.exception.UserNotExist;

public interface UserService {

	/**
	 * Crea el usuario
	 * @param userRequest
	 * @return
	 */
	public UserResponse createUser(UserRequest userRequest);

	/**
	 * Crea los contactos contactRequestList del usuario userId
	 * @param userId
	 * @param contactRequestList
	 * @return
	 * @throws UserNotExist
	 */
	public List<ContactResponse> createUserContacts(Long userId, List<ContactRequest> contactRequestList) throws UserNotExist;

	/**
	 * Actualiza los contactos contactRequestList del usuario userId
	 * @param userId
	 * @param contactRequestList
	 * @return
	 * @throws UserNotExist
	 * @throws ContactNotExist 
	 */
	public List<ContactResponse> updateUserContacts(Long userId, List<ContactRequest> contactRequestList) throws UserNotExist, ContactNotExist;

	/**
	 * Devuelve todos los contactos del usuario userId
	 * @param userId
	 * @return
	 * @throws UserNotExist
	 */
	public List<ContactResponse> getAllContacts(Long userId) throws UserNotExist;

	/**
	 * Devuelve los contactos comunes de userIdA y userIdB
	 * @param userIdA
	 * @param userIdB
	 * @return
	 */
	public List<ContactResponse> getCommonContacts(Long userIdA, Long userIdB);

}
