package es.bnext.repository;

import es.bnext.dto.in.UserRequest;
import es.bnext.entity.User;

public interface UserRepository {

	/**
	 * Crea el usaurio
	 * @param userRequest
	 * @return
	 */
	public User save(UserRequest userRequest);

	/**
	 * Actualiza el usuario
	 * @param user
	 * @return
	 */
	public User update(User user);

	/**
	 * Busca el usuario
	 * @param userId
	 * @return
	 */
	public User find(Long userId);

}
