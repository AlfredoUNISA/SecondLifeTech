package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.OrderPlaced;
import it.unisa.is.secondlifetech.repository.OrderItemRepository;
import it.unisa.is.secondlifetech.repository.OrderPlacedRepository;
import it.unisa.is.secondlifetech.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

	/**
	 * Crea un nuovo ordine nel database.
	 * Salva anche tutti gli OrderItems all'interno della lista.
	 *
	 * @param order l'oggetto OrderPlaced da creare
	 * @return l'oggetto OrderPlaced creato
	 * @throws RuntimeException se l'ordine è vuoto
	 */
	@Override
	public OrderPlaced createNewOrder(OrderPlaced order) throws RuntimeException {
		if (order.getItems().isEmpty()) {
			throw new RuntimeException("Un ordine non deve essere vuoto");
		}

		OrderPlaced result = orderPlacedRepository.save(order);
		orderItemRepository.saveAll(order.getItems());
		return result;
	}

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
	 * Ottiene tutti gli ordini dal database.
	 *
	 * @return una lista di oggetti OrderPlaced
	 */
	@Override
	public List<OrderPlaced> findAllOrders() {
		return orderPlacedRepository.findAll();
	}

	/**
	 * Ottiene tutti gli ordini dal database tramite l'email dell'utente.
	 *
	 * @param email l'email dell'utente di cui cercare gli ordini
	 * @return una lista di oggetti OrderPlaced corrispondenti all'email specificata
	 */
	@Override
	public List<OrderPlaced> findOrderByEmail(String email) {
		return orderPlacedRepository.findByEmail(email);
	}

	/**
	 * Ottiene tutti gli ordini dal database tramite lo stato di spedizione.
	 *
	 * @param shipped lo stato di spedizione di cui cercare gli ordini
	 * @return una lista di oggetti OrderPlaced corrispondenti allo stato di spedizione specificato
	 */
	@Override
	public List<OrderPlaced> findOrderByShipped(boolean shipped) {
		return orderPlacedRepository.findByShipped(shipped);
	}

	/**
	 * Ottiene tutti gli ordini dal database tramite la data dell'ordine.
	 *
	 * @param orderDate la data dell'ordine di cui cercare gli ordini
	 * @return una lista di oggetti OrderPlaced corrispondenti alla data specificata
	 */
	@Override
	public List<OrderPlaced> findOrderByDate(Date orderDate) {
		return orderPlacedRepository.findByDate(orderDate);
	}

	/**
	 * Aggiorna le informazioni di un ordine nel database.
	 *
	 * @param id l'ID dell'ordine da aggiornare
	 * @param order l'oggetto OrderPlaced con le nuove informazioni da salvare
	 * @return l'oggetto OrderPlaced aggiornato
	 */
	@Override
	public OrderPlaced updateOrder(UUID id, OrderPlaced order) {
		order.setId(id);
		return orderPlacedRepository.save(order);
	}

	/**
	 * Elimina un ordine e tutti gli oggetti al suo interno dal database tramite l'ID.
	 *
	 * @param id l'ID dell'ordine da eliminare
	 */
	@Override
	public void deleteOrder(UUID id) {
		orderPlacedRepository.deleteById(id);
	}

	/**
	 * Elimina un ordine e tutti gli oggetti al suo interno dal database.
	 *
	 * @param order l'ordine da eliminare
	 */
	@Override
	public void deleteOrder(OrderPlaced order) {
		orderPlacedRepository.delete(order);
	}
}
