package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.ProductVariation;

import java.util.List;
import java.util.UUID;

public interface ProductVariationService {
	/**
	 * Crea una nuova variante di prodotto nel database.
	 *
	 * @param productVariation l'oggetto ProductVariation da creare
	 * @return l'oggetto ProductVariation creato
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
	 * Ottiene tutte le varianti di prodotto dal database tramite lo stato.
	 *
	 * @param state lo stato delle varianti di prodotto da cercare
	 * @return una lista di oggetti ProductVariation corrispondenti allo stato specificato
	 */
	List<ProductVariation> findProductVariationsByState(String state);

	/**
	 * Ottiene tutte le varianti di prodotto dal database.
	 *
	 * @return una lista di tutte le varianti di prodotto
	 */
	List<ProductVariation> findAllProductVariations();

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
