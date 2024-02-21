package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.repository.ProductModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTests {

	@Mock
	private ProductModelRepository productModelRepository;

	@InjectMocks
	private ProductServiceImpl productModelService;

	private ProductModel productModel;

	@BeforeEach
	void setUp() {
		productModel = ProductModel.builder()
			.id(UUID.randomUUID())
			.variations(new ArrayList<>())
			.brand("Brand")
			.category(ProductCategory.SMARTPHONE)
			.build();
	}

	@Test
	void ProductModelService_FindProductModelByName_ShouldReturnCorrectProductModel() {
		// Arrange
		String name = productModel.getName();

		when(productModelRepository.findByName(name)).thenReturn(productModel);

		// Act
		ProductModel result = productModelService.findModelByName(name);

		// Assert
		assertThat(result).isEqualTo(productModel);
	}

	@Test
	void ProductModelService_FindProductModelsByBrand_ShouldReturnCorrectList() {
		// Arrange
		String brand = productModel.getBrand();

		when(productModelRepository.findByBrand(brand)).thenReturn(List.of(productModel));

		// Act
		List<ProductModel> results = productModelService.findModelsByBrand(brand);

		// Assert
		assertThat(results).hasSize(1);
		assertThat(results).containsOnly(productModel);
	}

	@Test
	void ProductModelService_FindProductModelsByCategory_ShouldReturnCorrectList() {
		// Arrange
		String category = productModel.getCategory();

		when(productModelRepository.findByCategory(category)).thenReturn(List.of(productModel));

		// Act
		List<ProductModel> results = productModelService.findModelsByCategory(category);

		// Assert
		assertThat(results).hasSize(1);
		assertThat(results).containsOnly(productModel);
	}
}