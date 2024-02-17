package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.CartProduct;

import java.util.List;
import java.util.UUID;

public interface CartProductService {
	/**
	 * Salva un prodotto nel carrello nel database.
	 *
	 * @param cartProduct l'oggetto CartProduct da salvare
	 * @return l'oggetto CartProduct salvato
	 */
	CartProduct saveCartProduct(CartProduct cartProduct);

	/**
	 * Ottiene un prodotto nel carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nel carrello da cercare
	 * @return l'oggetto CartProduct corrispondente all'ID specificato, o null se non trovato
	 */
	CartProduct findCartProductById(UUID id);

	/**
	 * Ottiene tutti i prodotti nel carrello dal database tramite l'ID del carrello.
	 *
	 * @param cartId l'ID del carrello di cui cercare i prodotti
	 * @return una lista di oggetti CartProduct corrispondenti all'ID specificato
	 */
	List<CartProduct> findCartProductsByCart(UUID cartId);

	/**
	 * Aggiorna le informazioni di un prodotto nel carrello nel database.
	 *
	 * @param id          l'ID del prodotto nel carrello da aggiornare
	 * @param cartProduct l'oggetto CartProduct con le nuove informazioni da salvare
	 * @return l'oggetto CartProduct aggiornato
	 */
	CartProduct updateCartProduct(UUID id, CartProduct cartProduct);

	/**
	 * Elimina un prodotto nel carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nel carrello da eliminare
	 */
	void deleteCartProduct(UUID id);
}
