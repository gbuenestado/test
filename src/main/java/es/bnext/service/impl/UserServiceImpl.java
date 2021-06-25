package es.bnext.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.bnext.dto.in.ContactRequest;
import es.bnext.dto.in.UserRequest;
import es.bnext.dto.out.ContactResponse;
import es.bnext.dto.out.UserResponse;
import es.bnext.entity.Contact;
import es.bnext.entity.User;
import es.bnext.entity.UsersContact;
import es.bnext.entity.UsersContactPk;
import es.bnext.exception.ContactNotExist;
import es.bnext.exception.UserNotExist;
import es.bnext.repository.ContactRepository;
import es.bnext.repository.UserRepository;
import es.bnext.repository.impl.ContactRepositoryJPA;
import es.bnext.repository.impl.UserRepositoryJPA;
import es.bnext.service.UserService;

@Singleton
public class UserServiceImpl implements UserService {

	private final static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;
	private final ContactRepository contactRepository;

	public UserServiceImpl(UserRepositoryJPA userRepository, ContactRepositoryJPA contactRepository) { 
		this.userRepository = userRepository;
		this.contactRepository = contactRepository;
	}

	@Override
	public UserResponse createUser(UserRequest userRequest) {
		LOG.info("INIT - createUser: " + userRequest);
		UserResponse result = null;

		User user = userRepository.save(userRequest);
		if(user != null && user.getBnextUserId() != null) {
			result = new UserResponse();
			result.setId(user.getBnextUserId());
			result.setLastName(user.getLastName());
			result.setName(user.getName());
			result.setPhone(user.getPhone());
		}

		LOG.info("END - createUser: " + result);
		return result;
	}

	@Override
	public List<ContactResponse> createUserContacts(Long userId, List<ContactRequest> contactRequestList) throws UserNotExist {
		LOG.info("INIT - createUserContacts: " + userId + ", contatacts: " + contactRequestList);
		List<ContactResponse> result = new ArrayList<>();

		if(contactRequestList != null) {

			User user = userRepository.find(userId);
			if(user != null) {

				for(ContactRequest contactRequest : contactRequestList) {

					Contact contact = contactRepository.find(contactRequest.getPhone());
					if(contact == null) {
						if(contact == null) {
							contact = new Contact();
						}

						contact.setContactName(contactRequest.getContactName());
						contact.setPhone(contactRequest.getPhone());
						contactRepository.saveOrUpdate(contact);

						UsersContactPk usersContactPk = new UsersContactPk();
						usersContactPk.setBnextUserId(user.getBnextUserId());
						usersContactPk.setPhone(contact.getPhone());
						UsersContact usersContact = new UsersContact();
						usersContact.setId(usersContactPk);
						usersContact.setUser(user);
						usersContact.setContact(contact);
						user.getUsersContacts().add(usersContact);
						userRepository.update(user);
						
					} else {
						UsersContactPk usersContactPk = new UsersContactPk();
						usersContactPk.setBnextUserId(user.getBnextUserId());
						usersContactPk.setPhone(contact.getPhone());
						UsersContact usersContact = new UsersContact();
						usersContact.setId(usersContactPk);
						usersContact.setUser(user);
						usersContact.setContact(contact);
						
						if(!user.getUsersContacts().contains(usersContact)) {
							LOG.info("createUserContacts - update user with new contact");
							user.getUsersContacts().add(usersContact);
							userRepository.update(user);
						}
					}

					ContactResponse contactResponse = new ContactResponse();
					contactResponse.setContactName(contact.getContactName());
					contactResponse.setPhone(contact.getPhone());
					result.add(contactResponse);
				}

			} else {
				throw new UserNotExist("El usuario " + userId + " no existe");
			}
		}

		LOG.info("END - createUserContacts: " + result);
		return result;
	}

	@Override
	public List<ContactResponse> updateUserContacts(Long userId, List<ContactRequest> contactRequestList) throws UserNotExist, ContactNotExist {
		LOG.info("INIT - updateUserContacts: " + userId + ", contatacts: " + contactRequestList);
		List<ContactResponse> result = new ArrayList<>();

		if(contactRequestList != null) {

			User user = userRepository.find(userId);
			if(user != null) {

				for(ContactRequest contactRequest : contactRequestList) {

					Contact contact = contactRepository.find(contactRequest.getPhone());
					if(contact != null) {
						contact.setContactName(contactRequest.getContactName());
						contact.setPhone(contactRequest.getPhone());
						contactRepository.saveOrUpdate(contact);

						UsersContactPk usersContactPk = new UsersContactPk();
						usersContactPk.setBnextUserId(user.getBnextUserId());
						usersContactPk.setPhone(contact.getPhone());
						UsersContact usersContact = new UsersContact();
						usersContact.setId(usersContactPk);
						usersContact.setUser(user);
						usersContact.setContact(contact);
						if(!user.getUsersContacts().contains(usersContact)) {
							LOG.info("updateUserContacts - update user with new contact");
							user.getUsersContacts().add(usersContact);
							userRepository.update(user);
						}

					} else {
						throw new ContactNotExist("El contacto para el telefono " + contactRequest.getPhone() + " no existe");
					}

					ContactResponse contactResponse = new ContactResponse();
					contactResponse.setContactName(contact.getContactName());
					contactResponse.setPhone(contact.getPhone());
					result.add(contactResponse);
				}

			} else {
				throw new UserNotExist("El usuario " + userId + " no existe");
			}
		}

		LOG.info("END - updateUserContacts: " + result);
		return result;
	}

	@Override
	public List<ContactResponse> getAllContacts(Long userId) throws UserNotExist {
		LOG.info("INIT - getAllContacts: " + userId);
		List<ContactResponse> result = new ArrayList<>();

		User user = userRepository.find(userId);
		if(user != null) {

			if(user.getUsersContacts() != null) {

				for(UsersContact usersContact : user.getUsersContacts()) {
					Contact contact = usersContact.getContact();

					ContactResponse contactResponse = new ContactResponse();
					contactResponse.setContactName(contact.getContactName());
					contactResponse.setPhone(contact.getPhone());
					result.add(contactResponse);
				}
			}

		} else {
			throw new UserNotExist("El usuario " + userId + " no existe");
		}

		LOG.info("END - getAllContacts: " + result);
		return result;
	}

	@Override
	public List<ContactResponse> getCommonContacts(Long userIdA, Long userIdB) {
		LOG.info("INIT - getCommonContacts: " + userIdA + " " + userIdB);
		List<ContactResponse> result = new ArrayList<>();

		List<Contact> contactList = contactRepository.getCommonContacts(userIdA, userIdB);
		if(contactList != null) {

			for(Contact contact : contactList) {
				ContactResponse contactResponse = new ContactResponse();
				contactResponse.setContactName(contact.getContactName());
				contactResponse.setPhone(contact.getPhone());
				result.add(contactResponse);
			}
		}

		LOG.info("END - getCommonContacts: " + result);
		return result;
	}

}
