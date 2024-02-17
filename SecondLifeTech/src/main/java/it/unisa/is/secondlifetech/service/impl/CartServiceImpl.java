package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.CartProduct;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.repository.CartProductRepository;
import it.unisa.is.secondlifetech.repository.CartRepository;
import it.unisa.is.secondlifetech.repository.ProductVariationRepository;
import it.unisa.is.secondlifetech.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final ProductVariationRepository productVariationRepository;
	private final CartProductRepository cartProductRepository;

	@Autowired
	public CartServiceImpl(CartRepository cartRepository, ProductVariationRepository productVariationRepository, CartProductRepository cartProductRepository) {
		this.cartRepository = cartRepository;
		this.productVariationRepository = productVariationRepository;
		this.cartProductRepository = cartProductRepository;
	}

	/**
	 * Aggiunge un prodotto al carrello.
	 *
	 * @param cartId             l'ID del carrello in cui aggiungere il prodotto
	 * @param productVariationId l'ID della variante di prodotto da aggiungere
	 * @param quantity           la quantità del prodotto da aggiungere
	 */

	@Override
	public void addToCart(UUID cartId, UUID productVariationId, int quantity) {
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new RuntimeException("Carrello non trovato"));

		ProductVariation productVariation = productVariationRepository.findById(productVariationId)
			.orElseThrow(() -> new RuntimeException("Variazione del prodotto non trovata"));

		if (productVariation.getQuantityInStock() < quantity) {
			throw new RuntimeException("Quantità non disponibile nell'inventario");
		}

		double subTotal = productVariation.getPrice() * quantity;

		CartProduct cartProduct = new CartProduct(cart, productVariation, quantity, subTotal);

		cart.getProducts().add(cartProduct);

		// Aggiorna il totale del carrello
		cart.setTotal(cart.getTotal() + subTotal);

		// Salva le modifiche
		cartRepository.save(cart);
		productVariationRepository.save(productVariation);
	}



	/**
	 * Modifica la quantità di un prodotto nel carrello.
	 *
	 * @param cartId             l'ID del carrello in cui modificare la quantità del prodotto
	 * @param productVariationId l'ID della variante di prodotto da modificare
	 * @param quantity           la nuova quantità del prodotto
	 */
	@Override
	public void editProductQuantityInCart(UUID cartId, UUID productVariationId, int newQuantity) {
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new RuntimeException("Carrello non trovato"));

		ProductVariation productVariation = productVariationRepository.findById(productVariationId)
			.orElseThrow(() -> new RuntimeException("Variazione del prodotto non trovata"));

		if (productVariation.getQuantityInStock() < newQuantity) {
			throw new RuntimeException("Quantità non disponibile nell'inventario");
		}

		for (CartProduct cartProduct : cart.getProducts()) {
			if (cartProduct.getProductVariation().getId().equals(productVariationId)) {
				double oldSubTotal = cartProduct.getSubTotal();
				double newSubTotal = productVariation.getPrice() * newQuantity;

				cartProduct.setQuantity(newQuantity);
				cartProduct.setSubTotal(newSubTotal);

				cart.setTotal(cart.getTotal() - oldSubTotal + newSubTotal);
				break;
			}
		}

		cartRepository.save(cart);
		productVariationRepository.save(productVariation);
	}

	/**
	 * Rimuove un prodotto dal carrello.
	 *
	 * @param cartId             l'ID del carrello da cui rimuovere il prodotto
	 * @param productVariationId l'ID della variante di prodotto da rimuovere
	 */
	@Override
	public void removeProductFromCart(UUID cartId, UUID productVariationId) {
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new RuntimeException("Carrello non trovato"));

		for (CartProduct cartProduct : cart.getProducts()) {
			if (cartProduct.getProductVariation().getId().equals(productVariationId)) {
				cart.setTotal(cart.getTotal() - cartProduct.getSubTotal());
				cart.getProducts().remove(cartProduct);
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
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new RuntimeException("Carrello non trovato"));

		cart.setTotal(0);
		cart.getProducts().clear();
	}

	/**
	 * Salva un carrello nel database.
	 *
	 * @param cart l'oggetto Cart da salvare
	 * @return l'oggetto Cart salvato
	 */
	@Override
	public Cart saveCart(Cart cart) {
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
