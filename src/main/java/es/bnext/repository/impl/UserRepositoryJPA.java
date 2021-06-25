package es.bnext.repository.impl;

import javax.inject.Singleton;
import javax.persistence.EntityManager;

import es.bnext.dto.in.UserRequest;
import es.bnext.entity.User;
import es.bnext.repository.UserRepository;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.TransactionalAdvice;

@Singleton
public class UserRepositoryJPA implements UserRepository {

	private final EntityManager entityManager;

	public UserRepositoryJPA(EntityManager entityManager) { 
		this.entityManager = entityManager;
	}

	@TransactionalAdvice
	@Override
	public User save(UserRequest userRequest) {
		User user = new User();
		user.setName(userRequest.getName());
		user.setLastName(userRequest.getLastName());
		user.setPhone(userRequest.getPhone());

		entityManager.persist(user);
		return user;
	}

	@TransactionalAdvice
	@Override
	public User update(User user) {
		entityManager.merge(user);
		return user;
	}

	@ReadOnly
	@Override
	public User find(Long userId) {
		return entityManager.find(User.class, userId);
	}

}