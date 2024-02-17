package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.repository.ProductModelRepository;
import it.unisa.is.secondlifetech.service.ProductModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductModelServiceImpl implements ProductModelService {
	private final ProductModelRepository productModelRepository;

	@Autowired
	public ProductModelServiceImpl(ProductModelRepository productModelRepository) {
		this.productModelRepository = productModelRepository;
	}

	/**
	 * Salva un modello di prodotto nel database.
	 *
	 * @param productModel l'oggetto ProductModel da salvare
	 * @return l'oggetto ProductModel salvato
	 */
	@Override
	public ProductModel saveProductModel(ProductModel productModel) {
		return productModelRepository.save(productModel);
	}

	/**
	 * Ottiene un modello di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID del modello di prodotto da cercare
	 * @return l'oggetto ProductModel corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public ProductModel findProductModelById(UUID id) {
		return productModelRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene tutti i modelli di prodotto dal database.
	 *
	 * @return una lista di oggetti ProductModel
	 */
	@Override
	public List<ProductModel> findAllProductModels() {
		return productModelRepository.findAll();
	}

	/**
	 * Ottiene un modello di prodotto dal database tramite il nome.
	 *
	 * @param name il nome del modello di prodotto da cercare
	 * @return l'oggetto ProductModel corrispondente al nome specificato, o null se non trovato
	 */
	@Override
	public ProductModel findProductModelByName(String name) {
		return productModelRepository.findByName(name);
	}

	/**
	 * Ottiene tutti i modelli di prodotto dal database tramite il brand.
	 *
	 * @param brand il brand dei modelli di prodotto da cercare
	 * @return una lista di oggetti ProductModel corrispondenti al brand specificato
	 */
	@Override
	public List<ProductModel> findProductModelsByBrand(String brand) {
		return productModelRepository.findByBrand(brand);
	}

	/**
	 * Ottiene tutti i modelli di prodotto dal database tramite la categoria.
	 *
	 * @param category la categoria dei modelli di prodotto da cercare
	 * @return una lista di oggetti ProductModel corrispondenti alla categoria specificata
	 */
	@Override
	public List<ProductModel> findProductModelsByCategory(String category) {
		return productModelRepository.findByCategory(category);
	}

	/**
	 * Aggiorna le informazioni di un modello di prodotto nel database.
	 *
	 * @param id           l'ID del modello di prodotto da aggiornare
	 * @param productModel l'oggetto ProductModel con le nuove informazioni da salvare
	 * @return l'oggetto ProductModel aggiornato
	 */
	@Override
	public ProductModel updateProductModel(UUID id, ProductModel productModel) {
		productModel.setId(id);
		return productModelRepository.save(productModel);
	}

	/**
	 * Elimina un modello di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID del modello di prodotto da eliminare
	 */
	@Override
	public void deleteProductModel(UUID id) {
		productModelRepository.deleteById(id);
	}
}
