package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartItemService {
	/**
	 * Salva un nuovo prodotto di un carrello nel database.
	 *
	 * @param cartItem l'oggetto CartItem da salvare
	 * @return l'oggetto CartItem salvato
	 */
	CartItem createNewCartItem(CartItem cartItem);

	/**
	 * Ottiene un prodotto di un carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nel carrello da cercare
	 * @return l'oggetto CartItem corrispondente all'ID specificato, o null se non trovato
	 */
	CartItem findCartItemById(UUID id);

	/**
	 * Ottiene tutti i prodotti di un carrello dal database tramite l'ID del carrello.
	 *
	 * @param cartId l'ID del carrello di cui cercare i prodotti
	 * @return una lista di oggetti CartItem corrispondenti all'ID specificato
	 */
	List<CartItem> findCartItemByCart(UUID cartId);

	/**
	 * Aggiorna le informazioni del prodotto di un carrello nel database.
	 *
	 * @param id          l'ID del prodotto nel carrello da aggiornare
	 * @param cartItem l'oggetto CartItem con le nuove informazioni da salvare
	 * @return l'oggetto CartItem aggiornato
	 */
	CartItem updateCartItem(UUID id, CartItem cartItem);

	/**
	 * Elimina un prodotto di un carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nel carrello da eliminare
	 */
	void deleteCartItem(UUID id);
}
