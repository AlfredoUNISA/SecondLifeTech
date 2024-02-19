package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartItemService {
	/**
	 * Salva un prodotto nel carrello nel database.
	 *
	 * @param cartItem l'oggetto CartItem da salvare
	 * @return l'oggetto CartItem salvato
	 */
	CartItem createNewCartProduct(CartItem cartItem);

	/**
	 * Ottiene un prodotto nel carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nel carrello da cercare
	 * @return l'oggetto CartItem corrispondente all'ID specificato, o null se non trovato
	 */
	CartItem findCartProductById(UUID id);

	/**
	 * Ottiene tutti i prodotti nel carrello dal database tramite l'ID del carrello.
	 *
	 * @param cartId l'ID del carrello di cui cercare i prodotti
	 * @return una lista di oggetti CartItem corrispondenti all'ID specificato
	 */
	List<CartItem> findCartProductsByCart(UUID cartId);

	/**
	 * Aggiorna le informazioni di un prodotto nel carrello nel database.
	 *
	 * @param id          l'ID del prodotto nel carrello da aggiornare
	 * @param cartItem l'oggetto CartItem con le nuove informazioni da salvare
	 * @return l'oggetto CartItem aggiornato
	 */
	CartItem updateCartProduct(UUID id, CartItem cartItem);

	/**
	 * Elimina un prodotto nel carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nel carrello da eliminare
	 */
	void deleteCartProduct(UUID id);
}
