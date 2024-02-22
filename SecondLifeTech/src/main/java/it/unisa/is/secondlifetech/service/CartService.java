package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.Cart;

import java.util.UUID;

public interface CartService {
	/**
	 * Aggiunge un nuovo prodotto al carrello.
	 *
	 * @param cart               il carrello dell'utente in cui aggiungere il prodotto
	 * @param productVariationId l'ID della variante di prodotto da aggiungere
	 * @param quantity           la quantità del prodotto da aggiungere
	 */
	void addToCart(Cart cart, UUID productVariationId, int quantity);

	/**
	 * Modifica la quantità di un prodotto nel carrello.
	 *
	 * @param cart               il carrello dell'utente in cui modificare la quantità del prodotto
	 * @param productVariationId l'ID della variante di prodotto da modificare
	 * @param newQuantity        la nuova quantità del prodotto
	 */
	boolean editProductQuantityInCart(Cart cart, UUID productVariationId, int newQuantity);

	/**
	 * Rimuove un prodotto dal carrello.
	 *
	 * @param cart               il carrello dell'utente da cui rimuovere il prodotto
	 * @param productVariationId l'ID della variante di prodotto da rimuovere
	 */
	void removeProductFromCart(Cart cart, UUID productVariationId);

	/**
	 * Svuota il carrello.
	 *
	 * @param cart il carrello da svuotare
	 */
	void clearCart(Cart cart);

	/**
	 * Finalizza un ordine.
	 *
	 * @param cart il carrello con gli oggetti da inserire nell'ordine
	 */
	void finalizeOrder(Cart cart);

	/**
	 * Salva un carrello nel database.
	 *
	 * @param cart l'oggetto Cart da salvare
	 * @return l'oggetto Cart salvato
	 */
	Cart createNewCart(Cart cart);

	/**
	 * Ottiene un carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del carrello da cercare
	 * @return l'oggetto Cart corrispondente all'ID specificato, o null se non trovato
	 */
	Cart findCartById(UUID id);

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
