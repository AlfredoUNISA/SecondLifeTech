package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.exception.EmailAlreadyInUseException;
import it.unisa.is.secondlifetech.exception.MissingRequiredField;
import it.unisa.is.secondlifetech.repository.PaymentMethodRepository;
import it.unisa.is.secondlifetech.repository.ShippingAddressRepository;
import it.unisa.is.secondlifetech.repository.UserRepository;
import it.unisa.is.secondlifetech.service.CartService;
import it.unisa.is.secondlifetech.service.OrderService;
import it.unisa.is.secondlifetech.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementazione del servizio per la gestione degli utenti.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final CartService cartService;
	private final ShippingAddressRepository shippingAddressRepository;
	private final PaymentMethodRepository paymentMethodRepository;
	private final OrderService orderService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, CartService cartService, ShippingAddressRepository shippingAddressRepository, PaymentMethodRepository paymentMethodRepository, OrderService orderService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.cartService = cartService;
		this.shippingAddressRepository = shippingAddressRepository;
		this.paymentMethodRepository = paymentMethodRepository;
		this.orderService = orderService;
		this.passwordEncoder = passwordEncoder;
	}



	// ================================================================================================================
	// =============== CREATE ==========================================================================================
	// ================================================================================================================

	/**
	 * Salva un nuovo utente nel database.
	 *
	 * @param user l'oggetto User da salvare
	 * @return l'oggetto User salvato
	 */
	@Override
	public User createNewUser(User user) throws EmailAlreadyInUseException, MissingRequiredField {
		if (user.getId() != null)
			throw new IllegalArgumentException("Usare la funzione di aggiornamento per modificare un utente esistente");

		checkForUserMissingFields(user);

		if (userRepository.existsByEmail(user.getEmail()))
			throw new EmailAlreadyInUseException(user.getEmail());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if (user.getRole().equals(UserRole.CLIENTE)) {
			Cart cart = new Cart();
			cartService.createNewCart(cart);

			user.setCart(cart);
			return userRepository.save(user);
		}

		return userRepository.save(user);
	}

	private static void checkForUserMissingFields(User user) throws MissingRequiredField {
		if (user.getFirstName().isEmpty()
				||user.getLastName().isEmpty()
				||user.getEmail().isEmpty()
				||user.getPassword().isEmpty()
				||user.getRole().isEmpty()) {
			throw new MissingRequiredField();
		}
	}

	/**
	 * Aggiunge un indirizzo di spedizione a un utente.
	 *
	 * @param user            l'utente a cui aggiungere l'indirizzo
	 * @param shippingAddress l'indirizzo da aggiungere
	 */
	@Override
	public ShippingAddress createNewShippingAddress(User user, ShippingAddress shippingAddress) {
		if (shippingAddress.getId() != null)
			throw new IllegalArgumentException("Usare la funzione di aggiornamento per modificare un indirizzo esistente");

		user.addShippingAddress(shippingAddress);

		ShippingAddress toReturn = shippingAddressRepository.save(shippingAddress);
		userRepository.save(user);

		return toReturn;
	}

	/**
	 * Aggiunge un metodo di pagamento a un utente.
	 *
	 * @param user  	    l'utente a cui aggiungere il metodo di pagamento
	 * @param paymentMethod il metodo di pagamento da aggiungere
	 */
	@Override
	public PaymentMethod createNewPaymentMethod(User user, PaymentMethod paymentMethod) {
		if (paymentMethod.getId() != null)
			throw new IllegalArgumentException("Usare la funzione di aggiornamento per modificare un metodo di pagamento esistente");

		user.addPaymentMethod(paymentMethod);

		PaymentMethod toReturn = paymentMethodRepository.save(paymentMethod);
		userRepository.save(user);

		return toReturn;
	}



	// ================================================================================================================
	// =============== READ ============================================================================================
	// ================================================================================================================

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
	 * Ottiene un indirizzo di spedizione dal database tramite l'ID.
	 *
	 * @param id l'ID dell'indirizzo di spedizione da cercare
	 * @return l'oggetto ShippingAddress corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public ShippingAddress findShippingAddressById(UUID id) {
		return shippingAddressRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene un metodo di pagamento dal database tramite l'ID.
	 *
	 * @param id l'ID del metodo di pagamento da cercare
	 * @return l'oggetto PaymentMethod corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public PaymentMethod findPaymentMethodById(UUID id) {
		return paymentMethodRepository.findById(id).orElse(null);
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
	 * Ottiene tutti gli utenti presenti nel database.
	 *
	 * @return una lista di tutti gli utenti presenti nel database
	 */
	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Page<User> findAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}



	// ================================================================================================================
	// =============== UPDATE ==========================================================================================
	// ================================================================================================================

	/**
	 * Aggiorna le informazioni di un utente nel database.
	 *
	 * @param user l'oggetto User con le nuove informazioni da salvare
	 * @return l'oggetto User aggiornato
	 */
	@Override
	public User updateUser(User user) {
		if (user.getId() == null)
			throw new IllegalArgumentException("ID dell'utente non specificato nella modifica");

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return userRepository.save(user);
	}

	/**
	 * Aggiorna un indirizzo di spedizione di un utente.
	 *
	 * @param shippingAddress l'indirizzo da aggiornare
	 */
	@Override
	public ShippingAddress updateShippingAddress(ShippingAddress shippingAddress) {
		if (shippingAddress.getId() == null)
			throw new IllegalArgumentException("ID dell'indirizzo non specificato nella modifica");
		return shippingAddressRepository.save(shippingAddress);
	}

	/**
	 * Aggiorna un metodo di pagamento di un utente.
	 *
	 * @param paymentMethod il metodo di pagamento da aggiornare
	 */
	@Override
	public PaymentMethod updatePaymentMethod(PaymentMethod paymentMethod) {
		if (paymentMethod.getId() == null)
			throw new IllegalArgumentException("ID del metodo di pagamento non specificato nella modifica");
		return paymentMethodRepository.save(paymentMethod);
	}



	// ================================================================================================================
	// =============== DELETE ==========================================================================================
	// ================================================================================================================

	/**
	 * Elimina un utente e tutti i suoi metodi di pagamento, indirizzi e il carrello dal database.
	 * Inoltre, se l'utente ha effettuato degli ordini, questi vengono dissociati dall'utente.
	 *
	 * @param user l'utente da eliminare
	 */
	@Override
	@Transactional
	public void deleteUser(User user) {
		Cart cart = user.getCart();
		List<PaymentMethod> paymentMethods = user.getPaymentMethods();
		List<ShippingAddress> shippingAddresses = user.getShippingAddresses();
		List<OrderPlaced> orders = user.getOrders();

		if (cart != null)
			cartService.deleteCart(cart);

		if (!paymentMethods.isEmpty())
			paymentMethodRepository.deleteAll(paymentMethods);

		if (!shippingAddresses.isEmpty())
			shippingAddressRepository.deleteAll(shippingAddresses);

		if (!orders.isEmpty()) {
			for (OrderPlaced order : orders) {
				order.setUser(null);
				orderService.updateOrder(order);
			}
		}

		userRepository.delete(user);
	}

	/**
	 * Rimuove un indirizzo di spedizione da un utente.
	 *
	 * @param shippingAddress l'indirizzo da rimuovere
	 */
	@Override
	public void deleteShippingAddress(ShippingAddress shippingAddress) {
		User user = shippingAddress.getUser();
		user.removeShippingAddress(shippingAddress);

		shippingAddressRepository.delete(shippingAddress);
		userRepository.save(user);
	}

	/**
	 * Rimuove un metodo di pagamento da un utente.
	 *
	 * @param paymentMethod il metodo di pagamento da rimuovere
	 */
	@Override
	public void deletePaymentMethod(PaymentMethod paymentMethod) {
		User user = paymentMethod.getUser();
		user.removePaymentMethod(paymentMethod);

		paymentMethodRepository.delete(paymentMethod);
		userRepository.save(user);
	}
}
