package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.CartItem;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.repository.CartItemRepository;
import it.unisa.is.secondlifetech.repository.CartRepository;
import it.unisa.is.secondlifetech.service.CartService;
import it.unisa.is.secondlifetech.service.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final ProductVariationService productVariationService;
	private final CartItemRepository cartItemRepository;

	@Autowired
	public CartServiceImpl(CartRepository cartRepository, ProductVariationService productVariationService, CartItemRepository cartItemRepository) {
		this.cartRepository = cartRepository;
		this.productVariationService = productVariationService;
		this.cartItemRepository = cartItemRepository;
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
		ProductVariation productVariation = productVariationService.findProductVariationById(productVariationId);

		verifyQuantityInStock(quantity, productVariation);

		// Calcola il subtotale del prodotto
		double subTotal = productVariation.getPrice() * quantity;

		// Crea un nuovo oggetto CartItem e lo aggiunge al carrello
		CartItem cartItem = new CartItem(productVariation, quantity, subTotal);
		cartItemRepository.save(cartItem);
		cart.addItem(cartItem);

		// Aggiorna il totale del carrello
		cart.setTotal(cart.getTotal() + subTotal);

		// Salva le modifiche
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
	private static void verifyQuantityInStock(int newQuantity, ProductVariation productVariation) {
		if (productVariation.getQuantityInStock() < newQuantity) {
			throw new RuntimeException("Quantità non disponibile nell'inventario");
		}
	}

	/**
	 * Rimuove un prodotto dal carrello.
	 *
	 * @param cartId             l'ID del carrello da cui rimuovere il prodotto
	 * @param productVariationId l'ID della variante di prodotto da rimuovere
	 */
	@Override
	public void removeProductFromCart(UUID cartId, UUID productVariationId) {
		// Trova il carrello
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new RuntimeException("Carrello non trovato"));

		// Cerca il prodotto nel carrello
		for (CartItem cartItem : cart.getItems()) {
			if (cartItem.getProductVariation().getId().equals(productVariationId)) {
				// Rimuovi il prodotto dal carrello
				cart.setTotal(cart.getTotal() - cartItem.getSubTotal());
				cart.getItems().remove(cartItem);

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
	 * @param cartId l'ID del carrello da svuotare
	 */
	@Override
	public void clearCart(UUID cartId) {
		// Trova il carrello
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new RuntimeException("Carrello non trovato"));

		// Rimuovi tutti i prodotti dal database
		cartItemRepository.deleteAll(cart.getItems());

		// Svuota il carrello
		cart.getItems().clear();
		cart.setTotal(0);

		// Salva le modifiche
		cartRepository.save(cart);
	}

	/**
	 * Salva un carrello nel database.
	 *
	 * @param cart l'oggetto Cart da salvare
	 * @return l'oggetto Cart salvato
	 */
	@Override
	public Cart createNewCart(Cart cart) {
		if (!cart.getUser().getRole().equals(UserRole.CLIENTE))
			throw new RuntimeException("L'utente non è un cliente");
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
	 * Ottiene un carrello dal database tramite l'ID dell'utente.
	 *
	 * @param userId l'ID dell'utente di cui cercare il carrello
	 * @return l'oggetto Cart corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public Cart findCartByUser(UUID userId) {
		return cartRepository.findByUserId(userId).orElse(null);
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
