package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.ProductModel;

import java.util.List;
import java.util.UUID;

public interface ProductModelService {
	/**
	 * Salva un modello di prodotto nel database.
	 *
	 * @param productModel l'oggetto ProductModel da salvare
	 * @return l'oggetto ProductModel salvato
	 */
	ProductModel saveProductModel(ProductModel productModel);

	/**
	 * Ottiene un modello di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID del modello di prodotto da cercare
	 * @return l'oggetto ProductModel corrispondente all'ID specificato, o null se non trovato
	 */
	ProductModel findProductModelById(UUID id);

	/**
	 * Ottiene tutti i modelli di prodotto dal database.
	 *
	 * @return una lista di oggetti ProductModel
	 */
	List<ProductModel> findAllProductModels();

	/**
	 * Ottiene un modello di prodotto dal database tramite il nome.
	 *
	 * @param name il nome del modello di prodotto da cercare
	 * @return l'oggetto ProductModel corrispondente al nome specificato, o null se non trovato
	 */
	ProductModel findProductModelByName(String name);

	/**
	 * Ottiene tutti i modelli di prodotto dal database tramite il brand.
	 *
	 * @param brand il brand dei modelli di prodotto da cercare
	 * @return una lista di oggetti ProductModel corrispondenti al brand specificato
	 */
	List<ProductModel> findProductModelsByBrand(String brand);

	/**
	 * Ottiene tutti i modelli di prodotto dal database tramite la categoria.
	 *
	 * @param category la categoria dei modelli di prodotto da cercare
	 * @return una lista di oggetti ProductModel corrispondenti alla categoria specificata
	 */
	List<ProductModel> findProductModelsByCategory(String category);

	/**
	 * Aggiorna le informazioni di un modello di prodotto nel database.
	 *
	 * @param id          l'ID del modello di prodotto da aggiornare
	 * @param productModel l'oggetto ProductModel con le nuove informazioni da salvare
	 * @return l'oggetto ProductModel aggiornato
	 */
	ProductModel updateProductModel(UUID id, ProductModel productModel);

	/**
	 * Elimina un modello di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID del modello di prodotto da eliminare
	 */
	void deleteProductModel(UUID id);
}
