package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.CartItem;
import it.unisa.is.secondlifetech.repository.CartItemRepository;
import it.unisa.is.secondlifetech.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartItemServiceImpl implements CartItemService {
	private final CartItemRepository cartItemRepository;

	@Autowired
	public CartItemServiceImpl(CartItemRepository cartItemRepository) {
		this.cartItemRepository = cartItemRepository;
	}

	/**
	 * Salva un prodotto nel carrello nel database.
	 *
	 * @param cartItem l'oggetto CartItem da salvare
	 * @return l'oggetto CartItem salvato
	 */
	@Override
	public CartItem createNewCartProduct(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}

	/**
	 * Ottiene un prodotto nel carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nel carrello da cercare
	 * @return l'oggetto CartItem corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public CartItem findCartProductById(UUID id) {
		return cartItemRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene tutti i prodotti nel carrello dal database tramite l'ID del carrello.
	 *
	 * @param cartId l'ID del carrello di cui cercare i prodotti
	 * @return una lista di oggetti CartItem corrispondenti all'ID specificato
	 */
	@Override
	public List<CartItem> findCartProductsByCart(UUID cartId) {
		return cartItemRepository.findByCartId(cartId);
	}

	/**
	 * Aggiorna le informazioni di un prodotto nel carrello nel database.
	 *
	 * @param id          l'ID del prodotto nel carrello da aggiornare
	 * @param cartItem l'oggetto CartItem con le nuove informazioni da salvare
	 * @return l'oggetto CartItem aggiornato
	 */
	@Override
	public CartItem updateCartProduct(UUID id, CartItem cartItem) {
		cartItem.setId(id);
		return cartItemRepository.save(cartItem);
	}

	/**
	 * Elimina un prodotto nel carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nel carrello da eliminare
	 */
	@Override
	public void deleteCartProduct(UUID id) {
		cartItemRepository.deleteById(id);
	}
}
