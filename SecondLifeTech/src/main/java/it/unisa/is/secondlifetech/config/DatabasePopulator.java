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
				ProductVariation variation = new ProductVariation(2017,2,4.7,128,200,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,4.7,128,200,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,4.7,128,200,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,4.7,128,220,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,4.7,128,220,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,4.7,128,220,10,"oro",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,4.7,256,230,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,4.7,256,230,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,4.7,256,240,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,4.7,256,240,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,4.7,256,240,10,"oro",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
			}

			model = new ProductModel("iPhone 7 Plus", "Apple", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2017,2,5.5,128,250,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,5.5,128,250,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,5.5,128,250,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,5.5,128,250,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,5.5,128,250,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,5.5,128,250,10,"oro",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,5.5,256,260,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,5.5,256,260,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,5.5,256,260,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,5.5,256,260,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2017,2,5.5,256,260,10,"oro",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
			}

			model = new ProductModel("iPhone 8", "Apple", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2018,2,4.7,128,210,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,4.7,128,210,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,4.7,128,210,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,4.7,128,230,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,4.7,128,230,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,4.7,128,230,10,"oro",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,4.7,256,240,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,4.7,256,240,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,4.7,256,250,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,4.7,256,250,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,4.7,256,250,10,"oro",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
			}

			model = new ProductModel("iPhone 8 Plus", "Apple", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2018,2,5.5,128,270,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,5.5,128,270,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,5.5,128,270,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,5.5,128,280,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,5.5,128,280,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,5.5,128,280,10,"oro",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,5.5,256,280,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,5.5,256,280,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,5.5,256,290,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,5.5,256,290,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,2,5.5,256,290,10,"oro",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
			}

			model = new ProductModel("iPhone X", "Apple", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2018,3,5.7,128,290,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,3,5.7,128,290,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,3,5.7,128,290,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,3,5.7,128,300,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,3,5.7,128,300,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,3,5.7,128,300,10,"oro",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,3,5.7,256,310,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,3,5.7,256,310,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,3,5.7,256,320,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,3,5.7,256,330,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2018,3,5.7,256,330,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
			}

			model = new ProductModel("iPhone XS", "Apple", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2019,3,5.7,128,310,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,128,310,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,128,310,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,128,320,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,128,320,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,128,320,10,"oro",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,256,330,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,256,330,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,256,330,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,256,340,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,256,340,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.5,128,330,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.5,128,330,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.5,128,330,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.5,128,340,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.5,128,340,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.5,128,340,10,"oro",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.5,256,350,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.5,256,350,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.5,256,355,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.5,256,360,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.5,256,360,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
			}




		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}