package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.ImageFile;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProductService {

	// ================================================================================================================
	// =============== CREATE ==========================================================================================
	// ================================================================================================================

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
	 * @param model     il modello di prodotto a cui aggiungere la variante
	 * @param variation l'oggetto ProductVariation da aggiungere
	 */
	ProductVariation createNewVariation(ProductModel model, ProductVariation variation);

	/**
	 * Sostituisce l'immagine di un modello con un nuova immagine.<br/><br/>
	 * Da usare per <b>creare</b> una nuova immagine o <b>aggiornare</b> l'immagine di un modello.
	 *
	 * @param model  il modello di prodotto a cui aggiungere l'immagine
	 * @param image   il file da aggiungere
	 * @return l'oggetto ImageFile aggiunto
	 */
	ImageFile changeImageModel(ProductModel model, MultipartFile image) throws IOException;

	/**
	 * Sostituisce l'immagine di un modello con un nuova immagine.<br/><br/>
	 * Da usare per <b>creare</b> una nuova immagine o <b>aggiornare</b> l'immagine di un modello.
	 *
	 * @param model      il modello di prodotto a cui aggiungere l'immagine
	 * @param imageFile  l'immagine da aggiungere
	 * @return l'oggetto ImageFile aggiunto
	 */
	ImageFile changeImageModel(ProductModel model, ImageFile imageFile);


	// ================================================================================================================
	// =============== READ ============================================================================================
	// ================================================================================================================

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
	 * Ottiene un'immagine dal database tramite l'ID.
	 *
	 * @param id l'ID dell'immagine da cercare
	 * @return l'oggetto ImageFile corrispondente all'ID specificato, o null se non trovato
	 */
	ImageFile findImageById(UUID id);

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



	// ================================================================================================================
	// =============== UPDATE ==========================================================================================
	// ================================================================================================================

	/**
	 * Aggiorna le informazioni di un modello di prodotto nel database.
	 *
	 * @param productModel l'oggetto ProductModel con le nuove informazioni da salvare
	 * @return l'oggetto ProductModel aggiornato
	 */
	ProductModel updateModel(ProductModel productModel);

	/**
	 * Aggiorna le informazioni di una variante di prodotto nel database.
	 *
	 * @param productVariation l'oggetto ProductVariation con le nuove informazioni da salvare
	 * @return l'oggetto ProductVariation aggiornato
	 */
	ProductVariation updateVariation(ProductVariation productVariation);



	// ================================================================================================================
	// =============== DELETE ==========================================================================================
	// ================================================================================================================

	/**
	 * Elimina un modello di prodotto e le sue variazioni dal database.
	 *
	 * @param model l'oggetto ProductModel da eliminare
	 */
	void deleteModel(ProductModel model);

	/**
	 * Rimuove una variante di prodotto da un modello di prodotto nel database.
	 *
	 * @param variation l'oggetto ProductVariation da rimuovere
	 */
	void deleteVariation(ProductVariation variation);

	/**
	 * Elimina un'immagine dal database.
	 *
	 * @param image l'oggetto ImageFile da eliminare
	 */
	void deleteImage(ImageFile image);
}
