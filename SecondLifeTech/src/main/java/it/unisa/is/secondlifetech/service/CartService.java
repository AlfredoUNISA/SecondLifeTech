package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.Cart;

import java.util.UUID;

public interface CartService {
	/**
	 * Aggiunge un prodotto al carrello.
	 *
	 * @param cartId             l'ID del carrello in cui aggiungere il prodotto
	 * @param productVariationId l'ID della variante di prodotto da aggiungere
	 * @param quantity           la quantità del prodotto da aggiungere
	 */
	void addToCart(UUID cartId, UUID productVariationId, int quantity);

	/**
	 * Modifica la quantità di un prodotto nel carrello.
	 *
	 * @param cartId             l'ID del carrello in cui modificare la quantità del prodotto
	 * @param productVariationId l'ID della variante di prodotto da modificare
	 * @param quantity           la nuova quantità del prodotto
	 */
	void modifyProductQuantityInCart(UUID cartId, UUID productVariationId, int quantity);

	/**
	 * Rimuove un prodotto dal carrello.
	 *
	 * @param cartId             l'ID del carrello da cui rimuovere il prodotto
	 * @param productVariationId l'ID della variante di prodotto da rimuovere
	 */
	void removeProductFromCart(UUID cartId, UUID productVariationId);

	/**
	 * Svuota il carrello.
	 *
	 * @param cartId l'ID del carrello da svuotare
	 */
	void clearCart(UUID cartId);

	/**
	 * Salva un carrello nel database.
	 *
	 * @param cart l'oggetto Cart da salvare
	 * @return l'oggetto Cart salvato
	 */
	Cart saveCart(Cart cart);

	/**
	 * Ottiene un carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del carrello da cercare
	 * @return l'oggetto Cart corrispondente all'ID specificato, o null se non trovato
	 */
	Cart findCartById(UUID id);

	/**
	 * Ottiene un carrello dal database tramite l'ID dell'utente.
	 *
	 * @param userId l'ID dell'utente di cui cercare il carrello
	 * @return l'oggetto Cart corrispondente all'ID specificato, o null se non trovato
	 */
	Cart findCartByUser(UUID userId);

	/**
	 * Aggiorna le informazioni di un carrello nel database.
	 *
	 * @param id   l'ID del carrello da aggiornare
	 * @param cart l'oggetto Cart con le nuove informazioni da salvare
	 * @return l'oggetto Cart aggiornato
	 */
	Cart updateCart(UUID id, Cart cart);

	/**
	 * Elimina un carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del carrello da eliminare
	 */
	void deleteCart(UUID id);
}
