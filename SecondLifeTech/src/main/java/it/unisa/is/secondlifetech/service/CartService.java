package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.CartItem;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.ShippingAddress;

import java.util.List;
import java.util.UUID;

public interface CartService {

	// ================================================================================================================
	// =============== CREATE ==========================================================================================
	// ================================================================================================================

	/**
	 * Salva un carrello nel database.
	 *
	 * @param cart l'oggetto Cart da salvare
	 * @return l'oggetto Cart salvato
	 */
	Cart createNewCart(Cart cart);

	/**
	 * Aggiunge un nuovo prodotto al carrello.<br/><br/>
	 * <i>Alias: createNewCartItem<i/>
	 *
	 * @param cart               il carrello dell'utente in cui aggiungere il prodotto
	 * @param productVariationId l'ID della variante di prodotto da aggiungere
	 * @param quantity           la quantità del prodotto da aggiungere
	 */
	void addToCart(Cart cart, UUID productVariationId, int quantity);

	/**
	 * Finalizza un ordine.
	 *
	 * @param cart il carrello con gli oggetti da inserire nell'ordine
	 * @param shippingAddress l'indirizzo di spedizione dell'ordine
	 */
	void finalizeOrder(Cart cart, ShippingAddress shippingAddress);


	// ================================================================================================================
	// =============== READ ============================================================================================
	// ================================================================================================================

	/**
	 * Ottiene un carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del carrello da cercare
	 * @return l'oggetto Cart corrispondente all'ID specificato, o null se non trovato
	 */
	Cart findCartById(UUID id);


	/**
	 * Ottiene un carrello dal database tramite l'ID dell'utente a cui è associato.
	 *
	 * @param userId l'ID dell'utente a cui è associato il carrello
	 * @return l'oggetto Cart corrispondente all'ID specificato, o null se non trovato
	 */
	Cart findCartByUserId(UUID userId);

	/**
	 * Ottiene un oggetto CartItem dal database tramite l'ID.
	 *
	 * @param id l'ID dell'oggetto da cercare
	 * @return   l'oggetto CartItem corrispondente all'ID specificato, o null se non trovato
	 */
	CartItem findCartItemById(UUID id);

	/**
	 * Ottiene tutti gli oggetti CartItem dal database in base alla variazione di prodotto che rappresentano.
	 *
	 * @param productVariation la variante di prodotto dei CartItem da cercare
	 */
	List<CartItem> findCartItemByProductVariation(ProductVariation productVariation);


	// ================================================================================================================
	// =============== UPDATE ==========================================================================================
	// ================================================================================================================

	/**
	 * Aggiorna le informazioni di un carrello nel database.
	 *
	 * @param cart l'oggetto Cart con le nuove informazioni da salvare
	 * @return l'oggetto Cart aggiornato
	 */
	Cart updateCart(Cart cart);

	/**
	 * Modifica la quantità di un prodotto nel carrello.
	 *
	 * @param cart               il carrello dell'utente in cui modificare la quantità del prodotto
	 * @param productVariationId l'ID della variante di prodotto da modificare
	 * @param newQuantity        la nuova quantità del prodotto
	 */
	void editProductQuantityInCart(Cart cart, UUID productVariationId, int newQuantity);

	// ================================================================================================================
	// =============== DELETE ==========================================================================================
	// ================================================================================================================

	/**
	 * Elimina un carrello dal database.
	 *
	 * @param cart il carrello da eliminare
	 */
	void deleteCart(Cart cart);

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
}
