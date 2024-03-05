package it.unisa.is.secondlifetech.config;

import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.repository.ProductModelRepository;
import it.unisa.is.secondlifetech.repository.ProductVariationRepository;
import it.unisa.is.secondlifetech.service.ProductService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabasePopulator {

	private final ProductModelRepository modelRepository;
	private final ProductVariationRepository variationRepository;

	private final ProductService productService;

	@Autowired
	public DatabasePopulator(ProductModelRepository modelRepository, ProductVariationRepository variationRepository, ProductService productService) {
        this.modelRepository = modelRepository;
        this.variationRepository = variationRepository;
        this.productService = productService;
    }

	@PostConstruct
	public void populate() {
		try {

			ProductModel model = new ProductModel("iPhone 7", "Apple", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2008,4,5.1,128,800,10,"nero",ProductState.ACCETTABILE,model);
				variation = new ProductVariation(2008,4,5.1,128,120,10,"nero",ProductState.ACCETTABILE,model);
				variation = new ProductVariation(2020,8,6.1,1200,1200,1,"nero",ProductState.ACCETTABILE,model);
			}

			model = new ProductModel("iPhone 8", "Apple", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2008,4,5.1,128,800,10,"nero",ProductState.ACCETTABILE,model);
				variation = new ProductVariation(2008,4,5.1,128,120,10,"nero",ProductState.ACCETTABILE,model);
				variation = new ProductVariation(2020,8,6.1,1200,1200,1,"nero",ProductState.ACCETTABILE,model);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}