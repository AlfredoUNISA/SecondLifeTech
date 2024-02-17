package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.repository.CartRepository;
import it.unisa.is.secondlifetech.repository.ProductVariationRepository;
import it.unisa.is.secondlifetech.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final ProductVariationRepository productVariationRepository;

	@Autowired
	public CartServiceImpl(CartRepository cartRepository, ProductVariationRepository productVariationRepository) {
		this.cartRepository = cartRepository;
		this.productVariationRepository = productVariationRepository;
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
		/*
		// Trova il carrello corrispondente all'ID fornito
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new RuntimeException("Carrello non trovato"));

		// Trova la variazione del prodotto corrispondente all'ID fornito
		ProductVariation productVariation = productVariationRepository.findById(productVariationId)
			.orElseThrow(() -> new RuntimeException("Variazione del prodotto non trovata"));

		// Verifica se la quantità richiesta è disponibile nel tuo inventario
		if (productVariation.getQuantityInStock() < quantity) {
			throw new RuntimeException("Quantità non disponibile nel inventario");
		}

		// Aggiorna la quantità del prodotto nel carrello
		int updatedQuantityInCart = quantity;
		for (ProductVariation pv : cart.getProducts()) {
			if (pv.getId().equals(productVariationId)) {
				updatedQuantityInCart += pv.getQuantityInCart();
				break;
			}
		}
		productVariation.setQuantityInCart(updatedQuantityInCart);

		// Aggiungi il prodotto al carrello
		cart.getProducts().add(productVariation);
		// Aggiorna la quantità disponibile nel tuo inventario
		productVariation.setQuantityInStock(productVariation.getQuantityInStock() - quantity);

		// Aggiorna il totale del carrello
		cart.setTotal(cart.getTotal() + (productVariation.getPrice() * quantity));

		// Salva le modifiche
		cartRepository.save(cart);
		productVariationRepository.save(productVariation);
		*/
	}



	/**
	 * Modifica la quantità di un prodotto nel carrello.
	 *
	 * @param cartId             l'ID del carrello in cui modificare la quantità del prodotto
	 * @param productVariationId l'ID della variante di prodotto da modificare
	 * @param quantity           la nuova quantità del prodotto
	 */
	@Override
	public void modifyProductQuantityInCart(UUID cartId, UUID productVariationId, int quantity) {

	}

	/**
	 * Rimuove un prodotto dal carrello.
	 *
	 * @param cartId             l'ID del carrello da cui rimuovere il prodotto
	 * @param productVariationId l'ID della variante di prodotto da rimuovere
	 */
	@Override
	public void removeProductFromCart(UUID cartId, UUID productVariationId) {

	}

	/**
	 * Svuota il carrello.
	 *
	 * @param cartId l'ID del carrello da svuotare
	 */
	@Override
	public void clearCart(UUID cartId) {

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
