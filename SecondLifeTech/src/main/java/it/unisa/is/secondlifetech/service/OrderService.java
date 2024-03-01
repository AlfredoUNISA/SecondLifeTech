package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.OrderItem;
import it.unisa.is.secondlifetech.entity.OrderPlaced;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.exception.NoItemsForFinalizationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Interfaccia per il servizio di gestione degli ordini.<br/><br/>
 * <i>Non si dovrebbero eliminare gli ordini o i loro oggetti, ma sono comunque presenti i metodi appositi.</i>
 */
public interface OrderService {

	// ================================================================================================================
	// =============== CREATE ==========================================================================================
	// ================================================================================================================

	/**
	 * Crea un nuovo ordine nel database.<br/><br/>
	 * <b>Salva anche tutti gli OrderItems all'interno della sua lista</b>, pertanto bisogna assicurarsi che siano presenti
	 * tutti gli OrderItem necessari.
	 *
	 * @param order l'oggetto OrderPlaced da creare
	 * @return l'oggetto OrderPlaced creato
	 */
	OrderPlaced createAndPlaceNewOrder(OrderPlaced order);

	// ================================================================================================================
	// =============== READ ============================================================================================
	// ================================================================================================================

	/**
	 * Ottiene un ordine dal database tramite l'ID.
	 *
	 * @param id l'ID dell'ordine da cercare
	 * @return l'oggetto OrderPlaced corrispondente all'ID specificato, o null se non trovato
	 */
	OrderPlaced findOrderById(UUID id);

	/**
	 * Ottiene un oggetto all'interno di un ordine dal database tramite l'ID.
	 *
	 * @param id l'ID dell'oggetto da cercare
	 * @return l'oggetto OrderItem corrispondente all'ID specificato, o null se non trovato
	 */
	OrderItem findOrderItemById(UUID id);

	/**
	 * Ottiene tutti gli ordini dal database tramite l'email dell'utente.
	 *
	 * @param email l'email dell'utente di cui cercare gli ordini
	 * @return una lista di oggetti OrderPlaced corrispondenti all'email specificata
	 */
	List<OrderPlaced> findOrdersByEmail(String email);

	/**
	 * Ottiene tutti gli ordini dal database tramite l'email dell'utente con un sistema a Paginazione.
	 *
	 * @param email l'email dell'utente di cui cercare gli ordini
	 * @return una pagina di oggetti OrderPlaced corrispondenti all'email specificata
	 */
	Page<OrderPlaced> findOrdersByEmailPaginated(String email, Pageable pageable);

	/**
	 * Ottiene tutti gli ordini dal database tramite lo stato di spedizione.
	 *
	 * @param shipped lo stato di spedizione di cui cercare gli ordini
	 * @return una lista di oggetti OrderPlaced corrispondenti allo stato di spedizione specificato
	 */
	List<OrderPlaced> findOrdersByShipped(boolean shipped);

	/**
	 * Ottiene tutti gli ordini dal database tramite lo stato della spedizione dell'ordine con un sistema a paginazione.
	 *
	 * @param shipped la data dell'ordine di cui cercare gli ordini
	 * @param pageable il sistema di paginazione
	 * @return una lista di oggetti OrderPlaced corrispondenti alla data specificata
	 */
	Page<OrderPlaced> findOrdersByShippedPaginated(boolean shipped, Pageable pageable);


	/**
	 * Ottiene tutti gli ordini dal database tramite la data dell'ordine.
	 *
	 * @param orderDate la data dell'ordine di cui cercare gli ordini
	 * @return una lista di oggetti OrderPlaced corrispondenti alla data specificata
	 */
	List<OrderPlaced> findOrdersByDate(Date orderDate);

	/**
	 * Ottiene tutti gli ordini dal database tramite la data dell'ordine con un sistema a paginazione.
	 *
	 * @param orderDate la data dell'ordine di cui cercare gli ordini
	 * @param pageable il sistema di paginazione
	 * @return una lista di oggetti OrderPlaced corrispondenti alla data specificata
	 */
	Page<OrderPlaced> findOrdersByDatePaginated(Date orderDate, Pageable pageable);

	/**
	 * Ottiene tutti gli oggetti all'interno di un ordine dal database tramite il prodotto.
	 *
	 * @param productVariation il prodotto di cui cercare gli oggetti
	 * @return una lista di oggetti OrderItem
	 */
	List<OrderItem> findOrderItemsByProductVariation(ProductVariation productVariation);

	/**
	 * Ottiene tutti gli ordini dal database.
	 *
	 * @return una lista di oggetti OrderPlaced
	 */
	List<OrderPlaced> findAllOrders();

	/**
	 * Ottiene tutti gli ordini dal database con un sistema a Paginazione.
	 *
	 * @return una pagina di oggetti OrderPlaced
	 */
	Page<OrderPlaced> findAllOrdersPaginated(Pageable pageable);

	/**
	 * Ottiene tutti gli oggetti all'interno di un ordine dal database.
	 *
	 * @return una lista di oggetti OrderItem
	 */
	List<OrderItem> findAllOrderItems();


	// ================================================================================================================
	// =============== UPDATE ==========================================================================================

	// ================================================================================================================

	/**
	 * Aggiorna le informazioni di un ordine nel database.
	 *
	 * @param order l'oggetto OrderPlaced con le nuove informazioni da salvare
	 * @return l'oggetto OrderPlaced aggiornato
	 */
	OrderPlaced updateOrder(OrderPlaced order);

	/**
	 * Aggiorna le informazioni di un oggetto all'interno di un ordine nel database.
	 *
	 * @param orderItem l'oggetto OrderItem con le nuove informazioni da salvare
	 * @return l'oggetto OrderItem aggiornato
	 */
	OrderItem updateOrderItem(OrderItem orderItem);

	/**
	 * Aggiorna lo stato di spedizione a "Spedito" di un ordine nel database.
	 *
	 * @param order l'oggetto OrderPlaced di cui aggiornare lo stato di spedizione
	 * @return l'oggetto OrderPlaced aggiornato
	 */
	OrderPlaced setOrderAsShipped(OrderPlaced order);


	// ================================================================================================================
	// =============== DELETE ==========================================================================================
	// ================================================================================================================

	/**
	 * Elimina un ordine e tutti gli oggetti al suo interno dal database.
	 *
	 * @param order l'ordine da eliminare
	 */
	void deleteOrder(OrderPlaced order);

	/**
	 * Elimina un oggetto all'interno di un ordine dal database.
	 *
	 * @param orderItem l'oggetto da eliminare
	 */
	void deleteOrderItem(OrderItem orderItem);
}
