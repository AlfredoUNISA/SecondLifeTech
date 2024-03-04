package it.unisa.is.secondlifetech.config;

import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.service.ProductService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabasePopulator {

	private final ProductService service;

	@Autowired
	public DatabasePopulator(ProductService service) {
		this.service = service;
	}

	@PostConstruct
	public void populate() {
		try {
			for (int i = 0; i < 5; i++) {
				if (service.findModelByName("model" + i) == null) {
					String category;

					if (i % 2 == 0) {
						category = ProductCategory.SMARTPHONE;
					} else {
						category = ProductCategory.TABLET;
					}

					ProductModel model = new ProductModel("model" + i, "brand" + i, category);
					service.createNewModel(model);

					for (int j = 0; j < 5; j++) {
						ProductVariation variation = getProductVariation(j, i, model);
						service.createNewVariation(model, variation);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static ProductVariation getProductVariation(int j, int i, ProductModel model) {
		int ram;

		if (j % 2 == 0) {
			ram = 4;
		} else {
			ram = 8;
		}

		double display;

		if (j % 2 == 0) {
			display = 5.5;
		} else {
			display = 6.0;
		}

		int storage;

		if (j % 2 == 0) {
			storage = 64;
		} else {
			storage = 128;
		}

		double price = 100 + i + j;

		String condition;

		if (j % 2 == 0) {
			condition = ProductState.BUONO;
		} else {
			condition = ProductState.OTTIMO;
		}



		return new ProductVariation(
				2020,
				ram,
				display,
				storage,
				price,
				j,
				"color" + j,
				condition,
				model
		);
	}
}