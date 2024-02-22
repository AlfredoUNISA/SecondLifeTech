package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.OrderItem;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.repository.OrderItemRepository;
import it.unisa.is.secondlifetech.repository.ProductModelRepository;
import it.unisa.is.secondlifetech.repository.ProductVariationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTests {

	@Mock
	private ProductModelRepository productModelRepository;

	@Mock
	private ProductVariationRepository productVariationRepository;

	@Mock
	private OrderItemRepository orderItemRepository;

	@InjectMocks
	private ProductServiceImpl productModelService;

	private ProductModel productModel;
	private ProductVariation productVariation;
	private OrderItem orderItem;

	@BeforeEach
	void setUp() {
		productModel = ProductModel.builder()
			.id(UUID.randomUUID())
			.variations(new ArrayList<>())
			.brand("Brand")
			.name("Name")
			.category(ProductCategory.SMARTPHONE)
			.build();

		productVariation = ProductVariation.builder()
			.id(UUID.randomUUID())
			.state(ProductState.OTTIMO)
			.ram(4)
			.storageSize(64)
			.year(2020)
			.color("Black")
			.model(productModel)
			.build();
		productModel.addVariation(productVariation);

		orderItem = OrderItem.builder()
			.id(UUID.randomUUID())
			.quantityOrdered(1)
			.subTotal(1000)
			.productVariation(productVariation)
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

	@Test
	void ProductModelService_DeleteVariation__WhenNotInOrders_ShouldRemoveVariationFromModelAndDatabase() {
		// Arrange
		UUID modelId = productModel.getId();
		UUID variationId = productVariation.getId();

		when(orderItemRepository.findAll()).thenReturn(Collections.emptyList());
		when(productModelRepository.findById(modelId)).thenReturn(Optional.of(productModel));
		when(productVariationRepository.findById(variationId)).thenReturn(Optional.of(productVariation));

		// Act
		productModelService.deleteVariation(modelId, variationId);

		// Assert
		verify(productModelRepository).save(productModel);
		verify(productVariationRepository).deleteById(variationId);
		assertThat(productModel.getVariations()).doesNotContain(productVariation);
	}

	@Test
	void ProductModelService_DeleteVariation__WhenIsInOrder_ShouldRemoveVariationFromModelAndDatabase() {
		// Arrange
		UUID modelId = productModel.getId();
		UUID variationId = productVariation.getId();

		when(orderItemRepository.findAll()).thenReturn(List.of(orderItem));

		// Act
		productModelService.deleteVariation(modelId, variationId);

		// Assert
		assertThat(orderItem.getBrand()).isEqualTo(productModel.getBrand());
		assertThat(orderItem.getModelName()).isEqualTo(productModel.getName());
		assertThat(orderItem.getCategory()).isEqualTo(productModel.getCategory());
		assertThat(orderItem.getState()).isEqualTo(productVariation.getState());
		assertThat(orderItem.getColor()).isEqualTo(productVariation.getColor());
		assertThat(orderItem.getDisplaySize()).isEqualTo(productVariation.getDisplaySize());
		assertThat(orderItem.getStorageSize()).isEqualTo(productVariation.getStorageSize());
		assertThat(orderItem.getRam()).isEqualTo(productVariation.getRam());
		assertThat(orderItem.getYear()).isEqualTo(productVariation.getYear());

		assertThat(orderItem.getProductVariation()).isNull();

		verify(orderItemRepository, times(1)).save(orderItem);
		verify(productModelRepository, times(1)).save(productModel);
		verify(productVariationRepository, times(1)).deleteById(variationId);
		assertThat(productModel.getVariations()).doesNotContain(productVariation);
	}

}