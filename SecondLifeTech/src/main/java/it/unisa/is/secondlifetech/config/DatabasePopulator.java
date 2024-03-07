package it.unisa.is.secondlifetech.config;

import it.unisa.is.secondlifetech.entity.ImageFile;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.repository.ProductModelRepository;
import it.unisa.is.secondlifetech.repository.ProductVariationRepository;
import it.unisa.is.secondlifetech.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

	public static ImageFile createFromFile(String filePath) throws IOException {
		File file = new File(filePath);

		String name = file.getName();
		String contentType = Files.probeContentType(file.toPath());
		byte[] data = Files.readAllBytes(file.toPath());

		return ImageFile.builder()
			.name(name)
			.contentType(contentType)
			.data(data)
			.build();
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

			model = new ProductModel("iPhone 11", "Apple", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2019,3,6.1,128,340,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.1,128,340,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.1,128,340,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.1,128,350,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.1,128,350,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.1,128,360,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.1,128,360,10,"nero",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.1,128,360,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.1,256,350,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.1,256,350,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.1,256,370,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.1,256,389,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.1,256,389,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
			}

			model = new ProductModel("iPhone 11 Pro", "Apple", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2019,3,5.7,128,399,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,128,399,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,128,399,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,128,419,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,128,419,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,128,439,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,128,439,10,"nero",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,128,439,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,256,459,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,256,459,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,256,469,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,256,479,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,5.7,256,479,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.7,128,479,10,"nero",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.7,128,479,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.7,256,450,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.7,256,450,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.7,256,460,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.7,256,479,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,3,6.7,512,499,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);

			}


			model = new ProductModel("iPhone 12", "Apple", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2020,3,6.1,128,440,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,440,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,440,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,450,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,450,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,460,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,460,10,"nero",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,460,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,256,450,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,256,450,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,256,470,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,256,489,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,256,489,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,128,429,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,128,429,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,128,439,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,128,439,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,128,439,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,256,459,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,256,439,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
			}

			model = new ProductModel("iPhone 12 Pro", "Apple", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2020, 6, 6.1, 128, 470, 10, "nero", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 470, 10, "bianco", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 470, 10, "blu", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 480, 10, "nero", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 480, 10, "bianco", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 480, 10, "blu", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 256, 520, 10, "nero", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 490, 10, "bianco", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 490, 10, "blu", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 570, 10, "nero", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 570, 10, "bianco", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 570, 10, "blu", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 580, 10, "nero", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 580, 10, "bianco", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 256, 599, 10, "blu", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 590, 10, "nero", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 590, 10, "bianco", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 256, 619, 10, "blu", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 512, 659, 10, "blu", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 512, 670, 10, "nero", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 512, 670, 10, "bianco", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 512, 670, 10, "blu", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
			}

			model = new ProductModel("iPhone 13", "Apple", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2020,3,6.1,128,540,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,540,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,540,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,550,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,550,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,560,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,560,10,"nero",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,128,560,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,256,550,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,256,550,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,256,570,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,256,589,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,6.1,256,659,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,128,529,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,128,529,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,128,539,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,128,539,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,128,539,10,"oro",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,256,639,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2020,4,5.4,256,639,10,"oro",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
			}

			model = new ProductModel("iPhone 13 Pro", "Apple", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2020, 6, 6.1, 128, 670, 10, "nero", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 670, 10, "bianco", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 670, 10, "blu", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 680, 10, "nero", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 680, 10, "bianco", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 680, 10, "blu", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 256, 720, 10, "nero", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 690, 10, "bianco", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.1, 128, 690, 10, "blu", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 770, 10, "nero", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 770, 10, "bianco", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 770, 10, "blu", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 780, 10, "nero", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 780, 10, "bianco", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 256, 799, 10, "blu", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 790, 10, "nero", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 128, 790, 10, "bianco", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 256, 819, 10, "blu", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 512, 859, 10, "blu", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 512, 870, 10, "nero", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 512, 870, 10, "bianco", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 6, 6.7, 512, 870, 10, "blu", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
			}


			model = new ProductModel("Xiaomi 11", "Xiaomi", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2019,6,6.1,128,340,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,340,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,340,10,"blu",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,350,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,350,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,360,10,"blu",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,360,10,"nero",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,360,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,256,350,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,256,350,10,"blu",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,256,370,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,256,389,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,256,389,10,"blu",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
			}

			model = new ProductModel("Xiaomi 12", "Xiaomi", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2019,8,6.1,128,540,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,8,6.1,128,540,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,8,6.1,128,540,10,"blu",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,8,6.1,128,550,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,8,6.1,128,550,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,8,6.1,128,560,10,"blu",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,8,6.1,128,560,10,"nero",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,8,6.1,128,560,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,12,6.1,256,650,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,12,6.1,256,650,10,"blu",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,12,6.1,256,670,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,12,6.1,256,689,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,12,6.1,256,689,10,"blu",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
			}

			model = new ProductModel("Pixel 6a", "Google", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2019,6,6.1,128,340,10,"nero",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,340,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,340,10,"verde",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,350,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,350,10,"bianco",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,360,10,"verde",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,360,10,"nero",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,128,360,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,256,350,10,"bianco",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,256,350,10,"verde",ProductState.ACCETTABILE,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,256,370,10,"nero",ProductState.BUONO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,256,389,10,"bianco",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
				variation = new ProductVariation(2019,6,6.1,256,389,10,"verde",ProductState.OTTIMO,model);
				productService.createNewVariation(model,variation);
			}

			model = new ProductModel("Pixel 6", "Google", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2020, 8, 6.1, 128, 470, 10, "nero", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 470, 10, "bianco", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 470, 10, "rosso", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 480, 10, "nero", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 480, 10, "bianco", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 480, 10, "rosso", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.1, 256, 520, 10, "nero", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 490, 10, "bianco", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 490, 10, "rosso", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
			}

			model = new ProductModel("Pixel 6 Pro", "Google", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2020, 12, 6.7, 128, 570, 10, "nero", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 128, 570, 10, "bianco", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 128, 570, 10, "verde", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 128, 580, 10, "nero", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 128, 580, 10, "bianco", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 256, 599, 10, "oro", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 128, 590, 10, "nero", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 128, 590, 10, "bianco", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 256, 619, 10, "verde", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 512, 659, 10, "verde", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 512, 670, 10, "nero", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 512, 670, 10, "bianco", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 512, 670, 10, "oro", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
			}


			model = new ProductModel("Pixel 7", "Google", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2020, 8, 6.1, 128, 570, 10, "nero", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 570, 10, "bianco", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 570, 10, "verde", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 580, 10, "nero", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 580, 10, "bianco", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 580, 10, "verde", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.1, 256, 620, 10, "nero", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 690, 10, "bianco", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 8, 6.1, 128, 690, 10, "verde", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
			}
			model = new ProductModel("Pixel 7 Pro", "Google", ProductCategory.SMARTPHONE);
			if (!modelRepository.existsByName(model.getName())) {
				productService.createNewModel(model);
				ProductVariation variation = new ProductVariation(2020, 12, 6.7, 128, 770, 10, "nero", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 128, 770, 10, "bianco", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 128, 770, 10, "blu", ProductState.ACCETTABILE, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 128, 780, 10, "nero", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 128, 780, 10, "bianco", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 256, 799, 10, "blu", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 128, 790, 10, "nero", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 128, 790, 10, "bianco", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 256, 819, 10, "blu", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 512, 859, 10, "blu", ProductState.BUONO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 512, 870, 10, "nero", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 512, 870, 10, "bianco", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
				variation = new ProductVariation(2020, 12, 6.7, 512, 870, 10, "blu", ProductState.OTTIMO, model);
				productService.createNewVariation(model, variation);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}