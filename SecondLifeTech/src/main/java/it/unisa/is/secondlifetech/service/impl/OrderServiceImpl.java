package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.OrderItem;
import it.unisa.is.secondlifetech.entity.OrderPlaced;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.repository.OrderItemRepository;
import it.unisa.is.secondlifetech.repository.OrderPlacedRepository;
import it.unisa.is.secondlifetech.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
	private final OrderPlacedRepository orderPlacedRepository;
	private final OrderItemRepository orderItemRepository;

	@Autowired
	public OrderServiceImpl(OrderPlacedRepository orderPlacedRepository, OrderItemRepository orderItemRepository) {
		this.orderPlacedRepository = orderPlacedRepository;
		this.orderItemRepository = orderItemRepository;
	}


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
	@Override
	public OrderPlaced createAndPlaceNewOrder(OrderPlaced order) throws RuntimeException {
		if (order == null)
			return null;

		if (order.getId() != null)
			throw new IllegalArgumentException("Usare la funzione di aggiornamento per modificare un ordine esistente");

		OrderPlaced toReturn = orderPlacedRepository.save(order);
		orderItemRepository.saveAll(order.getItems());
		return toReturn;
	}



	// ================================================================================================================
	// =============== READ ============================================================================================
	// ================================================================================================================

	/**
	 * Ottiene un ordine dal database tramite l'ID.
	 *
	 * @param id l'ID dell'ordine da cercare
	 * @return l'oggetto OrderPlaced corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public OrderPlaced findOrderById(UUID id) {
		return orderPlacedRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene un oggetto all'interno di un ordine dal database tramite l'ID.
	 *
	 * @param id l'ID dell'oggetto da cercare
	 * @return l'oggetto OrderItem corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public OrderItem findOrderItemById(UUID id) {
		return orderItemRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene tutti gli ordini dal database tramite l'email dell'utente.
	 *
	 * @param email l'email dell'utente di cui cercare gli ordini
	 * @return una lista di oggetti OrderPlaced corrispondenti all'email specificata
	 */
	@Override
	public List<OrderPlaced> findOrdersByEmail(String email) {
		return orderPlacedRepository.findByEmail(email);
	}

	/**
	 * Ottiene tutti gli ordini dal database tramite l'email dell'utente con un sistema a paginazione.
	 *
	 * @param email l'email dell'utente di cui cercare gli ordini
	 * @return una pagina di oggetti OrderPlaced corrispondenti all'email specificata
	 */
	@Override
	public Page<OrderPlaced> findOrdersByEmailPaginated(String email, Pageable pageable) {
		return orderPlacedRepository.findByEmail(email, pageable);
	}

	/**
	 * Ottiene tutti gli ordini dal database tramite lo stato di spedizione.
	 *
	 * @param shipped lo stato di spedizione di cui cercare gli ordini
	 * @return una lista di oggetti OrderPlaced corrispondenti allo stato di spedizione specificato
	 */
	@Override
	public List<OrderPlaced> findOrdersByShipped(boolean shipped) {
		return orderPlacedRepository.findByShipped(shipped);
	}


	/**
	 * Ottiene tutti gli ordini dal database tramite lo stato della spedizione dell'ordine con un sistema a paginazione.
	 *
	 * @param shipped la data dell'ordine di cui cercare gli ordini
	 * @param pageable il sistema di paginazione
	 * @return una lista di oggetti OrderPlaced corrispondenti alla data specificata
	 */
	@Override
	public Page<OrderPlaced> findOrdersByShippedPaginated(boolean shipped, Pageable pageable) {
		return orderPlacedRepository.findByShipped(shipped, pageable);
	}

	/**
	 * Ottiene tutti gli ordini dal database tramite la data dell'ordine.
	 *
	 * @param orderDate la data dell'ordine di cui cercare gli ordini
	 * @return una lista di oggetti OrderPlaced corrispondenti alla data specificata
	 */
	@Override
	public List<OrderPlaced> findOrdersByDate(Date orderDate) {
		return orderPlacedRepository.findByDate(orderDate);
	}

	/**
	 * Ottiene tutti gli ordini dal database tramite la data dell'ordine con un sistema a paginazione.
	 *
	 * @param orderDate la data dell'ordine di cui cercare gli ordini
	 * @param pageable il sistema di paginazione
	 * @return una lista di oggetti OrderPlaced corrispondenti alla data specificata
	 */
	@Override
	public Page<OrderPlaced> findOrdersByDatePaginated(Date orderDate, Pageable pageable) {
		return orderPlacedRepository.findByDate(orderDate, pageable);
	}

	/**
	 * Ottiene tutti gli oggetti all'interno di un ordine dal database tramite il prodotto.
	 *
	 * @param productVariation il prodotto di cui cercare gli oggetti
	 * @return una lista di oggetti OrderItem
	 */
	@Override
	public List<OrderItem> findOrderItemsByProductVariation(ProductVariation productVariation) {
		return orderItemRepository.findByProductVariationId(productVariation.getId());
	}

	/**
	 * Ottiene tutti gli ordini dal database.
	 *
	 * @return una lista di oggetti OrderPlaced
	 */
	@Override
	public List<OrderPlaced> findAllOrders() {
		return orderPlacedRepository.findAll();
	}

	/**
	 * Ottiene tutti gli oggetti all'interno di un ordine dal database.
	 *
	 * @return una lista di oggetti OrderItem
	 */
	@Override
	public List<OrderItem> findAllOrderItems() {
		return orderItemRepository.findAll();
	}

	/**
	 * Ottiene tutti gli ordini dal database con un sistema a Paginazione.
	 *
	 * @return una pagina di oggetti OrderPlaced
	 */
	@Override
	public Page<OrderPlaced> findAllOrdersPaginated(Pageable pageable) {
		return orderPlacedRepository.findAll(pageable);
	}

	public Page<OrderPlaced> findAllOrdersPaginatedWithFilters(String email, Date date, Boolean shipped, Pageable pageable) {
		return null;
	}


	// ================================================================================================================
	// =============== UPDATE ==========================================================================================
	// ================================================================================================================

	/**
	 * Aggiorna le informazioni di un ordine nel database.
	 *
	 * @param order l'oggetto OrderPlaced con le nuove informazioni da salvare
	 * @return l'oggetto OrderPlaced aggiornato
	 */
	@Override
	public OrderPlaced updateOrder(OrderPlaced order) {
		if (order.getId() == null)
			throw new IllegalArgumentException("Impossibile modificare un oggetto senza id per la classe OrderPlaced");

		return orderPlacedRepository.save(order);
	}

	/**
	 * Aggiorna le informazioni di un oggetto all'interno di un ordine nel database.
	 *
	 * @param orderItem l'oggetto OrderItem con le nuove informazioni da salvare
	 * @return l'oggetto OrderItem aggiornato
	 */
	@Override
	public OrderItem updateOrderItem(OrderItem orderItem) {
		if (orderItem.getId() == null)
			throw new IllegalArgumentException("Impossibile modificare un oggetto senza id per la classe OrderItem");

		return orderItemRepository.save(orderItem);
	}

	@Override
	public OrderPlaced setOrderAsShipped(OrderPlaced order) {
		if (order.getId() == null)
			throw new IllegalArgumentException("Impossibile modificare un oggetto senza id per la classe OrderPlaced");

		order.setShipped(true);
		return orderPlacedRepository.save(order);
	}


	// ================================================================================================================
	// =============== DELETE ==========================================================================================
	// ================================================================================================================

	/**
	 * Elimina un ordine e tutti gli oggetti al suo interno dal database.
	 *
	 * @param order l'ordine da eliminare
	 */
	@Override
	public void deleteOrder(OrderPlaced order) {
		orderItemRepository.deleteAll(order.getItems());
		orderPlacedRepository.delete(order);
	}

	/**
	 * Elimina un oggetto all'interno di un ordine dal database.
	 *
	 * @param orderItem l'oggetto da eliminare
	 */
	@Override
	public void deleteOrderItem(OrderItem orderItem) {
		orderItemRepository.delete(orderItem);
	}
}
