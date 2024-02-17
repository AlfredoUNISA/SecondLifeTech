package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.CartProduct;
import it.unisa.is.secondlifetech.repository.CartProductRepository;
import it.unisa.is.secondlifetech.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartProductServiceImpl implements CartProductService {
	private final CartProductRepository cartProductRepository;

	@Autowired
	public CartProductServiceImpl(CartProductRepository cartProductRepository) {
		this.cartProductRepository = cartProductRepository;
	}

	/**
	 * Salva un prodotto nel carrello nel database.
	 *
	 * @param cartProduct l'oggetto CartProduct da salvare
	 * @return l'oggetto CartProduct salvato
	 */
	@Override
	public CartProduct saveCartProduct(CartProduct cartProduct) {
		return cartProductRepository.save(cartProduct);
	}

	/**
	 * Ottiene un prodotto nel carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nel carrello da cercare
	 * @return l'oggetto CartProduct corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public CartProduct findCartProductById(UUID id) {
		return cartProductRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene tutti i prodotti nel carrello dal database tramite l'ID del carrello.
	 *
	 * @param cartId l'ID del carrello di cui cercare i prodotti
	 * @return una lista di oggetti CartProduct corrispondenti all'ID specificato
	 */
	@Override
	public List<CartProduct> findCartProductsByCart(UUID cartId) {
		return cartProductRepository.findByCartId(cartId);
	}

	/**
	 * Aggiorna le informazioni di un prodotto nel carrello nel database.
	 *
	 * @param id          l'ID del prodotto nel carrello da aggiornare
	 * @param cartProduct l'oggetto CartProduct con le nuove informazioni da salvare
	 * @return l'oggetto CartProduct aggiornato
	 */
	@Override
	public CartProduct updateCartProduct(UUID id, CartProduct cartProduct) {
		cartProduct.setId(id);
		return cartProductRepository.save(cartProduct);
	}

	/**
	 * Elimina un prodotto nel carrello dal database tramite l'ID.
	 *
	 * @param id l'ID del prodotto nel carrello da eliminare
	 */
	@Override
	public void deleteCartProduct(UUID id) {
		cartProductRepository.deleteById(id);
	}
}
