package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.OrderItem;
import it.unisa.is.secondlifetech.entity.OrderPlaced;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.repository.OrderItemRepository;
import it.unisa.is.secondlifetech.repository.ProductModelRepository;
import it.unisa.is.secondlifetech.repository.ProductVariationRepository;
import it.unisa.is.secondlifetech.service.OrderService;
import it.unisa.is.secondlifetech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
	private final ProductModelRepository productModelRepository;
	private final ProductVariationRepository productVariationRepository;
	private final OrderService orderService;
	private final OrderItemRepository orderItemRepository;

	@Autowired
	public ProductServiceImpl(ProductModelRepository productModelRepository, ProductVariationRepository productVariationRepository, OrderService orderService, OrderItemRepository orderItemRepository) {
		this.productModelRepository = productModelRepository;
		this.productVariationRepository = productVariationRepository;
		this.orderService = orderService;
		this.orderItemRepository = orderItemRepository;
	}


	/**
	 * Aggiunge una nuova variante di prodotto a un modello di prodotto nel database.
	 *
	 * @param modelId   l'ID del modello di prodotto a cui aggiungere la variante
	 * @param variation l'oggetto ProductVariation da aggiungere
	 */
	@Override
	public void createAndAddVariationToModel(UUID modelId, ProductVariation variation) {
		ProductModel model = findModelById(modelId);
		model.addVariation(variation);
		productVariationRepository.save(variation);
		productModelRepository.save(model);
	}

	/**
	 * Crea solamente una nuova variante di prodotto nel database.
	 *
	 * @param productVariation l'oggetto ProductVariation da creare
	 * @return l'oggetto ProductVariation creato
	 */
	@Override
	public ProductVariation createNewVariation(ProductVariation productVariation) {
		return productVariationRepository.save(productVariation);
	}


	/**
	 * Rimuove una variante di prodotto da un modello di prodotto nel database.
	 *
	 * @param modelId     l'ID del modello di prodotto da cui rimuovere la variante
	 * @param variationId l'ID della variante di prodotto da rimuovere
	 */
	@Override
	public void deleteVariation(UUID modelId, UUID variationId) {
		ProductModel model = null;
		ProductVariation variation = null;

		// Se la variante è presente in un ordine, aggiorna le informazioni dell'ordine con quelle del modello
		for (OrderItem item : orderItemRepository.findAll()) {
			variation = item.getProductVariation();
			if (variation.getId().equals(variationId)) {
				model = variation.getModel();

				// Modello
				item.setModelName(model.getName());
				item.setBrand(model.getBrand());
				item.setCategory(model.getCategory());

				// Variante
				item.setColor(variation.getColor());
				item.setDisplaySize(variation.getDisplaySize());
				item.setStorageSize(variation.getStorageSize());
				item.setRam(variation.getRam());
				item.setState(variation.getState());
				item.setYear(variation.getYear());

				// Salva
				item.setProductVariation(null);
				orderItemRepository.save(item);
				break;
			} else {
				variation = null;
			}
		}

		if (model == null)
			model = findModelById(modelId);

		if (variation == null)
			variation = findVariationById(variationId);

		model.removeVariation(variation);
		productModelRepository.save(model);
		productVariationRepository.deleteById(variationId);
	}

	/**
	 * Crea un nuovo modello di prodotto nel database.
	 *
	 * @param productModel l'oggetto ProductModel da creare
	 * @return l'oggetto ProductModel creato
	 */
	@Override
	public ProductModel createNewModel(ProductModel productModel) {
		return productModelRepository.save(productModel);
	}

	/**
	 * Ottiene un modello di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID del modello di prodotto da cercare
	 * @return l'oggetto ProductModel corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public ProductModel findModelById(UUID id) {
		return productModelRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene tutti i modelli di prodotto dal database.
	 *
	 * @return una lista di oggetti ProductModel
	 */
	@Override
	public List<ProductModel> findAllModels() {
		return productModelRepository.findAll();
	}

	/**
	 * Ottiene un modello di prodotto dal database tramite il nome.
	 *
	 * @param name il nome del modello di prodotto da cercare
	 * @return l'oggetto ProductModel corrispondente al nome specificato, o null se non trovato
	 */
	@Override
	public ProductModel findModelByName(String name) {
		return productModelRepository.findByName(name);
	}

	/**
	 * Ottiene tutti i modelli di prodotto dal database tramite il brand.
	 *
	 * @param brand il brand dei modelli di prodotto da cercare
	 * @return una lista di oggetti ProductModel corrispondenti al brand specificato
	 */
	@Override
	public List<ProductModel> findModelsByBrand(String brand) {
		return productModelRepository.findByBrand(brand);
	}

	/**
	 * Ottiene tutti i modelli di prodotto dal database tramite la categoria.
	 *
	 * @param category la categoria dei modelli di prodotto da cercare
	 * @return una lista di oggetti ProductModel corrispondenti alla categoria specificata
	 */
	@Override
	public List<ProductModel> findModelsByCategory(String category) {
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
	public ProductModel updateModel(UUID id, ProductModel productModel) {
		productModel.setId(id);
		return productModelRepository.save(productModel);
	}

	/**
	 * Elimina un modello di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID del modello di prodotto da eliminare
	 */
	@Override
	public void deleteModel(UUID id) {
		productVariationRepository.deleteAll(
			findModelById(id).getVariations()
		);

		productModelRepository.deleteById(id);
	}

	/**
	 * Ottiene una variante di prodotto dal database tramite l'ID.
	 *
	 * @param id l'ID della variante di prodotto da cercare
	 * @return l'oggetto ProductVariation corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public ProductVariation findVariationById(UUID id) {
		return productVariationRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene tutte le varianti di prodotto dal database.
	 *
	 * @param state lo stato delle varianti di prodotto da cercare
	 * @return una lista di oggetti ProductVariation
	 */
	@Override
	public List<ProductVariation> findVariationsByState(String state) {
		return productVariationRepository.findByState(state);
	}

	/**
	 * Ottiene tutte le varianti di prodotto dal database.
	 *
	 * @return una lista di oggetti ProductVariation
	 */
	@Override
	public List<ProductVariation> findAllVariations() {
		return productVariationRepository.findAll();
	}

	/**
	 * Aggiorna le informazioni di una variante di prodotto nel database.
	 *
	 * @param variationId      l'ID della variante di prodotto da aggiornare
	 * @param productVariation l'oggetto ProductVariation con le nuove informazioni da salvare
	 * @return l'oggetto ProductVariation aggiornato
	 */
	@Override
	public ProductVariation updateVariation(UUID variationId, ProductVariation productVariation) {
		productVariation.setId(variationId);
		return productVariationRepository.save(productVariation);
	}
}
