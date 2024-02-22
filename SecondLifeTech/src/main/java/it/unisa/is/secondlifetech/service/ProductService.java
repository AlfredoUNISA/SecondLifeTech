package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;

import java.util.List;
import java.util.UUID;

public interface ProductService {



// =============== CREATE ===============



	/**
	 * Crea un nuovo modello di prodotto nel database.
	 *
	 * @param productModel l'oggetto ProductModel da creare
	 * @return l'oggetto ProductModel creato
	 */
	ProductModel createNewModel(ProductModel productModel);



	/**
	 * Crea e aggiunge una nuova variante di prodotto a un modello di prodotto nel database.
	 *
	 * @param modelId   l'ID del modello di prodotto a cui aggiungere la variante
	 * @param variation l'oggetto ProductVariation da aggiungere
	 */
	void createAndAddVariationToModel(UUID modelId, ProductVariation variation);

	/**
	 * Crea solamente una nuova variante di prodotto nel database.
	 *
	 * @return l'oggetto ProductVariation creato
	 */
	ProductVariation createNewVariation(ProductVariation productVariation);



// =============== READ ===============



	/**
	 * Ottiene un modello di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID del modello di prodotto da cercare
	 * @return l'oggetto ProductModel corrispondente all'ID specificato, o null se non trovato
	 */
	ProductModel findModelById(UUID id);

	/**
	 * Ottiene una variante di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID della variante di prodotto da cercare
	 * @return l'oggetto ProductVariation corrispondente all'ID specificato, o null se non trovato
	 */
	ProductVariation findVariationById(UUID id);

	/**
	 * Ottiene tutti i modelli di prodotto dal database.
	 *
	 * @return una lista di oggetti ProductModel
	 */
	List<ProductModel> findAllModels();

	/**
	 * Ottiene tutte le varianti di prodotto dal database.
	 *
	 * @return una lista di oggetti ProductVariation
	 */
	List<ProductVariation> findAllVariations();

	/**
	 * Ottiene un modello di prodotto dal database tramite il nome.
	 *
	 * @param name il nome del modello di prodotto da cercare
	 * @return l'oggetto ProductModel corrispondente al nome specificato, o null se non trovato
	 */
	ProductModel findModelByName(String name);

	/**
	 * Ottiene tutti i modelli di prodotto dal database tramite il brand.
	 *
	 * @param brand il brand dei modelli di prodotto da cercare
	 * @return una lista di oggetti ProductModel corrispondenti al brand specificato
	 */
	List<ProductModel> findModelsByBrand(String brand);

	/**
	 * Ottiene tutti i modelli di prodotto dal database tramite la categoria.
	 *
	 * @param category la categoria dei modelli di prodotto da cercare
	 * @return una lista di oggetti ProductModel corrispondenti alla categoria specificata
	 */
	List<ProductModel> findModelsByCategory(String category);

	/**
	 * Ottiene tutte le varianti di prodotto dal database.
	 *
	 * @param state lo stato delle varianti di prodotto da cercare
	 * @return una lista di oggetti ProductVariation
	 */
	List<ProductVariation> findVariationsByState(String state);



// =============== UPDATE ===============



	/**
	 * Aggiorna le informazioni di un modello di prodotto nel database.
	 *
	 * @param id          l'ID del modello di prodotto da aggiornare
	 * @param productModel l'oggetto ProductModel con le nuove informazioni da salvare
	 * @return l'oggetto ProductModel aggiornato
	 */
	ProductModel updateModel(UUID id, ProductModel productModel);

	/**
	 * Aggiorna le informazioni di una variante di prodotto nel database.
	 *
	 * @param variationId                l'ID della variante di prodotto da aggiornare
	 * @param productVariation l'oggetto ProductVariation con le nuove informazioni da salvare
	 * @return l'oggetto ProductVariation aggiornato
	 */
	ProductVariation updateVariation(UUID variationId, ProductVariation productVariation);



// =============== DELETE ===============



	/**
	 * Elimina un modello di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID del modello di prodotto da eliminare
	 */
	void deleteModel(UUID id);

	/**
	 * Rimuove una variante di prodotto da un modello di prodotto nel database.
	 *
	 * @param modelId     l'ID del modello di prodotto da cui rimuovere la variante
	 * @param variationId l'ID della variante di prodotto da rimuovere
	 */
	void deleteVariation(UUID modelId, UUID variationId);
}
