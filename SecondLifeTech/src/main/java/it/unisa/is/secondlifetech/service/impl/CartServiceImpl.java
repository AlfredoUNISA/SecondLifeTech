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

	/**
	 * Aggiunge un nuovo prodotto al carrello.
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

		// Trova la variante di prodotto
		ProductVariation productVariation = productService.findVariationById(productVariationId);

		verifyQuantityInStock(quantity, productVariation);

		// Calcola il subtotale del prodotto
		double subTotal = productVariation.getPrice() * quantity;

		// Crea un nuovo oggetto CartItem
		CartItem cartItem = new CartItem(productVariation, quantity, subTotal);
		cart.addItem(cartItem); // Aggiorna anche il totale del carrello

		// Salva le modifiche
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
	}

	/**
	 * Modifica la quantità di un prodotto nel carrello.
	 *
	 * @param cart               il carrello dell'utente in cui modificare la quantità del prodotto
	 * @param productVariationId l'ID della variante di prodotto da modificare
	 * @param newQuantity        la nuova quantità del prodotto
	 * @return true se è stato trovato il prodotto ed è modificato, false altrimenti
	 */
	@Override
	public boolean editProductQuantityInCart(Cart cart, UUID productVariationId, int newQuantity) {
		// Cerca il prodotto nel carrello
		for (CartItem cartItem : cart.getItems()) {
			if (cartItem.getProductVariation().getId().equals(productVariationId)) {
				editQuantity(cartItem, newQuantity);
				return true;
			}
		}
		return false;
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
	 * Rimuove un prodotto dal carrello.
	 *
	 * @param cart               il carrello dell'utente da cui rimuovere il prodotto
	 * @param productVariationId l'ID della variante di prodotto da rimuovere
	 */
	@Override
	public void removeProductFromCart(Cart cart, UUID productVariationId) {
		// Cerca il prodotto nel carrello
		for (CartItem cartItem : cart.getItems()) {
			if (cartItem.getProductVariation().getId().equals(productVariationId)) {
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
		// Rimuovi tutti i prodotti dal database
		cartItemRepository.deleteAll(cart.getItems());

		// Svuota il carrello e reimposta il totale
		cart.clear();

		// Salva le modifiche
		cartRepository.save(cart);
	}

	/**
	 * Finalizza un ordine.
	 *
	 * @param cart il carrello con gli oggetti da inserire nell'ordine
	 */
	@Override
	@Transactional
	public void finalizeOrder(Cart cart) {
		User user = cart.getUser();

		OrderPlaced order = new OrderPlaced(
			"address",
			user.getEmail(),
			new Date(),
			cart.getTotal(),
			false,
			user
		);

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
			productService.updateVariation(productVariation.getId(), productVariation);

			order.addItem(orderItem);
		}

		orderService.createNewOrder(order);
		clearCart(cart);
	}

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
	 * Aggiorna le informazioni di un carrello nel database.
	 *
	 * @param id   l'ID del carrello da aggiornare
	 * @param cart l'oggetto Cart con le nuove informazioni da salvare
	 * @return l'oggetto Cart aggiornato
	 */
	@Override
	public Cart updateCart(UUID id, Cart cart) {
		cart.setId(id);
		return cartRepository.save(cart);
	}

	/**
	 * Elimina un carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del carrello da eliminare
	 */
	@Override
	public void deleteCart(UUID id) {
		cartRepository.deleteById(id);
	}
}
