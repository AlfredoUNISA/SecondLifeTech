package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.repository.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductVariationServiceImpl implements it.unisa.is.secondlifetech.service.ProductVariationService {
	private final ProductVariationService productVariationService;

	@Autowired
	public ProductVariationServiceImpl(ProductVariationService productVariationService) {
		this.productVariationService = productVariationService;
	}

	/**
	 * Crea una nuova variante di prodotto nel database.
	 *
	 * @param productVariation l'oggetto ProductVariation da creare
	 * @return l'oggetto ProductVariation creato
	 */
	@Override
	public ProductVariation createNewProductVariation(ProductVariation productVariation) {
		return productVariationService.save(productVariation);
	}

	/**
	 * Ottiene una variante di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID della variante di prodotto da cercare
	 * @return l'oggetto ProductVariation corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public ProductVariation findProductVariationById(UUID id) {
		return productVariationService.findById(id).orElse(null);
	}

	/**
	 * Ottiene tutte le varianti di un prodotto dal database tramite l'ID del modello.
	 *
	 * @param productId l'ID del prodotto di cui cercare le varianti
	 * @return una lista di oggetti ProductVariation corrispondenti all'ID specificato
	 */
	@Override
	public List<ProductVariation> findProductVariationsByProductModelId(UUID productId) {
		return productVariationService.findByProductModelId(productId);
	}

	/**
	 * Ottiene tutte le varianti di prodotto dal database tramite lo stato.
	 *
	 * @param state lo stato delle varianti di prodotto da cercare
	 * @return una lista di oggetti ProductVariation corrispondenti allo stato specificato
	 */
	@Override
	public List<ProductVariation> findProductVariationsByState(String state) {
		return productVariationService.findByState(state);
	}

	/**
	 * Aggiorna le informazioni di una variante di prodotto nel database.
	 *
	 * @param id               l'ID della variante di prodotto da aggiornare
	 * @param productVariation l'oggetto ProductVariation con le nuove informazioni da salvare
	 * @return l'oggetto ProductVariation aggiornato
	 */
	@Override
	public ProductVariation updateProductVariation(UUID id, ProductVariation productVariation) {
		productVariation.setId(id);
		return productVariationService.save(productVariation);
	}

	/**
	 * Elimina una variante di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID della variante di prodotto da eliminare
	 */
	@Override
	public void deleteProductVariation(UUID id) {
		productVariationService.deleteById(id);
	}
}
