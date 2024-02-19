package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.OrderPlaced;
import it.unisa.is.secondlifetech.repository.OrderPlacedRepository;
import it.unisa.is.secondlifetech.service.OrderPlacedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderPlacedServiceImpl implements OrderPlacedService {
	private OrderPlacedRepository orderPlacedRepository;

	@Autowired
	public OrderPlacedServiceImpl(OrderPlacedRepository orderPlacedRepository) {
		this.orderPlacedRepository = orderPlacedRepository;
	}

	/**
	 * Salva un ordine nel database.
	 *
	 * @param order l'oggetto OrderPlaced da salvare
	 * @return l'oggetto OrderPlaced salvato
	 */
	@Override
	public OrderPlaced createNewOrder(OrderPlaced order) {
		return orderPlacedRepository.save(order);
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
		return orderPlacedRepository.findByOrderDate(orderDate);
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
	 * Elimina un ordine dal database.
	 *
	 * @param id l'ID dell'ordine da eliminare
	 */
	@Override
	public void deleteOrder(OrderPlaced id) {
		orderPlacedRepository.delete(id);
	}
}
