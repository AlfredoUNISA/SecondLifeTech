package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.PaymentMethod;
import it.unisa.is.secondlifetech.entity.ShippingAddress;
import it.unisa.is.secondlifetech.entity.User;

import java.util.List;
import java.util.UUID;

/**
 * Interfaccia per il servizio di gestione degli utenti.
 */
public interface UserService {

	void addShippingAddress(User user, ShippingAddress shippingAddress);
	void removeShippingAddress(User user, ShippingAddress shippingAddress);
	void updateShippingAddress(User user, ShippingAddress shippingAddress);

	void addPaymentMethod(User user, PaymentMethod paymentMethod);
	void removePaymentMethod(User user, PaymentMethod paymentMethod);
	void updatePaymentMethod(User user, PaymentMethod paymentMethod);

	/**
	 * Crea un nuovo utente nel database.
	 *
	 * @param user l'oggetto User da creare
	 * @return l'oggetto User creato
	 */
	User createNewUser(User user);

	/**
	 * Ottiene un utente dal database tramite l'ID.
	 *
	 * @param id l'ID dell'utente da cercare
	 * @return l'oggetto User corrispondente all'ID specificato, o null se non trovato
	 */
	User findUserById(UUID id);

	/**
	 * Ottiene un utente dal database tramite l'email.
	 *
	 * @param email l'email dell'utente da cercare
	 * @return l'oggetto User corrispondente all'email specificato, o null se non trovato
	 */
	User findUserByEmail(String email);

	/**
	 * Ottiene tutti gli utenti con un determinato ruolo.
	 * @param role il ruolo da cercare
	 * @return una lista di tutti gli utenti con il ruolo specificato
	 */
	List<User> findUsersByRole(String role);

	/**
	 * Ottiene l'utente a cui è associato un carrello.
	 *
	 * @param cartId l'ID del carrello di cui cercare l'utente
	 * @return l'oggetto User corrispondente al carrello specificato, o null se non trovato
	 */
	User findUserByCartId(UUID cartId);

	/**
	 * Ottiene tutti gli utenti presenti nel database.
	 *
	 * @return una lista di tutti gli utenti presenti nel database
	 */
	List<User> findAllUsers();

	/**
	 * Aggiorna le informazioni di un utente nel database.
	 *
	 * @param id   l'ID dell'utente da aggiornare
	 * @param user l'oggetto User con le nuove informazioni da salvare
	 * @return l'oggetto User aggiornato
	 */
	User updateUser(UUID id, User user);

	/**
	 * Elimina un utente dal database tramite l'ID.
	 *
	 * @param id l'ID dell'utente da eliminare
	 */
	void deleteUser(UUID id);

}
