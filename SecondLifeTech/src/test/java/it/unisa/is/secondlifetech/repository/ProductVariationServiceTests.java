package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductVariationServiceTests {
	@Autowired
	private ProductVariationRepository productVariationService;
	@Autowired
	private ProductModelRepository productModelRepository;

	@Test
	void ProductVariation_FindByState_ReturnCorrectList() {
		// Arrange
		ProductModel productModel = new ProductModel(
			"iPhone 11",
			"Apple",
			ProductCategory.SMARTPHONE
		);

		productModelRepository.save(productModel);

		ProductVariation productVariation1 = new ProductVariation(
			2020,
			4,
			6.0,
			128,
			250.0,
			3,
			"Green",
			ProductState.ACCETTABILE,
			productModel
		);

		ProductVariation productVariation2 = new ProductVariation(
			2018,
			4,
			5.5,
			64,
			190.0,
			6,
			"Red",
			ProductState.ACCETTABILE,
			productModel
		);

		ProductVariation productVariation3 = new ProductVariation(
			2019,
			4,
			5.9,
			128,
			230.0,
			2,
			"Blue",
			ProductState.BUONO,
			productModel
		);

		productVariationService.save(productVariation1);
		productVariationService.save(productVariation2);
		productVariationService.save(productVariation3);

		// Act
		List<ProductVariation> foundVariations = productVariationService.findByState(ProductState.ACCETTABILE);

		// Assert
		assertThat(foundVariations).isNotNull();
		assertThat(foundVariations.size()).isEqualTo(2);
		assertThat(foundVariations).containsOnly(productVariation1, productVariation2);
		assertThat(foundVariations).doesNotContain(productVariation3);
		assertThat(foundVariations.get(0).getProductModel()).isEqualTo(productModel);
	}

	@Test
	void ProductVariation_FindByProductModelId_ReturnCorrectList() {
		// Arrange
		ProductModel productModel1 = new ProductModel(
			"iPhone 11",
			"Apple",
			ProductCategory.SMARTPHONE
		);

		ProductModel productModel2 = new ProductModel(
			"S24",
			"Samsung",
			ProductCategory.SMARTPHONE
		);

		productModelRepository.save(productModel1);
		productModelRepository.save(productModel2);

		ProductVariation productVariation1 = new ProductVariation(
			2020,
			4,
			6.0,
			128,
			250.0,
			3,
			"Green",
			ProductState.ACCETTABILE,
			productModel1
		);

		ProductVariation productVariation2 = new ProductVariation(
			2018,
			4,
			5.5,
			64,
			190.0,
			6,
			"Red",
			ProductState.ACCETTABILE,
			productModel1
		);

		ProductVariation productVariation3 = new ProductVariation(
			2019,
			4,
			5.9,
			128,
			230.0,
			2,
			"Blue",
			ProductState.BUONO,
			productModel2
		);

		productVariationService.save(productVariation1);
		productVariationService.save(productVariation2);
		productVariationService.save(productVariation3);

		// Act
		List<ProductVariation> foundVariations = productVariationService.findByProductModelId(productModel1.getId());

		// Assert
		assertThat(foundVariations).isNotNull();
		assertThat(foundVariations.size()).isEqualTo(2);
		assertThat(foundVariations).containsOnly(productVariation1, productVariation2);
		assertThat(foundVariations).doesNotContain(productVariation3);
		assertThat(foundVariations.get(0).getProductModel()).isEqualTo(productModel1);
	}
}