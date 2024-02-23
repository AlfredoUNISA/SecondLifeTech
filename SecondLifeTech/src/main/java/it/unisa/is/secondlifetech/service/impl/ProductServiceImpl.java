package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.ImageFile;
import it.unisa.is.secondlifetech.entity.OrderItem;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.repository.ImageFileRepository;
import it.unisa.is.secondlifetech.repository.OrderItemRepository;
import it.unisa.is.secondlifetech.repository.ProductModelRepository;
import it.unisa.is.secondlifetech.repository.ProductVariationRepository;
import it.unisa.is.secondlifetech.service.OrderService;
import it.unisa.is.secondlifetech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
	private final ProductModelRepository productModelRepository;
	private final ProductVariationRepository productVariationRepository;
	private final OrderService orderService;
	private final OrderItemRepository orderItemRepository;
	private final ImageFileRepository imageFileRepository;

	@Autowired
	public ProductServiceImpl(ProductModelRepository productModelRepository, ProductVariationRepository productVariationRepository, OrderService orderService, OrderItemRepository orderItemRepository, ImageFileRepository imageFileRepository) {
		this.productModelRepository = productModelRepository;
		this.productVariationRepository = productVariationRepository;
		this.orderService = orderService;
		this.orderItemRepository = orderItemRepository;
		this.imageFileRepository = imageFileRepository;
	}


	// ================================================================================================================
	// =============== CREATE ==========================================================================================
	// ================================================================================================================

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
	 * Aggiunge una nuova variante di prodotto a un modello di prodotto nel database.
	 *
	 * @param model     l'oggetto ProductModel a cui aggiungere la variante
	 * @param variation l'oggetto ProductVariation da aggiungere
	 */
	@Override
	public ProductVariation createNewVariation(ProductModel model, ProductVariation variation) {
		model.addVariation(variation);

		ProductVariation toReturn = productVariationRepository.save(variation);
		productModelRepository.save(model);

		return toReturn;
	}

	/**
	 * Sostituisce l'immagine di un modello con un nuova immagine.<br/><br/>
	 * Da usare per <b>creare</b> una nuova immagine o <b>aggiornare</b> l'immagine di un modello.
	 *
	 * @param model il modello di prodotto a cui aggiungere l'immagine
	 * @param image il file da aggiungere
	 * @return l'oggetto ImageFile aggiunto
	 */
	@Override
	public ImageFile changeImageModel(ProductModel model, MultipartFile image) throws IOException {
		if (image.isEmpty())
			return changeImageModel(model, (ImageFile) null);

		ImageFile imageFile = new ImageFile();
		imageFile.setName(image.getOriginalFilename());
		imageFile.setContentType(image.getContentType());
		imageFile.setData(image.getBytes());


		return changeImageModel(model, imageFile);
	}

	/**
	 * Sostituisce l'immagine di un modello con un nuova immagine.<br/><br/>
	 * Da usare per <b>creare</b> una nuova immagine o <b>aggiornare</b> l'immagine di un modello.
	 *
	 * @param model     il modello di prodotto a cui aggiungere l'immagine
	 * @param imageFile l'immagine da aggiungere
	 * @return l'oggetto ImageFile aggiunto
	 */
	@Override
	public ImageFile changeImageModel(ProductModel model, ImageFile imageFile) {
		if (imageFile == null)
			return null;

		model.changeImage(imageFile);
		ImageFile toReturn = imageFileRepository.save(imageFile);
		productModelRepository.save(model);
		return toReturn;
	}


	// ================================================================================================================
	// =============== READ ============================================================================================
	// ================================================================================================================

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
	 * Ottiene un'immagine dal database tramite l'ID.
	 *
	 * @param id l'ID dell'immagine da cercare
	 * @return l'oggetto ImageFile corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public ImageFile findImageById(UUID id) {
		return imageFileRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene un modello di prodotto dal database tramite il nome.
	 *
	 * @param name il nome del modello di prodotto da cercare
	 * @return l'oggetto ProductModel corrispondente al nome specificato, o null se non trovato
	 */
	@Override
	public ProductModel findModelByName(String name) {
		return productModelRepository.findByName(name).orElse(null);
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
	 * Ottiene tutti i modelli di prodotto dal database.
	 *
	 * @return una lista di oggetti ProductModel
	 */
	@Override
	public List<ProductModel> findAllModels() {
		return productModelRepository.findAll();
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


	// ================================================================================================================
	// =============== UPDATE ==========================================================================================
	// ================================================================================================================

	/**
	 * Aggiorna le informazioni di un modello di prodotto nel database.
	 *
	 * @param productModel l'oggetto ProductModel con le nuove informazioni da salvare
	 * @return l'oggetto ProductModel aggiornato
	 */
	@Override
	public ProductModel updateModel(ProductModel productModel) {
		return productModelRepository.save(productModel);
	}

	/**
	 * Aggiorna le informazioni di una variante di prodotto nel database.
	 *
	 * @param productVariation l'oggetto ProductVariation con le nuove informazioni da salvare
	 * @return l'oggetto ProductVariation aggiornato
	 */
	@Override
	public ProductVariation updateVariation(ProductVariation productVariation) {
		return productVariationRepository.save(productVariation);
	}


	// ================================================================================================================
	// =============== DELETE ==========================================================================================
	// ================================================================================================================

	/**
	 * Elimina un modello di prodotto e le sue variazioni dal database.
	 *
	 * @param model l'oggetto ProductModel da eliminare
	 */
	@Override
	public void deleteModel(ProductModel model) {
		if (model.getImageFile() != null)
			imageFileRepository.delete(model.getImageFile());

		List<ProductVariation> variations = model.getVariations();

		// Copia della lista per evitare ConcurrentModificationException
		for (ProductVariation variation : new ArrayList<>(variations))
			deleteVariation(variation);

		productModelRepository.delete(model);
	}

	/**
	 * Rimuove una variante di prodotto da un modello di prodotto nel database.
	 *
	 * @param variation l'oggetto ProductVariation da rimuovere
	 */
	@Override
	public void deleteVariation(ProductVariation variation) {
		ProductModel model = variation.getModel();

		// Se la variante è presente in un ordine, aggiorna le informazioni dell'ordine
		for (OrderItem item : orderService.findOrderItemsByProductVariation(variation)) {
			// Informazioni Modello
			item.setModelName(model.getName());
			item.setBrand(model.getBrand());
			item.setCategory(model.getCategory());

			// Informazioni Variante
			item.setColor(variation.getColor());
			item.setDisplaySize(variation.getDisplaySize());
			item.setStorageSize(variation.getStorageSize());
			item.setRam(variation.getRam());
			item.setState(variation.getState());
			item.setYear(variation.getYear());

			// Salva
			item.setProductVariation(null);
			orderItemRepository.save(item);
		}

		model.removeVariation(variation);
		productVariationRepository.delete(variation);
		productModelRepository.save(model);
	}

	/**
	 * Elimina un'immagine dal database.
	 *
	 * @param image l'oggetto ImageFile da eliminare
	 */
	@Override
	public void deleteImage(ImageFile image) {
		image.getModel().removeImage();
		productModelRepository.save(image.getModel());
		imageFileRepository.delete(image);
	}
}
