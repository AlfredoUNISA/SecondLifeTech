package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.repository.UserRepository;
import it.unisa.is.secondlifetech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Salva un utente nel database.
	 *
	 * @param user l'oggetto User da salvare
	 * @return l'oggetto User salvato
	 */
	@Override
	public User saveUser(User user) {
		// TODO: implementare la logica di encoding della password
		return userRepository.save(user);
	}

	/**
	 * Ottiene un utente dal database tramite l'ID.
	 *
	 * @param id l'ID dell'utente da cercare
	 * @return l'oggetto User corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public User findUserById(UUID id) {
		return userRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene un utente dal database tramite l'email.
	 *
	 * @param email l'email dell'utente da cercare
	 * @return l'oggetto User corrispondente all'email specificato, o null se non trovato
	 */
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	/**
	 * Ottiene tutti gli utenti con un determinato ruolo.
	 * @param role il ruolo da cercare
	 * @return una lista di tutti gli utenti con il ruolo specificato
	 */
	@Override
	public List<User> findUsersByRole(String role) {
		return userRepository.findByRole(role);
	}

	/**
	 * Ottiene tutti gli utenti presenti nel database.
	 *
	 * @return una lista di tutti gli utenti presenti nel database
	 */
	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * Aggiorna le informazioni di un utente nel database.
	 *
	 * @param id   l'ID dell'utente da aggiornare
	 * @param user l'oggetto User con le nuove informazioni da salvare
	 * @return l'oggetto User aggiornato
	 */
	@Override
	public User updateUser(UUID id, User user) {
		user.setId(id);
		return userRepository.save(user);
	}

	/**
	 * Elimina un utente dal database tramite l'ID.
	 *
	 * @param id l'ID dell'utente da eliminare
	 */
	@Override
	public void deleteUser(UUID id) {
		userRepository.deleteById(id);
	}
}
