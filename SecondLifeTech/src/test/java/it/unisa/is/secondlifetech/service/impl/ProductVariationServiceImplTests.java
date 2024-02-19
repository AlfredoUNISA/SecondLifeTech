package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.repository.ProductVariationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductVariationServiceImplTests {

	@Mock
	private ProductVariationService productVariationService;

	@InjectMocks
	private ProductVariationServiceImpl productVariationService;

	@Test
	void ProductVariationService_FindProductVariationsByState_ReturnCorrectList() {
		// Arrange
		ProductModel productModel = new ProductModel(
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

		when(productVariationService.findByState(ProductState.ACCETTABILE)).thenReturn(List.of(productVariation1, productVariation2));

		// Act
		List<ProductVariation> foundVariations = productVariationService.findProductVariationsByState(ProductState.ACCETTABILE);

		// Assert
		assertThat(foundVariations).isNotNull();
		assertThat(foundVariations.size()).isEqualTo(2);
		assertThat(foundVariations).containsOnly(productVariation1, productVariation2);
		assertThat(foundVariations).doesNotContain(productVariation3);
		assertThat(foundVariations.get(0).getProductModel()).isEqualTo(productModel);
	}

	@Test
	void ProductVariationService_FindProductVariationsByProductModelId_ReturnCorrectList() {
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

		when(productVariationService.findByProductModelId(productModel1.getId())).thenReturn(List.of(productVariation1, productVariation2));

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