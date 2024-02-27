package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.exception.*;
import it.unisa.is.secondlifetech.repository.CartItemRepository;
import it.unisa.is.secondlifetech.repository.CartRepository;
import it.unisa.is.secondlifetech.service.CartService;
import it.unisa.is.secondlifetech.service.OrderService;
import it.unisa.is.secondlifetech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final OrderService orderService;
	private final ProductService productService;

	@Autowired
	public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, OrderService orderService, ProductService productService) {
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.orderService = orderService;
		this.productService = productService;
	}



	// ================================================================================================================
	// =============== CREATE ==========================================================================================
	// ================================================================================================================

	/**
	 * Salva un carrello nel database.
	 *
	 * @param cart l'oggetto Cart da salvare
	 * @return l'oggetto Cart salvato
	 */
	@Override
	public Cart createNewCart(Cart cart) {
		if (cart != null)
			return cartRepository.save(cart);
		return null;
	}

	/**
	 * Aggiunge un nuovo prodotto al carrello.<br/><br/>
	 * <i>Alias: createNewCartItem<i/>
	 *
	 * @param cart               il carrello dell'utente in cui aggiungere il prodotto
	 * @param productVariationId l'ID della variante di prodotto da aggiungere
	 * @param quantity           la quantità del prodotto da aggiungere
	 * @throws NoDevicesAvailableException  se la quantità richiesta non è disponibile nell'inventario
	 * @throws IllegalArgumentException se la variante di prodotto specificata non esiste
	 */
	@Override
	@Transactional
	public void addToCart(Cart cart, UUID productVariationId, int quantity) throws NoDevicesAvailableException, IllegalArgumentException {
		if (quantity < 1)
			throw new IllegalArgumentException("La quantità deve essere maggiore di 1");

		// Se il prodotto è già presente nel carrello, aggiorna la quantità
		for (CartItem cartItem : cart.getItems()) {
			if (cartItem.getProductVariation().getId().equals(productVariationId)) {
				editQuantity(cartItem, cartItem.getQuantity() + quantity);
				return;
			}
		}

		ProductVariation productVariation = productService.findVariationById(productVariationId);

		if (productVariation == null)
			throw new IllegalArgumentException("Variante di prodotto " + productVariationId + " non trovata");

		verifyQuantityInStock(quantity, productVariation);

		// Calcola il subtotale
		double subTotal = productVariation.getPrice() * quantity;

		// Aggiungi il prodotto al carrello e aggiorna il totale
		CartItem cartItem = new CartItem(productVariation, quantity, subTotal);
		cart.addItem(cartItem);

		// Salva le modifiche
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
	}

	/**
	 * Finalizza un ordine.
	 *
	 * @param cart il carrello con gli oggetti da inserire nell'ordine
	 * @param shippingAddress l'indirizzo di spedizione dell'ordine
	 * @throws NoItemsForFinalizationException se il carrello è vuoto
	 * @throws NoDevicesAvailableException se la quantità richiesta non è disponibile nell'inventario
	 * @throws NoShippingAddressException se l'indirizzo di spedizione non è specificato
	 */
	@Override
	@Transactional
	public void finalizeOrder(Cart cart, ShippingAddress shippingAddress,
	                          PaymentMethod paymentMethod, boolean paymentSuccessfulMock) throws NoItemsForFinalizationException, NoDevicesAvailableException, NoShippingAddressException, NoPaymentMethodException, PaymentFailedException {
		// Verifica che il carrello non sia vuoto
		if (cart.getItems().isEmpty())
			throw new NoItemsForFinalizationException(cart.getId());

		// Verifica che l'indirizzo di spedizione sia specificato
		if (shippingAddress == null)
			throw new NoShippingAddressException();

		// Verifica che il metodo di pagamento sia specificato
		// PaymentMethod non viene usato
		if (paymentMethod == null)
			throw new NoPaymentMethodException();

		User user = cart.getUser();

		// Crea un nuovo ordine
		OrderPlaced order = new OrderPlaced(
			shippingAddress.fullAddress(),
			user.getEmail(),
			new Date(),
			cart.getTotal(),
			false,
			user
		);

		// Aggiungi tutti gli oggetti del carrello all'ordine
		for (CartItem cartItem : cart.getItems()) {
			ProductVariation productVariation = cartItem.getProductVariation();

			verifyQuantityInStock(cartItem.getQuantity(), productVariation);

			OrderItem orderItem = new OrderItem(
				cartItem.getQuantity(),
				cartItem.getSubTotal(),
				order,
				productVariation
			);

			productVariation.setQuantityInStock(productVariation.getQuantityInStock() - cartItem.getQuantity());
			productService.updateVariation(productVariation);

			order.addItem(orderItem);
		}

		if (!paymentSuccessfulMock)
			throw new PaymentFailedException();

		// Salva l'ordine nel database
		orderService.createAndPlaceNewOrder(order);
		clearCart(cart);
	}



	// ================================================================================================================
	// =============== READ ============================================================================================
	// ================================================================================================================

	/**
	 * Ottiene un carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del carrello da cercare
	 * @return l'oggetto Cart corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public Cart findCartById(UUID id) {
		return cartRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene un carrello dal database tramite l'ID dell'utente a cui è associato.
	 *
	 * @param userId l'ID dell'utente a cui è associato il carrello
	 * @return l'oggetto Cart corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public Cart findCartByUserId(UUID userId) {
		return cartRepository.findByUserId(userId).orElse(null);
	}

	/**
	 * Ottiene un oggetto CartItem dal database tramite l'ID.
	 *
	 * @param id l'ID dell'oggetto da cercare
	 * @return l'oggetto CartItem corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public CartItem findCartItemById(UUID id) {
		return cartItemRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene tutti gli oggetti CartItem dal database in base alla variazione di prodotto che rappresentano.
	 *
	 * @param productVariation la variante di prodotto dei CartItem da cercare
	 */
	@Override
	public List<CartItem> findCartItemByProductVariation(ProductVariation productVariation) {
		return cartItemRepository.findByProductVariationId(productVariation.getId());
	}



	// ================================================================================================================
	// =============== UPDATE ==========================================================================================
	// ================================================================================================================

	/**
	 * Aggiorna le informazioni di un carrello nel database.
	 *
	 * @param cart l'oggetto Cart con le nuove informazioni da salvare
	 * @return l'oggetto Cart aggiornato
	 * @throws IllegalArgumentException se l'ID del carrello non è specificato
	 */
	@Override
	public Cart updateCart(Cart cart) {
		if (cart.getId() == null)
			throw new IllegalArgumentException("Impossibile modificare un oggetto senza id per la classe Cart");
		return cartRepository.save(cart);
	}

	/**
	 * Modifica la quantità di un prodotto nel carrello.
	 *
	 * @param cart               il carrello dell'utente in cui modificare la quantità del prodotto
	 * @param productVariationId l'ID della variante di prodotto da modificare
	 * @param newQuantity        la nuova quantità del prodotto
	 */
	@Override
	public void editProductQuantityInCart(Cart cart, UUID productVariationId, int newQuantity) throws NoDevicesAvailableException {
		// Cerca il prodotto nel carrello
		for (CartItem cartItem : cart.getItems()) {
			if (cartItem.getProductVariation().getId().equals(productVariationId)) {
				editQuantity(cartItem, newQuantity);
				return;
			}
		}
	}



	// ================================================================================================================
	// =============== DELETE ==========================================================================================
	// ================================================================================================================

	/**
	 * Elimina un carrello e tutti gli oggetti al suo interno.
	 *
	 * @param cart il carrello da eliminare
	 */
	@Override
	public void deleteCart(Cart cart) {
		List<CartItem> items = cart.getItems();

		if (!items.isEmpty())
			cartItemRepository.deleteAll(items);

		cartRepository.delete(cart);
	}

	/**
	 * Rimuove un prodotto dal carrello.
	 *
	 * @param cart       il carrello dell'utente da cui rimuovere il prodotto
	 * @param cartItemId l'ID dell'oggetto del carrello da rimuovere
	 */
	@Override
	public void removeProductFromCart(Cart cart, UUID cartItemId) {
		// Cerca il prodotto nel carrello
		for (CartItem cartItem : cart.getItems()) {
			if (cartItem.getId().equals(cartItemId)) {
				// Rimuovi il prodotto dal carrello e aggiorna il totale
				cart.removeItem(cartItem);

				// Salva le modifiche
				cartItemRepository.delete(cartItem);
				cartRepository.save(cart);
				return;
			}
		}
	}

	/**
	 * Svuota il carrello.
	 *
	 * @param cart il carrello da svuotare
	 */
	@Override
	public void clearCart(Cart cart) {
		// Rimuovi tutti gli oggetti del carrello dal database
		cartItemRepository.deleteAll(cart.getItems());

		// Svuota il carrello e reimposta il totale
		cart.clear();

		// Salva le modifiche
		cartRepository.save(cart);
	}

	// ================================================================================================================
	// =============== OTHER ===========================================================================================
	// ================================================================================================================

	/**
	 * Verifica che la quantità richiesta sia disponibile nell'inventario.
	 *
	 * @param requestedQuantity la quantità richiesta
	 * @param productVariation  la variante di prodotto da cui prelevare la quantità
	 * @throws NoDevicesAvailableException se la quantità richiesta non è disponibile nell'inventario
	 */
	private static void verifyQuantityInStock(int requestedQuantity, ProductVariation productVariation) throws NoDevicesAvailableException {
		if (productVariation.getQuantityInStock() < requestedQuantity) {
			throw new NoDevicesAvailableException(requestedQuantity, productVariation);
		}
	}

	/**
	 * Modifica la quantità di un prodotto nel carrello.
	 *
	 * @param cartItem    l'oggetto CartItem da modificare
	 * @param newQuantity la nuova quantità del prodotto
	 * @throws NoDevicesAvailableException se la quantità richiesta non è disponibile nell'inventario
	 */
	private void editQuantity(CartItem cartItem, int newQuantity) throws NoDevicesAvailableException {
		if (newQuantity < 1)
			throw new IllegalArgumentException("La quantità deve essere maggiore di 1");

		ProductVariation productVariation = cartItem.getProductVariation();

		verifyQuantityInStock(newQuantity, productVariation);

		double oldSubTotal = cartItem.getSubTotal();

		// Imposta i nuovi valori
		double newSubTotal = productVariation.getPrice() * newQuantity;
		cartItem.setQuantity(newQuantity);
		cartItem.setSubTotal(newSubTotal);

		Cart cart = cartItem.getCart();
		cart.setTotal(cart.getTotal() - oldSubTotal + newSubTotal);

		// Salva le modifiche nel database
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
	}
}
