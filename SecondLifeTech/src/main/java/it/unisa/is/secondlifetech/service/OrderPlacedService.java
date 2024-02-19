package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.OrderPlaced;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OrderPlacedService {
	/**
	 * Salva un ordine nel database.
	 *
	 * @param order l'oggetto OrderPlaced da salvare
	 * @return l'oggetto OrderPlaced salvato
	 */
	OrderPlaced createNewOrder(OrderPlaced order);

	/**
	 * Ottiene un ordine dal database tramite l'ID.
	 *
	 * @param id l'ID dell'ordine da cercare
	 * @return l'oggetto OrderPlaced corrispondente all'ID specificato, o null se non trovato
	 */
	OrderPlaced findOrderById(UUID id);

	/**
	 * Ottiene tutti gli ordini dal database tramite l'email dell'utente.
	 *
	 * @param email l'email dell'utente di cui cercare gli ordini
	 * @return una lista di oggetti OrderPlaced corrispondenti all'email specificata
	 */
	List<OrderPlaced> findOrderByEmail(String email);

	/**
	 * Ottiene tutti gli ordini dal database tramite lo stato di spedizione.
	 *
	 * @param shipped lo stato di spedizione di cui cercare gli ordini
	 * @return una lista di oggetti OrderPlaced corrispondenti allo stato di spedizione specificato
	 */
	List<OrderPlaced> findOrderByShipped(boolean shipped);

	/**
	 * Ottiene tutti gli ordini dal database tramite la data dell'ordine.
	 *
	 * @param orderDate la data dell'ordine di cui cercare gli ordini
	 * @return una lista di oggetti OrderPlaced corrispondenti alla data specificata
	 */
	List<OrderPlaced> findOrderByDate(Date orderDate);

	/**
	 * Aggiorna le informazioni di un ordine nel database.
	 *
	 * @param id l'ID dell'ordine da aggiornare
	 * @param order l'oggetto OrderPlaced con le nuove informazioni da salvare
	 * @return l'oggetto OrderPlaced aggiornato
	 */
	OrderPlaced updateOrder(UUID id, OrderPlaced order);

	/**
	 * Elimina un ordine dal database.
	 *
	 * @param id l'ID dell'ordine da eliminare
	 */
	void deleteOrder(OrderPlaced id);
}
