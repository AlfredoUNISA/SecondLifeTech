package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderItemService {
	/**
	 * Crea un prodotto in un ordine nel database.
	 *
	 * @param orderItem l'oggetto OrderItem da creare
	 * @return l'oggetto OrderItem creato
	 */
	OrderItem createNewOrderItem(OrderItem orderItem);

	/**
	 * Ottiene un prodotto di un ordine dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nell'ordine da cercare
	 * @return l'oggetto OrderItem corrispondente all'ID specificato, o null se non trovato
	 */
	OrderItem findOrderItemById(UUID id);

	/**
	 * Ottiene tutti i prodotti di un ordine specifico dal database tramite l'ID dell'ordine.
	 *
	 * @param orderPlacedId l'ID dell'ordine di cui cercare i prodotti
	 * @return una lista di oggetti OrderItem corrispondenti all'ID specificato
	 */
	List<OrderItem> findOrderItemsByOrderPlaced(UUID orderPlacedId);

	/**
	 * Aggiorna le informazioni di un prodotto di un ordine nel database.
	 *
	 * @param id        l'ID del prodotto nell'ordine da aggiornare
	 * @param orderItem l'oggetto OrderItem con le nuove informazioni da salvare
	 * @return l'oggetto OrderItem aggiornato
	 */
	OrderItem updateOrderItem(UUID id, OrderItem orderItem);

	/**
	 * Elimina un prodotto di un ordine dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nell'ordine da eliminare
	 */
	void deleteOrderItem(UUID id);
}
