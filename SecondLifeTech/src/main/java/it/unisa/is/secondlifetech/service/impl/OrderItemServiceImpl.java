package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.OrderItem;
import it.unisa.is.secondlifetech.repository.OrderItemRepository;
import it.unisa.is.secondlifetech.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderItemServiceImpl implements OrderItemService {
	private final OrderItemRepository orderItemRepository;

	@Autowired
	public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}

	/**
	 * Crea un prodotto in un ordine nel database.
	 *
	 * @param orderItem l'oggetto OrderItem da creare
	 * @return l'oggetto OrderItem creato
	 */
	@Override
	public OrderItem createNewOrderItem(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}

	/**
	 * Ottiene un prodotto di un ordine dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nell'ordine da cercare
	 * @return l'oggetto OrderItem corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public OrderItem findOrderItemById(UUID id) {
		return orderItemRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene tutti i prodotti di un ordine specifico dal database tramite l'ID dell'ordine.
	 *
	 * @param orderPlacedId l'ID dell'ordine di cui cercare i prodotti
	 * @return una lista di oggetti OrderItem corrispondenti all'ID specificato
	 */
	@Override
	public List<OrderItem> findOrderItemsByOrderPlaced(UUID orderPlacedId) {
		return orderItemRepository.findByOrderPlacedId(orderPlacedId);
	}

	/**
	 * Aggiorna le informazioni di un prodotto di un ordine nel database.
	 *
	 * @param id        l'ID del prodotto nell'ordine da aggiornare
	 * @param orderItem l'oggetto OrderItem con le nuove informazioni da salvare
	 * @return l'oggetto OrderItem aggiornato
	 */
	@Override
	public OrderItem updateOrderItem(UUID id, OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}

	/**
	 * Elimina un prodotto di un ordine dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nell'ordine da eliminare
	 */
	@Override
	public void deleteOrderItem(UUID id) {
		orderItemRepository.deleteById(id);
	}
}
