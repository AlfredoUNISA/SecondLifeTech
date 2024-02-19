package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.ProductVariation;

import java.util.List;
import java.util.UUID;

public interface ProductVariationService {
	/**
	 * Salva una variante di prodotto nel database.
	 *
	 * @param productVariation l'oggetto ProductVariation da salvare
	 * @return l'oggetto ProductVariation salvato
	 */
	ProductVariation createNewProductVariation(ProductVariation productVariation);

	/**
	 * Ottiene una variante di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID della variante di prodotto da cercare
	 * @return l'oggetto ProductVariation corrispondente all'ID specificato, o null se non trovato
	 */
	ProductVariation findProductVariationById(UUID id);

	/**
	 * Ottiene tutte le varianti di un prodotto dal database tramite l'ID del modello.
	 * @param productId l'ID del prodotto di cui cercare le varianti
	 * @return una lista di oggetti ProductVariation corrispondenti all'ID specificato
	 */
	List<ProductVariation> findProductVariationsByProductModelId(UUID productId);

	/**
	 * Ottiene tutte le varianti di prodotto dal database tramite lo stato.
	 * @param state lo stato delle varianti di prodotto da cercare
	 * @return una lista di oggetti ProductVariation corrispondenti allo stato specificato
	 */
	List<ProductVariation> findProductVariationsByState(String state);

	/**
	 * Aggiorna le informazioni di una variante di prodotto nel database.
	 *
	 * @param id                l'ID della variante di prodotto da aggiornare
	 * @param productVariation l'oggetto ProductVariation con le nuove informazioni da salvare
	 * @return l'oggetto ProductVariation aggiornato
	 */
	ProductVariation updateProductVariation(UUID id, ProductVariation productVariation);

	/**
	 * Elimina una variante di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID della variante di prodotto da eliminare
	 */
	void deleteProductVariation(UUID id);

}
