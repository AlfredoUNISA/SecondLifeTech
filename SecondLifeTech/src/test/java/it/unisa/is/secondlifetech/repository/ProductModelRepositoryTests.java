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
class ProductModelRepositoryTests {
	@Autowired
	private ProductModelRepository productModelRepository;
	@Autowired
	private ProductVariationRepository productVariationRepository;

	@Test
	void ProductModelRepository_FindByName_ReturnCorrectProductModel() {
		// Arrange
		ProductModel productModel1 = new ProductModel(
			"iPhone 11",
			"Apple",
			ProductCategory.SMARTPHONE
		);
		productModelRepository.save(productModel1);

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
		productVariationRepository.save(productVariation1);

		ProductModel productModel2 = new ProductModel(
			"iPhone 12",
			"Apple",
			ProductCategory.SMARTPHONE
		);

		productModelRepository.save(productModel2);

		ProductVariation productVariation2 = new ProductVariation(
			2021,
			4,
			6.1,
			128,
			300.0,
			3,
			"Blue",
			ProductState.ACCETTABILE,
			productModel2
		);
		productVariationRepository.save(productVariation2);

		ProductModel productModel3 = new ProductModel(
			"Galaxy Tab S7",
			"Samsung",
			ProductCategory.TABLET
		);

		productModelRepository.save(productModel3);

		ProductVariation productVariation3 = new ProductVariation(
			2020,
			4,
			11.0,
			256,
			700.0,
			3,
			"Black",
			ProductState.BUONO,
			productModel3
		);
		productVariationRepository.save(productVariation2);

		// Act
		ProductModel foundProductModel = productModelRepository.findByName(productModel1.getName());
		ProductVariation foundProductVariation = productVariationRepository.findByProductModelId(foundProductModel.getId()).get(0);

		// Assert
		assertThat(foundProductModel).isNotNull();
		assertThat(foundProductModel).isEqualTo(productModel1);

		assertThat(foundProductVariation).isNotNull();
		assertThat(foundProductVariation).isEqualTo(productVariation1);
	}

	@Test
	void ProductModelRepository_FindByBrand_ReturnCorrectList() {
		// Arrange
		ProductModel productModel1 = new ProductModel(
			"iPhone 11",
			"Apple",
			ProductCategory.SMARTPHONE
		);
		productModelRepository.save(productModel1);

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
		productVariationRepository.save(productVariation1);

		ProductModel productModel2 = new ProductModel(
			"iPhone 12",
			"Apple",
			ProductCategory.SMARTPHONE
		);

		productModelRepository.save(productModel2);

		ProductVariation productVariation2 = new ProductVariation(
			2021,
			4,
			6.1,
			128,
			300.0,
			3,
			"Blue",
			ProductState.ACCETTABILE,
			productModel2
		);
		productVariationRepository.save(productVariation2);

		ProductModel productModel3 = new ProductModel(
			"Galaxy Tab S7",
			"Samsung",
			ProductCategory.TABLET
		);

		productModelRepository.save(productModel3);

		ProductVariation productVariation3 = new ProductVariation(
			2020,
			4,
			11.0,
			256,
			700.0,
			3,
			"Black",
			ProductState.BUONO,
			productModel3
		);
		productVariationRepository.save(productVariation2);

		// Act
		List<ProductModel> foundProductModels = productModelRepository.findByBrand(productModel1.getBrand());

		// get the product model from the list with the name "iphone 11"
		ProductModel foundProductModel = foundProductModels.stream().filter(productModel -> productModel.getName().equals("iPhone 11")).findFirst().orElse(null);

		assert foundProductModel != null;
		ProductVariation foundProductVariation = productVariationRepository.findByProductModelId(foundProductModel.getId()).get(0);

		// Assert
		assertThat(foundProductModels).isNotNull();
		assertThat(foundProductModels.size()).isEqualTo(2);
		assertThat(foundProductModels).containsOnly(productModel1, productModel2);
		assertThat(foundProductModels).doesNotContain(productModel3);

		assertThat(foundProductVariation).isNotNull();
		assertThat(foundProductVariation).isEqualTo(productVariation1);
	}

	@Test
	void ProductModelRepository_FindByCategory_ReturnCorrectList() {
		// Arrange
		ProductModel productModel1 = new ProductModel(
			"iPhone 11",
			"Apple",
			ProductCategory.SMARTPHONE
		);
		productModelRepository.save(productModel1);

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
		productVariationRepository.save(productVariation1);

		ProductModel productModel2 = new ProductModel(
			"iPhone 12",
			"Apple",
			ProductCategory.SMARTPHONE
		);

		productModelRepository.save(productModel2);

		ProductVariation productVariation2 = new ProductVariation(
			2021,
			4,
			6.1,
			128,
			300.0,
			3,
			"Blue",
			ProductState.ACCETTABILE,
			productModel2
		);
		productVariationRepository.save(productVariation2);

		ProductModel productModel3 = new ProductModel(
			"Galaxy Tab S7",
			"Samsung",
			ProductCategory.TABLET
		);

		productModelRepository.save(productModel3);

		ProductVariation productVariation3 = new ProductVariation(
			2020,
			4,
			11.0,
			256,
			700.0,
			3,
			"Black",
			ProductState.BUONO,
			productModel3
		);
		productVariationRepository.save(productVariation2);

		// Act
		List<ProductModel> foundProductModels = productModelRepository.findByCategory(productModel1.getCategory());

		// get the product model from the list with the name "iphone 11"
		ProductModel foundProductModel = foundProductModels.stream().filter(productModel -> productModel.getName().equals("iPhone 11")).findFirst().orElse(null);

		assert foundProductModel != null;
		ProductVariation foundProductVariation = productVariationRepository.findByProductModelId(foundProductModel.getId()).get(0);

		// Assert
		assertThat(foundProductModels).isNotNull();
		assertThat(foundProductModels.size()).isEqualTo(2);
		assertThat(foundProductModels).containsOnly(productModel1, productModel2);
		assertThat(foundProductModels).doesNotContain(productModel3);

		assertThat(foundProductVariation).isNotNull();
		assertThat(foundProductVariation).isEqualTo(productVariation1);

		assertThat(productModelRepository.findByName("blabla")).isNull();
	}
}