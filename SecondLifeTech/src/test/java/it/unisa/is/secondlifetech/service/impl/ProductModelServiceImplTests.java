package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.repository.ProductModelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductModelServiceImplTests {

	@Mock
	private ProductModelRepository productModelRepository;

	@InjectMocks
	private ProductModelServiceImpl productModelService;

	@Test
	void ProductModelService_FindProductModelByName_ReturnCorrectProductModel() {
		// Arrange
		ProductModel productModel1 = new ProductModel(
			"iPhone 11",
			"Apple",
			ProductCategory.SMARTPHONE
		);

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

		ProductModel productModel2 = new ProductModel(
			"iPhone 12",
			"Apple",
			ProductCategory.SMARTPHONE
		);

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

		ProductModel productModel3 = new ProductModel(
			"Galaxy Tab S7",
			"Samsung",
			ProductCategory.TABLET
		);

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

		when(productModelRepository.findByName(productModel1.getName())).thenReturn(productModel1);

		// Act
		ProductModel foundProductModel = productModelService.findProductModelByName(productModel1.getName());

		// Assert
		assertThat(foundProductModel).isNotNull();
		assertThat(foundProductModel).isEqualTo(productModel1);
	}

	@Test
	void ProductModelService_FindProductModelsByBrand_ReturnCorrectList() {
		// Arrange
		ProductModel productModel1 = new ProductModel(
			"iPhone 11",
			"Apple",
			ProductCategory.SMARTPHONE
		);

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

		ProductModel productModel2 = new ProductModel(
			"iPhone 12",
			"Apple",
			ProductCategory.SMARTPHONE
		);

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

		ProductModel productModel3 = new ProductModel(
			"Galaxy Tab S7",
			"Samsung",
			ProductCategory.TABLET
		);

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

		when(productModelRepository.findByBrand(productModel1.getBrand())).thenReturn(List.of(productModel1, productModel2));

		// Act
		List<ProductModel> foundProductModels = productModelService.findProductModelsByBrand(productModel1.getBrand());

		// Assert
		assertThat(foundProductModels).isNotNull();
		assertThat(foundProductModels.size()).isEqualTo(2);
		assertThat(foundProductModels).containsOnly(productModel1, productModel2);
		assertThat(foundProductModels).doesNotContain(productModel3);

	}

	@Test
	void ProductModelService_FindProductModelsByCategory_ReturnCorrectList() {
		// Arrange
		ProductModel productModel1 = new ProductModel(
			"iPhone 11",
			"Apple",
			ProductCategory.SMARTPHONE
		);

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

		ProductModel productModel2 = new ProductModel(
			"iPhone 12",
			"Apple",
			ProductCategory.SMARTPHONE
		);

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

		ProductModel productModel3 = new ProductModel(
			"Galaxy Tab S7",
			"Samsung",
			ProductCategory.TABLET
		);

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

		when(productModelRepository.findByCategory(productModel1.getCategory())).thenReturn(List.of(productModel1, productModel2));

		// Act
		List<ProductModel> foundProductModels = productModelService.findProductModelsByCategory(productModel1.getCategory());

		// Assert
		assertThat(foundProductModels).isNotNull();
		assertThat(foundProductModels.size()).isEqualTo(2);
		assertThat(foundProductModels).containsOnly(productModel1, productModel2);
		assertThat(foundProductModels).doesNotContain(productModel3);
	}
}