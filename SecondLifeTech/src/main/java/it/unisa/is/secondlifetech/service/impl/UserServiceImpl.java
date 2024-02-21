package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.repository.UserRepository;
import it.unisa.is.secondlifetech.service.CartService;
import it.unisa.is.secondlifetech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementazione del servizio per la gestione degli utenti.
 */
@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final CartService cartService;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, CartService cartService) {
		this.userRepository = userRepository;
		this.cartService = cartService;
	}

	/**
	 * Salva un nuovo utente nel database.
	 *
	 * @param user l'oggetto User da salvare
	 * @return l'oggetto User salvato
	 */
	@Override
	public User createNewUser(User user) {
		// TODO: implementare la logica di encoding della password

		if (user.getId() != null)
			throw new RuntimeException("Usare la funzione di aggiornamento per modificare un utente esistente");

		if (user.getRole().equals(UserRole.CLIENTE)) {
			Cart cart = new Cart();
			cartService.createNewCart(cart);

			user.setCart(cart);
			return userRepository.save(user);
		}

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
	 *
	 * @param role il ruolo da cercare
	 * @return una lista di tutti gli utenti con il ruolo specificato
	 */
	@Override
	public List<User> findUsersByRole(String role) {
		return userRepository.findByRole(role);
	}

	/**
	 * Ottiene l'utente a cui è associato un carrello.
	 *
	 * @param cartId l'ID del carrello di cui cercare l'utente
	 * @return l'oggetto User corrispondente al carrello specificato, o null se non trovato
	 */
	@Override
	public User findUserByCartId(UUID cartId) {
		return userRepository.findByCartId(cartId).orElse(null);
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
