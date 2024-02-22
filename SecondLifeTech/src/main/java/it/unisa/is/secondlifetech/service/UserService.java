package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.PaymentMethod;
import it.unisa.is.secondlifetech.entity.ShippingAddress;
import it.unisa.is.secondlifetech.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Interfaccia per il servizio di gestione degli utenti.
 */
public interface UserService {

// =============== CREATE ===============

	/**
	 * Crea un nuovo utente nel database.
	 *
	 * @param user l'oggetto User da creare
	 * @return l'oggetto User creato
	 */
	User createNewUser(User user);

	/**
	 * Crea e aggiunge un nuovo indirizzo di spedizione a un utente.
	 *
	 * @param user            l'utente a cui aggiungere l'indirizzo
	 * @param shippingAddress l'indirizzo da aggiungere
	 */
	ShippingAddress createNewShippingAddress(User user, ShippingAddress shippingAddress);

	/**
	 * Crea e aggiunge un nuovo metodo di pagamento a un utente.
	 *
	 * @param user  	    l'utente a cui aggiungere il metodo di pagamento
	 * @param paymentMethod il metodo di pagamento da aggiungere
	 */
	PaymentMethod createNewPaymentMethod(User user, PaymentMethod paymentMethod);



// =============== READ ===============

	/**
	 * Ottiene un utente dal database tramite l'ID.
	 *
	 * @param id l'ID dell'utente da cercare
	 * @return l'oggetto User corrispondente all'ID specificato, o null se non trovato
	 */
	User findUserById(UUID id);

	/**
	 * Ottiene un indirizzo di spedizione dal database tramite l'ID.
	 *
	 * @param id l'ID dell'indirizzo di spedizione da cercare
	 * @return l'oggetto ShippingAddress corrispondente all'ID specificato, o null se non trovato
	 */
	ShippingAddress findShippingAddressById(UUID id);

	/**
	 * Ottiene un metodo di pagamento dal database tramite l'ID.
	 *
	 * @param id l'ID del metodo di pagamento da cercare
	 * @return l'oggetto PaymentMethod corrispondente all'ID specificato, o null se non trovato
	 */
	PaymentMethod findPaymentMethodById(UUID id);

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
	 * Ottiene tutti gli utenti presenti nel database.
	 *
	 * @return una lista di tutti gli utenti presenti nel database
	 */
	List<User> findAllUsers();



// =============== UPDATE ===============

	/**
	 * Aggiorna le informazioni di un utente nel database.
	 *
	 * @param user l'oggetto User con le nuove informazioni da salvare
	 * @return l'oggetto User aggiornato
	 */
	User updateUser(User user);

	/**
	 * Aggiorna un indirizzo di spedizione di un utente.
	 *
	 * @param shippingAddress l'indirizzo da aggiornare
	 */
	ShippingAddress updateShippingAddress(ShippingAddress shippingAddress);

	/**
	 * Aggiorna un metodo di pagamento di un utente.
	 *
	 * @param paymentMethod il metodo di pagamento da aggiornare
	 */
	PaymentMethod updatePaymentMethod(PaymentMethod paymentMethod);



// =============== DELETE ===============

	/**
	 * Elimina un utente e tutti i suoi metodi di pagamento, indirizzi e il carrello dal database.
	 * Inoltre, se l'utente ha effettuato degli ordini, questi vengono dissociati dall'utente.
	 *
	 * @param user l'utente da eliminare
	 */
	@Transactional
	void deleteUser(User user);

	/**
	 * Rimuove un indirizzo di spedizione da un utente.
	 *
	 * @param shippingAddress l'indirizzo da rimuovere
	 */
	void deleteShippingAddress(ShippingAddress shippingAddress);

	/**
	 * Rimuove un metodo di pagamento da un utente.
	 *
	 * @param paymentMethod il metodo di pagamento da rimuovere
	 */
	void deletePaymentMethod(PaymentMethod paymentMethod);
}
