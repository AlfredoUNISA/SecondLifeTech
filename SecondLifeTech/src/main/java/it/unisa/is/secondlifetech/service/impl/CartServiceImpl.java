package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.*;
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
		return cartRepository.save(cart);
	}

	/**
	 * Aggiunge un nuovo prodotto al carrello.<br/><br/>
	 * <i>Alias: createNewCartItem<i/>
	 *
	 * @param cart               il carrello dell'utente in cui aggiungere il prodotto
	 * @param productVariationId l'ID della variante di prodotto da aggiungere
	 * @param quantity           la quantità del prodotto da aggiungere
	 */
	@Override
	@Transactional
	public void addToCart(Cart cart, UUID productVariationId, int quantity) {
		// Se il prodotto è già presente nel carrello, aggiorna la quantità
		for (CartItem cartItem : cart.getItems()) {
			if (cartItem.getProductVariation().getId().equals(productVariationId)) {
				editQuantity(cartItem, cartItem.getQuantity() + quantity);
				return;
			}
		}

		ProductVariation productVariation = productService.findVariationById(productVariationId);

		verifyQuantityInStock(quantity, productVariation);

		double subTotal = productVariation.getPrice() * quantity;

		CartItem cartItem = new CartItem(productVariation, quantity, subTotal);
		cart.addItem(cartItem); // Aggiorna anche il totale del carrello

		// Salva le modifiche
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
	}

	/**
	 * Finalizza un ordine.
	 *
	 * @param cart il carrello con gli oggetti da inserire nell'ordine
	 * @param shippingAddress l'indirizzo di spedizione dell'ordine
	 */
	@Override
	@Transactional
	public void finalizeOrder(Cart cart, ShippingAddress shippingAddress) {
		if (cart.getItems().isEmpty()) {
			throw new RuntimeException("Un ordine non deve essere vuoto");
		}

		User user = cart.getUser();

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
	 */
	@Override
	public Cart updateCart(Cart cart) {
		if (cart.getId() == null)
			throw new IllegalArgumentException("ID del carrello non specificato nella modifica");
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
	public void editProductQuantityInCart(Cart cart, UUID productVariationId, int newQuantity) {
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
		if (!items.isEmpty()) {
			cartItemRepository.deleteAll(items);
		}
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
				break;
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
	 */
	private static void verifyQuantityInStock(int requestedQuantity, ProductVariation productVariation) {
		if (productVariation.getQuantityInStock() < requestedQuantity) {
			throw new RuntimeException("Quantità non disponibile nell'inventario " +
				"(disponibili: " + productVariation.getQuantityInStock() +
				", richiesti: " + requestedQuantity + ")");
		}
	}

	/**
	 * Modifica la quantità di un prodotto nel carrello.
	 */
	private void editQuantity(CartItem cartItem, int newQuantity) {
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
