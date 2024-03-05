package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.dto.ProductFilters;
import it.unisa.is.secondlifetech.entity.ImageFile;
import it.unisa.is.secondlifetech.entity.OrderItem;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.exception.ErrorInField;
import it.unisa.is.secondlifetech.exception.MissingRequiredField;
import it.unisa.is.secondlifetech.repository.ImageFileRepository;
import it.unisa.is.secondlifetech.repository.OrderItemRepository;
import it.unisa.is.secondlifetech.repository.ProductModelRepository;
import it.unisa.is.secondlifetech.repository.ProductVariationRepository;
import it.unisa.is.secondlifetech.service.OrderService;
import it.unisa.is.secondlifetech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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
	public ProductModel createNewModel(ProductModel productModel) throws ErrorInField, MissingRequiredField {
		if (productModel == null)
			return null;

		checkProductModelValues(productModel);

		return productModelRepository.save(productModel);
	}

	/**
	 * Aggiunge una nuova variante di prodotto a un modello di prodotto nel database.
	 *
	 * @param model     l'oggetto ProductModel a cui aggiungere la variante
	 * @param variation l'oggetto ProductVariation da aggiungere
	 */
	@Override
	public ProductVariation createNewVariation(ProductModel model, ProductVariation variation) throws ErrorInField, MissingRequiredField {
		if (variation == null)
			return null;

		checkProductVariationValues(variation);

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
			return null;

		// Trasforma il file in un oggetto ImageFile
		ImageFile imageFile = new ImageFile();
		imageFile.setName(image.getOriginalFilename());
		imageFile.setContentType(image.getContentType());
		imageFile.setData(image.getBytes());

		// Se il modello ha già un'immagine, elimina l'immagine precedente
		if (model.getImageFile() != null) {
			deleteImage(model.getImageFile());
		}

		// Aggiorna l'immagine del modello
		model.changeImage(imageFile);

		// Salva l'immagine e il modello
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
	 * Ottiene tutti i modelli di prodotto dal database.
	 *
	 * @return una lista di oggetti ProductModel
	 */
	@Override
	public Page<ProductModel> findAllModelsPaginated(Pageable pageable) {
		return productModelRepository.findAll(pageable);
	}

	/**
	 * Ottiene tutti i modelli di prodotto dal database con i filtri specificati.
	 *
	 * @param filters i filtri da applicare
	 * @return una lista di oggetti ProductModel
	 */
	@Override
	public Page<ProductModel> findAllModelsPaginatedWithFilters(ProductFilters filters, Pageable pageable) throws ErrorInField {
		List<ProductModel> allModels = productModelRepository.findAll();
		List<ProductModel> filteredModels = doFilter(filters, allModels);

		// Paginazione
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;

		List<ProductModel> paginatedList;

		if (filteredModels.size() < startItem) {
			paginatedList = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, filteredModels.size());
			paginatedList = filteredModels.subList(startItem, toIndex);
		}

		return new PageImpl<>(
			paginatedList,
			PageRequest.of(currentPage, pageSize),
			filteredModels.size()
		);
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
	 * @param model l'oggetto ProductModel con le nuove informazioni da salvare
	 * @param image        il file da aggiungere come immagine del modello
	 * @return l'oggetto ProductModel aggiornato
	 */
	@Override
	public ProductModel updateModel(ProductModel model, MultipartFile image) throws IOException, ErrorInField, MissingRequiredField {
		if (model.getId() == null)
			throw new IllegalArgumentException("Impossibile modificare un oggetto senza id per la classe ProductModel");

		checkProductModelValues(model);

		// Copia dell'immagine originale in caso non sia stata modificata
		ProductModel original = findModelById(model.getId());
		model.setImageFile(original.getImageFile());

		// Se è stata specificata una nuova immagine, aggiorna l'immagine
		if(image != null && !image.isEmpty()) {
			changeImageModel(model, image);
			return model; // Il modello viene salvato in changeImageModel
		}

		return productModelRepository.save(model);
	}

	/**
	 * Aggiorna le informazioni di una variante di prodotto nel database.
	 *
	 * @param productVariation l'oggetto ProductVariation con le nuove informazioni da salvare
	 * @return l'oggetto ProductVariation aggiornato
	 */
	@Override
	public ProductVariation updateVariation(ProductVariation productVariation) {
		if (productVariation.getId() == null)
			throw new IllegalArgumentException("Impossibile modificare un oggetto senza id per la classe ProductVariation");

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
		checkProductModel(model);

		if (model.getImageFile() != null)
			deleteImage(model.getImageFile());

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
		checkProductModel(model);
		checkProductVariation(variation);

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

	// ================================================================================================================
	// =============== OTHER ===========================================================================================
	// ================================================================================================================


	/**
	 * Controlla se il modello di prodotto specificato è valido.
	 *
	 * @param model l'oggetto ProductModel da controllare
	 * @throws IllegalArgumentException se il modello di prodotto specificato è null
	 */
	private static void checkProductModel(ProductModel model) {
		if (model == null)
			throw new IllegalArgumentException("Il modello specificato non è valido");
	}

	/**
	 * Controlla se la variante di prodotto specificata è valida.
	 *
	 * @param variation l'oggetto ProductVariation da controllare
	 * @throws IllegalArgumentException se la variante di prodotto specificata è null
	 */
	private static void checkProductVariation(ProductVariation variation) {
		if (variation == null)
			throw new IllegalArgumentException("La variante specificata non è valida");
	}

	/**
	 * Controlla che i valori del modello di prodotto siano validi.
	 *
	 * @param productModel l'oggetto ProductModel da controllare
	 * @throws ErrorInField se uno dei valori non è valido
	 * @throws MissingRequiredField se uno dei valori richiesti è mancante
	 */
	private static void checkProductModelValues(ProductModel productModel) throws ErrorInField, MissingRequiredField {
		if (productModel.getName().isEmpty()
			|| productModel.getBrand().isEmpty()
			|| productModel.getCategory().isEmpty()){
			throw new MissingRequiredField();
		}

		if (productModel.getName().length() < ProductFilters.MIN_STRING_LENGTH || productModel.getName().length() > ProductFilters.MAX_STRING_LENGTH)
			throw new ErrorInField("Il nome del modello deve essere lungo tra i " + ProductFilters.MIN_STRING_LENGTH + " e i " + ProductFilters.MAX_STRING_LENGTH + " caratteri");

		if (productModel.getBrand().length() < ProductFilters.MIN_STRING_LENGTH || productModel.getBrand().length() > ProductFilters.MAX_STRING_LENGTH)
			throw new ErrorInField("Il nome del brand deve essere lungo tra i " + ProductFilters.MIN_STRING_LENGTH + " e i " + ProductFilters.MAX_STRING_LENGTH + " caratteri");

		if (!List.of(ProductCategory.ALL_CATEGORIES).contains(productModel.getCategory()))
			throw new ErrorInField("La categoria specificata non è valida");
	}

	private static void checkProductVariationValues(ProductVariation variation) throws ErrorInField, MissingRequiredField {
		if (variation.getState().isEmpty() || variation.getColor().isEmpty()) {
			throw new MissingRequiredField();
		}

		if (variation.getPrice() < ProductFilters.MIN_PRICE || variation.getPrice() > ProductFilters.MAX_PRICE)
			throw new ErrorInField("Il prezzo deve essere compreso tra " + ProductFilters.MIN_PRICE + " e " + ProductFilters.MAX_PRICE);

		if (variation.getYear() < ProductFilters.MIN_YEAR || variation.getYear() > ProductFilters.MAX_YEAR)
			throw new ErrorInField("L'anno deve essere compreso tra " + ProductFilters.MIN_YEAR + " e " + ProductFilters.MAX_YEAR);

		if (variation.getRam() < ProductFilters.MIN_RAM || variation.getRam() > ProductFilters.MAX_RAM)
			throw new ErrorInField("La RAM deve essere compresa tra " + ProductFilters.MIN_RAM + " e " + ProductFilters.MAX_RAM);

		if (variation.getDisplaySize() < ProductFilters.MIN_DISPLAY_SIZE || variation.getDisplaySize() > ProductFilters.MAX_DISPLAY_SIZE)
			throw new ErrorInField("La dimensione dello schermo deve essere compresa tra " + ProductFilters.MIN_DISPLAY_SIZE + " e " + ProductFilters.MAX_DISPLAY_SIZE);

		if (variation.getStorageSize() < ProductFilters.MIN_STORAGE_SIZE || variation.getStorageSize() > ProductFilters.MAX_STORAGE_SIZE)
			throw new ErrorInField("La dimensione dello storage deve essere compresa tra " + ProductFilters.MIN_STORAGE_SIZE + " e " + ProductFilters.MAX_STORAGE_SIZE);

		if (variation.getColor().length() < ProductFilters.MIN_STRING_LENGTH || variation.getColor().length() > ProductFilters.MAX_STRING_LENGTH)
			throw new ErrorInField("Il colore deve essere lungo tra i " + ProductFilters.MIN_STRING_LENGTH + " e i " + ProductFilters.MAX_STRING_LENGTH + " caratteri");

		if (!List.of(ProductState.ALL_STATES).contains(variation.getState()))
			throw new ErrorInField("Lo stato specificato non è valido");
	}

	/**
	 * Esegue il filtraggio dei modelli di prodotto.
	 */
	private List<ProductModel> doFilter(ProductFilters filters, List<ProductModel> allModels) throws ErrorInField {
		if (filters.hasError())
			throw new ErrorInField("I filtri specificati non sono validi");

		List<ProductModel> filteredModels = new ArrayList<>();

		for (ProductModel model : allModels) {
			// If the model does not match the filters, skip it
			if (filters.getName() != null && !model.getName().toLowerCase().contains(filters.getName().toLowerCase()))
				continue;
			if (filters.getBrand() != null && !model.getBrand().toLowerCase().contains(filters.getBrand().toLowerCase()))
				continue;
			if (filters.getCategory() != null && !model.getCategory().toLowerCase().contains(filters.getCategory().toLowerCase()))
				continue;

			boolean hasVariation = false;
			for (ProductVariation variation : model.getVariations()) {
				if (filters.getMinYear() > variation.getYear() || filters.getMaxYear() < variation.getYear())
					continue;
				if (filters.getMinRam() > variation.getRam() || filters.getMaxRam() < variation.getRam())
					continue;
				if (filters.getMinDisplaySize() > variation.getDisplaySize() || filters.getMaxDisplaySize() < variation.getDisplaySize())
					continue;
				if (filters.getMinStorageSize() > variation.getStorageSize() || filters.getMaxStorageSize() < variation.getStorageSize())
					continue;
				if (filters.getMinPrice() > variation.getPrice() || filters.getMaxPrice() < variation.getPrice())
					continue;
				if (filters.getColor() != null && !variation.getColor().toLowerCase().contains(filters.getColor().toLowerCase()))
					continue;
				if (filters.getState() != null && !variation.getState().toLowerCase().contains(filters.getState().toLowerCase()))
					continue;

				hasVariation = true;
				break;
			}

			if (hasVariation)
				filteredModels.add(model);
		}
		return filteredModels;
	}
}
