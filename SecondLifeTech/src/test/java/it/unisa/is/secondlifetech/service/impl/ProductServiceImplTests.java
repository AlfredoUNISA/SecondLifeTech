package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.OrderItem;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.repository.OrderItemRepository;
import it.unisa.is.secondlifetech.repository.ProductModelRepository;
import it.unisa.is.secondlifetech.repository.ProductVariationRepository;
import it.unisa.is.secondlifetech.service.OrderService;
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
	private OrderService orderService;

	@Mock
	private OrderItemRepository orderItemRepository;

	@InjectMocks
	private ProductServiceImpl productService;

	private ProductModel productModel;
	private ProductVariation productVariation;

	@BeforeEach
	void setup() {
		productModel = new ProductModel();
		productModel.setId(UUID.randomUUID());

		productVariation = new ProductVariation();
		productVariation.setId(UUID.randomUUID());
		productVariation.setModel(productModel);
	}



	// ================================================================================================================
	// =============== CREATE ==========================================================================================
	// ================================================================================================================

	@Test
	void ProductServiceImpl_CreateNewModel_ShouldReturnCreatedModel() {
		// Arrange
		when(productModelRepository.save(any(ProductModel.class))).thenReturn(productModel);

		// Act
		ProductModel createdModel = productService.createNewModel(productModel);

		// Assert
		assertThat(createdModel).isEqualTo(productModel);
		verify(productModelRepository).save(productModel);
	}

	@Test
	void ProductServiceImpl_CreateNewVariation_ShouldReturnCreatedVariation() {
		// Arrange
		when(productVariationRepository.save(any(ProductVariation.class))).thenReturn(productVariation);
		when(productModelRepository.save(any(ProductModel.class))).thenReturn(productModel);

		// Act
		ProductVariation createdVariation = productService.createNewVariation(productModel, productVariation);

		// Assert
		assertThat(createdVariation).isEqualTo(productVariation);
		verify(productVariationRepository).save(productVariation);
		verify(productModelRepository).save(productModel);
	}



	// ================================================================================================================
	// =============== READ ============================================================================================
	// ================================================================================================================

	@Test
	void ProductServiceImpl_FindModelById_ShouldReturnProductModel() {
		// Arrange
		when(productModelRepository.findById(productModel.getId())).thenReturn(Optional.of(productModel));

		// Act
		ProductModel foundModel = productService.findModelById(productModel.getId());

		// Assert
		assertThat(foundModel).isEqualTo(productModel);
	}

	@Test
	void ProductServiceImpl_FindVariationById_ShouldReturnProductVariation() {
		// Arrange
		when(productVariationRepository.findById(productVariation.getId())).thenReturn(Optional.of(productVariation));

		// Act
		ProductVariation foundVariation = productService.findVariationById(productVariation.getId());

		// Assert
		assertThat(foundVariation).isEqualTo(productVariation);
	}

	@Test
	void ProductServiceImpl_FindModelByName_ShouldReturnProductModel() {
		// Arrange
		String name = "Test Model";
		productModel.setName(name);
		when(productModelRepository.findByName(name)).thenReturn(productModel);

		// Act
		ProductModel foundModel = productService.findModelByName(name);

		// Assert
		assertThat(foundModel).isEqualTo(productModel);
	}

	@Test
	void ProductServiceImpl_FindModelsByBrand_ShouldReturnListOfProductModels() {
		// Arrange
		String brand = "Test Brand";
		List<ProductModel> models = Collections.singletonList(productModel);
		when(productModelRepository.findByBrand(brand)).thenReturn(models);

		// Act
		List<ProductModel> foundModels = productService.findModelsByBrand(brand);

		// Assert
		assertThat(foundModels).isEqualTo(models);
	}

	@Test
	void ProductServiceImpl_FindModelsByCategory_ShouldReturnListOfProductModels() {
		// Arrange
		String category = "Test Category";
		List<ProductModel> models = Collections.singletonList(productModel);
		when(productModelRepository.findByCategory(category)).thenReturn(models);

		// Act
		List<ProductModel> foundModels = productService.findModelsByCategory(category);

		// Assert
		assertThat(foundModels).isEqualTo(models);
	}

	@Test
	void ProductServiceImpl_FindVariationsByState_ShouldReturnListOfProductVariations() {
		// Arrange
		String state = "Test State";
		List<ProductVariation> variations = Collections.singletonList(productVariation);
		when(productVariationRepository.findByState(state)).thenReturn(variations);

		// Act
		List<ProductVariation> foundVariations = productService.findVariationsByState(state);

		// Assert
		assertThat(foundVariations).isEqualTo(variations);
	}

	@Test
	void ProductServiceImpl_FindAllModels_ShouldReturnListOfProductModels() {
		// Arrange
		List<ProductModel> models = Collections.singletonList(productModel);
		when(productModelRepository.findAll()).thenReturn(models);

		// Act
		List<ProductModel> foundModels = productService.findAllModels();

		// Assert
		assertThat(foundModels).isEqualTo(models);
	}

	@Test
	void ProductServiceImpl_FindAllVariations_ShouldReturnListOfProductVariations() {
		// Arrange
		List<ProductVariation> variations = Collections.singletonList(productVariation);
		when(productVariationRepository.findAll()).thenReturn(variations);

		// Act
		List<ProductVariation> foundVariations = productService.findAllVariations();

		// Assert
		assertThat(foundVariations).isEqualTo(variations);
	}

	// ================================================================================================================
	// =============== UPDATE ==========================================================================================
	// ================================================================================================================

	@Test
	void ProductServiceImpl_UpdateModel_ShouldUpdateModel() {
		// Arrange
		when(productModelRepository.save(any(ProductModel.class))).thenReturn(productModel);

		// Act
		ProductModel updatedProductModel = productService.updateModel(productModel);

		// Assert
		assertThat(updatedProductModel).isEqualTo(productModel);
		verify(productModelRepository).save(productModel);
	}

	@Test
	void ProductServiceImpl_UpdateVariation_ShouldUpdateVariation() {
		// Arrange
		when(productVariationRepository.save(any(ProductVariation.class))).thenReturn(productVariation);

		// Act
		ProductVariation updatedProductVariation = productService.updateVariation(productVariation);

		// Assert
		assertThat(updatedProductVariation).isEqualTo(productVariation);
		verify(productVariationRepository).save(productVariation);
	}


	// ================================================================================================================
	// =============== DELETE ==========================================================================================
	// ================================================================================================================

	@Test
	void ProductServiceImpl_deleteModel_ShouldDeleteModel() {
		// Arrange
		List<ProductVariation> variations = new ArrayList<>();
		variations.add(productVariation);
		productModel.setVariations(variations);

		// Act
		productService.deleteModel(productModel);

		// Assert
		verify(productModelRepository, times(1)).delete(productModel);
	}

	@Test
	void ProductServiceImpl_deleteVariation_ShouldDeleteVariation() {
		// Arrange
		productVariation.setModel(productModel);
		List<OrderItem> orderItems = new ArrayList<>();
		OrderItem orderItem = new OrderItem();
		orderItems.add(orderItem);
		when(orderService.findOrderItemsByProductVariation(productVariation)).thenReturn(orderItems);

		// Act
		productService.deleteVariation(productVariation);

		// Assert
		verify(orderItemRepository, times(1)).save(orderItem);
		verify(productVariationRepository, times(1)).delete(productVariation);
		verify(productModelRepository, times(1)).save(productModel);
	}

	/*
	@Test
	void ProductModelService_DeleteVariation__WhenIsInOrder_ShouldRemoveVariationFromModelAndDatabase() {
		// Arrange
		UUID modelId = productModel.getId();
		UUID variationId = productVariation.getId();

		when(orderItemRepository.findAll()).thenReturn(List.of(orderItem));

		// Act
		productModelService.deleteVariation(productVariation);

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
	*/

}