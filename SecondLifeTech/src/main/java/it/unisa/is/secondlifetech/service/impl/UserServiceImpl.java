package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.dto.ProductFilters;
import it.unisa.is.secondlifetech.dto.UserFilters;
import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.exception.EmailAlreadyInUseException;
import it.unisa.is.secondlifetech.exception.ErrorInFieldException;
import it.unisa.is.secondlifetech.exception.MissingRequiredFieldException;
import it.unisa.is.secondlifetech.repository.PaymentMethodRepository;
import it.unisa.is.secondlifetech.repository.ShippingAddressRepository;
import it.unisa.is.secondlifetech.repository.UserRepository;
import it.unisa.is.secondlifetech.service.CartService;
import it.unisa.is.secondlifetech.service.OrderService;
import it.unisa.is.secondlifetech.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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
	public User createNewUser(User user) throws EmailAlreadyInUseException, MissingRequiredFieldException, ErrorInFieldException {
		checkUser(user);
		checkUserValues(user);

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



	/**
	 * Aggiunge un indirizzo di spedizione a un utente.
	 *
	 * @param user            l'utente a cui aggiungere l'indirizzo
	 * @param shippingAddress l'indirizzo da aggiungere
	 */
	@Override
	public ShippingAddress createNewShippingAddress(User user, ShippingAddress shippingAddress) throws MissingRequiredFieldException, ErrorInFieldException {
		checkShippingAddress(shippingAddress);
		checkShippingAddressValues(shippingAddress);

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
	public PaymentMethod createNewPaymentMethod(User user, PaymentMethod paymentMethod) throws MissingRequiredFieldException, ErrorInFieldException {
		checkPaymentMethod(paymentMethod);
		checkPaymentMethodValues(paymentMethod);

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

	/**
	 * Ottiene tutti gli utenti dal database con un sistema a Paginazione.
	 *
	 * @return una pagina di oggetti User
	 */
	@Override
	public Page<User> findAllUsersPaginated(Pageable pageable) {
		return userRepository.findAll(pageable);
	}


	/**
	 * Ottiene tutti gli utenti dal database con un sistema a Paginazione e con filtri.
	 *
	 * @return una pagina di oggetti User
	 */
	@Override
	public Page<User> findAllUsersPaginatedWithFilters(UserFilters filters, Pageable pageable) {
		List<User> allUsers = userRepository.findAll();
		List<User> filteredUsers = doFilter(filters, allUsers);

		// Paginazione
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;

		List<User> paginatedList;

		if (filteredUsers.size() < startItem) {
			paginatedList = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, filteredUsers.size());
			paginatedList = filteredUsers.subList(startItem, toIndex);
		}

		return new PageImpl<>(
			paginatedList,
			PageRequest.of(currentPage, pageSize),
			filteredUsers.size()
		);
	}

	/**
	 * Esegue il filtraggio dei modelli di prodotto.
	 */
	private List<User> doFilter(UserFilters filters, List<User> allUsers) {
		List<User> filteredUsers = new ArrayList<>();

		if (filters.getEmail() != null && !filters.getEmail().isEmpty()) {
			// Filtra per email
			User foundUser = allUsers.stream()
				.filter(user -> user.getEmail().equalsIgnoreCase(filters.getEmail()))
				.findFirst()
				.orElse(null);

			if (filters.getRole() != null
				&& !filters.getRole().isEmpty()
				&& foundUser != null
				&& foundUser.getRole().equalsIgnoreCase(filters.getRole())
			   )
				filteredUsers.add(foundUser);

			else
				filteredUsers.add(foundUser);
		}
		else if (filters.getRole() != null && !filters.getRole().isEmpty()) {
			// Filtra per ruolo
			filteredUsers.addAll(allUsers.stream()
				.filter(user -> user.getRole().equalsIgnoreCase(filters.getRole()))
				.toList()
			);
		}
		else {
			// Nessun filtro
			return allUsers;
		}

		return filteredUsers;
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

		String oldPassword = userRepository.findById(user.getId()).get().getPassword();
		String newPassword = passwordEncoder.encode(user.getPassword());
		if (newPassword.equalsIgnoreCase(oldPassword))
			user.setPassword(oldPassword);
		else
			user.setPassword(newPassword);

		return userRepository.save(user);
	}

	/**
	 * Aggiorna un indirizzo di spedizione di un utente.
	 *
	 * @param shippingAddress l'indirizzo da aggiornare
	 */
	@Override
	public ShippingAddress updateShippingAddress(ShippingAddress shippingAddress) throws MissingRequiredFieldException, ErrorInFieldException {
		if (shippingAddress.getId() == null)
			throw new IllegalArgumentException("ID dell'indirizzo non specificato nella modifica");

		checkShippingAddressValues(shippingAddress);

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
		if (user == null || user.getId() == null)
			return;

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


	// ================================================================================================================
	// =============== OTHER ===========================================================================================
	// ================================================================================================================

	private static void checkUser(User user) {
		if (user == null)
			throw new IllegalArgumentException("L'utente specificato non è valido");

		if (user.getId() != null)
			throw new IllegalArgumentException("Usare la funzione di aggiornamento per modificare un utente esistente");
	}

	private static void checkUserValues(User user) throws MissingRequiredFieldException, ErrorInFieldException {
		if (user.getFirstName().isEmpty()
			||user.getLastName().isEmpty()
			||user.getEmail().isEmpty()
			||user.getPassword().isEmpty()
			||user.getRole().isEmpty()) {
			throw new MissingRequiredFieldException();
		}

		if (user.getFirstName().length() < UserFilters.MIN_STRING_LENGTH || user.getFirstName().length() > UserFilters.MAX_STRING_LENGTH)
			throw new ErrorInFieldException("Il nome deve essere lungo tra i " + ProductFilters.MIN_STRING_LENGTH + " e i " + ProductFilters.MAX_STRING_LENGTH + " caratteri");

		if (user.getLastName().length() < UserFilters.MIN_STRING_LENGTH || user.getLastName().length() > UserFilters.MAX_STRING_LENGTH)
			throw new ErrorInFieldException("Il cognome deve essere lungo tra i " + ProductFilters.MIN_STRING_LENGTH + " e i " + ProductFilters.MAX_STRING_LENGTH + " caratteri");

		if (!user.getPhoneNumber().isEmpty() && user.getPhoneNumber().length() < 6 || user.getPhoneNumber().length() > 15)
			throw new ErrorInFieldException("Il numero di telefono deve essere lungo tra i 6 e i 15 caratteri");

		// regex per l'email
		if (!user.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
			throw new ErrorInFieldException("Email non valida");

		if (user.getPassword().length() < UserFilters.MIN_STRING_LENGTH || user.getPassword().length() > UserFilters.MAX_STRING_LENGTH)
			throw new ErrorInFieldException("La password deve essere lunga tra i " + ProductFilters.MIN_STRING_LENGTH + " e i " + ProductFilters.MAX_STRING_LENGTH + " caratteri");

		if (!List.of(UserRole.ALL_ROLES).contains(UserRole.getRoleName(user.getRole())))
			throw new ErrorInFieldException("Il ruolo specificato non è valido");
	}

	private void checkShippingAddress(ShippingAddress shippingAddress) {
		if (shippingAddress == null)
			throw new IllegalArgumentException("L'indirizzo specificato non è valido");

		if (shippingAddress.getId() != null)
			throw new IllegalArgumentException("Usare la funzione di aggiornamento per modificare un indirizzo esistente");
	}

	private void checkShippingAddressValues(ShippingAddress shippingAddress) throws MissingRequiredFieldException, ErrorInFieldException {
		if (shippingAddress.getStreet().isEmpty()
			|| shippingAddress.getCity().isEmpty()
			|| shippingAddress.getZipCode().isEmpty()
			|| shippingAddress.getState().isEmpty()
			|| shippingAddress.getCountry().isEmpty()) {
			throw new MissingRequiredFieldException();
		}

		if (shippingAddress.getStreet().length() < UserFilters.MIN_STRING_LENGTH || shippingAddress.getStreet().length() > UserFilters.MAX_STRING_LENGTH)
			throw new ErrorInFieldException("La via deve essere lunga tra i " + ProductFilters.MIN_STRING_LENGTH + " e i " + ProductFilters.MAX_STRING_LENGTH + " caratteri");

		if (shippingAddress.getCity().length() < UserFilters.MIN_STRING_LENGTH || shippingAddress.getCity().length() > UserFilters.MAX_STRING_LENGTH)
			throw new ErrorInFieldException("La città deve essere lunga tra i " + ProductFilters.MIN_STRING_LENGTH + " e i " + ProductFilters.MAX_STRING_LENGTH + " caratteri");

		if (shippingAddress.getZipCode().length() < 4 || shippingAddress.getZipCode().length() > 10)
			throw new ErrorInFieldException("Il codice postale deve essere lungo tra i 4 e i 10 caratteri");

		if (shippingAddress.getCountry().length() < UserFilters.MIN_STRING_LENGTH || shippingAddress.getCountry().length() > UserFilters.MAX_STRING_LENGTH)
			throw new ErrorInFieldException("La provincia/paese deve essere lungo tra i " + ProductFilters.MIN_STRING_LENGTH + " e i " + ProductFilters.MAX_STRING_LENGTH + " caratteri");

		if (shippingAddress.getState().length() < UserFilters.MIN_STRING_LENGTH || shippingAddress.getState().length() > UserFilters.MAX_STRING_LENGTH)
			throw new ErrorInFieldException("La nazione deve essere lunga tra i " + ProductFilters.MIN_STRING_LENGTH + " e i " + ProductFilters.MAX_STRING_LENGTH + " caratteri");
	}

	private void checkPaymentMethod(PaymentMethod paymentMethod) {
		if (paymentMethod == null)
			throw new IllegalArgumentException("Il metodo di pagamento specificato non è valido");

		if (paymentMethod.getId() != null)
			throw new IllegalArgumentException("Usare la funzione di aggiornamento per modificare un metodo di pagamento esistente");
	}

	private void checkPaymentMethodValues(PaymentMethod paymentMethod) throws MissingRequiredFieldException, ErrorInFieldException {
		if (paymentMethod.getCardNumber().isEmpty()
			|| paymentMethod.getCardHolderName().isEmpty()
			|| paymentMethod.getExpirationDate().isEmpty()
			|| paymentMethod.getCvv().isEmpty()) {
			throw new MissingRequiredFieldException();
		}

		if (paymentMethod.getCardNumber().length() < 16 || paymentMethod.getCardNumber().length() > 19)
			throw new ErrorInFieldException("Il numero della carta deve essere lungo tra i 16 e i 19 caratteri");

		if (paymentMethod.getCardHolderName().length() < UserFilters.MIN_STRING_LENGTH || paymentMethod.getCardHolderName().length() > UserFilters.MAX_STRING_LENGTH)
			throw new ErrorInFieldException("Il titolare della carta deve essere lungo tra i " + ProductFilters.MIN_STRING_LENGTH + " e i " + ProductFilters.MAX_STRING_LENGTH + " caratteri");

		if (paymentMethod.getExpirationDate().length() != 5)
			throw new ErrorInFieldException("La data di scadenza deve essere lunga 5 caratteri");

		if (paymentMethod.getCvv().length() != 3)
			throw new ErrorInFieldException("Il CVC deve essere lungo 3 caratteri");
	}

	// TODO: implementare gli errori su thymeleaf
}
