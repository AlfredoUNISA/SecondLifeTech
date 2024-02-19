package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.CartItem;
import it.unisa.is.secondlifetech.repository.CartItemRepository;
import it.unisa.is.secondlifetech.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementazione del servizio per la gestione dei prodotti di un carrello.
 */
@Service
public class CartItemServiceImpl implements CartItemService {
	private final CartItemRepository cartItemRepository;

	@Autowired
	public CartItemServiceImpl(CartItemRepository cartItemRepository) {
		this.cartItemRepository = cartItemRepository;
	}

	/**
	 * Salva un nuovo prodotto di un carrello nel database.
	 *
	 * @param cartItem l'oggetto CartItem da salvare
	 * @return l'oggetto CartItem salvato
	 */
	@Override
	public CartItem createNewCartItem(CartItem cartItem) {
		if (cartItem.getId() != null) {
			throw new IllegalArgumentException("Utilizzare il metodo updateCartItem per modificare il prodotto di un carrello esistente.");
		}

		return cartItemRepository.save(cartItem);
	}

	/**
	 * Ottiene un prodotto di un carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nel carrello da cercare
	 * @return l'oggetto CartItem corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public CartItem findCartItemById(UUID id) {
		return cartItemRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene tutti i prodotti di un carrello dal database tramite l'ID del carrello.
	 *
	 * @param cartId l'ID del carrello di cui cercare i prodotti
	 * @return una lista di oggetti CartItem corrispondenti all'ID specificato
	 */
	@Override
	public List<CartItem> findCartItemByCart(UUID cartId) {
		return cartItemRepository.findByCartId(cartId);
	}

	/**
	 * Aggiorna le informazioni del prodotto di un carrello nel database.
	 *
	 * @param id          l'ID del prodotto nel carrello da aggiornare
	 * @param cartItem l'oggetto CartItem con le nuove informazioni da salvare
	 * @return l'oggetto CartItem aggiornato
	 */
	@Override
	public CartItem updateCartItem(UUID id, CartItem cartItem) {
		cartItem.setId(id);
		return cartItemRepository.save(cartItem);
	}

	/**
	 * Elimina un prodotto di un carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nel carrello da eliminare
	 */
	@Override
	public void deleteCartItem(UUID id) {
		cartItemRepository.deleteById(id);
	}
}
