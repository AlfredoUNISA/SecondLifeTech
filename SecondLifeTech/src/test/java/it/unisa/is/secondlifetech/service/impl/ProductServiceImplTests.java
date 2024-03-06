package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.exception.ErrorInField;
import it.unisa.is.secondlifetech.exception.MissingRequiredField;
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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
		productModel.setName("iPhone 6");
		productModel.setBrand("Apple");
		productModel.setCategory(ProductCategory.SMARTPHONE);

		productVariation = new ProductVariation();
		productVariation.setId(UUID.randomUUID());
		productVariation.setYear(2016);
		productVariation.setRam(4);
		productVariation.setDisplaySize(5.1);
		productVariation.setStorageSize(128);
		productVariation.setPrice(129.90);
		productVariation.setQuantityInStock(10);
		productVariation.setColor("Blu");
		productVariation.setState(ProductState.ACCETTABILE);
		productVariation.setModel(productModel);
	}



	// ================================================================================================================
	// =============== TEST CASE 1 =====================================================================================
	// ================================================================================================================

	/**
	 * Deve essere creato un ProductModel con i dati inseriti durante la creazione
	 */
	@Test
	void createNewModel_WhenModelIsValid_ShouldCreateModel() throws ErrorInField, MissingRequiredField {
		// Arrange
		when(productModelRepository.save(any(ProductModel.class))).thenReturn(productModel);

		// Act
		ProductModel createdModel = productService.createNewModel(productModel);

		// Assert
		assertThat(createdModel).isEqualTo(productModel);
		verify(productModelRepository, times(1)).save(productModel);
	}

	@Test
	void createNewModel_WhenModelIsNull_ShouldReturnNull() throws ErrorInField, MissingRequiredField {
		// Arrange
		ProductModel nullModel = null;

		// Act
		ProductModel createdModel = productService.createNewModel(nullModel);

		// Assert
		assertThat(createdModel).isNull();
	}
	
	@Test
	void createNewModel_WhenModelIsInvalid_ShouldThrowErrorInField() {
		// Arrange
		ProductModel invalidModel = new ProductModel();
		invalidModel.setName("InvalidNameWithMoreThan50Characters123456789012345678901234567890");

		// Act and Assert
		assertThrows(ErrorInField.class, () -> productService.createNewModel(invalidModel));
	}

	/**
	 * Test case for creating a new ProductVariation with valid data.
	 * The created ProductVariation should have the same data as the input.
	 */
	@Test
	void createNewVariation_WhenVariationIsValid_ShouldCreateVariation() throws ErrorInField, MissingRequiredField {
		// Arrange
		when(productVariationRepository.save(any(ProductVariation.class))).thenReturn(productVariation);

		// Act
		ProductVariation createdVariation = productService.createNewVariation(productModel, productVariation);

		// Assert
		assertThat(createdVariation).isEqualTo(productVariation);
		verify(productVariationRepository, times(1)).save(productVariation);
	}

	/**
	 * Test case for creating a new ProductVariation with null data.
	 * The method should return null.
	 */
	@Test
	void createNewVariation_WhenVariationIsNull_ShouldReturnNull() throws ErrorInField, MissingRequiredField {
		// Arrange
		ProductVariation nullVariation = null;

		// Act
		ProductVariation createdVariation = productService.createNewVariation(productModel, nullVariation);

		// Assert
		assertThat(createdVariation).isNull();
	}

	/**
	 * Test case for creating a new ProductVariation with invalid data.
	 * The method should throw an ErrorInField exception.
	 */
	@Test
	void createNewVariation_WhenVariationIsInvalid_ShouldThrowErrorInField() {
		// Arrange
		ProductVariation invalidVariation = new ProductVariation();
		invalidVariation.setColor("InvalidColorWithMoreThan50Characters123456789012345678901234567890");

		// Act and Assert
		assertThrows(ErrorInField.class, () -> productService.createNewVariation(productModel, invalidVariation));
	}


}