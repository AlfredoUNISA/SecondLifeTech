package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.CartItem;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.exception.ErrorInField;
import it.unisa.is.secondlifetech.exception.MissingRequiredField;
import it.unisa.is.secondlifetech.repository.*;
import it.unisa.is.secondlifetech.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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

	@Mock
	private CartItemRepository cartItemRepository;

	@Mock
	private CartRepository cartRepository;

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
	void ProductTC1_createNewModel_WhenModelIsValid_ShouldCreateModel() throws ErrorInField, MissingRequiredField {
		// Arrange
		when(productModelRepository.save(any(ProductModel.class))).thenReturn(productModel);

		// Act
		ProductModel createdModel = productService.createNewModel(productModel);

		// Assert
		assertThat(createdModel).isEqualTo(productModel);
		verify(productModelRepository, times(1)).save(productModel);
	}

	@Test
	void ProductTC1E_createNewModel_WhenModelIsNull_ShouldReturnNull() throws ErrorInField, MissingRequiredField {
		// Arrange
		ProductModel nullModel = null;

		// Act
		ProductModel createdModel = productService.createNewModel(nullModel);

		// Assert
		assertThat(createdModel).isNull();
	}

	@Test
	void ProductTC1E_createNewModel_WhenModelNameAlreadyExists_ShouldThrowErrorInField() {
		// Arrange
		when(productModelRepository.existsByName(anyString())).thenReturn(true);

		// Act and Assert
		assertThrows(ErrorInField.class, () -> productService.createNewModel(productModel));
	}
	
	@Test
	void ProductTC1E_createNewModel_WhenModelNameIsInvalid_ShouldThrowErrorInField() {
		// Arrange
		ProductModel productModelError = new ProductModel();
		productModelError.setName("iP");
		productModelError.setBrand(productModel.getBrand());
		productModelError.setCategory(productModel.getCategory());

		// Act and Assert
		assertThrows(ErrorInField.class, () -> productService.createNewModel(productModelError));
	}

	@Test
	void ProductTC1E_createNewModel_WhenModelBrandIsInvalid_ShouldThrowErrorInField() {
		// Arrange
		ProductModel productModelError = new ProductModel();
		productModelError.setName(productModel.getName());
		productModelError.setBrand("Ap");
		productModelError.setCategory(productModel.getCategory());

		// Act and Assert
		assertThrows(ErrorInField.class, () -> productService.createNewModel(productModelError));
	}

	@Test
	void ProductTC1E_createNewModel_WhenModelCategoryIsInvalid_ShouldThrowErrorInField() {
		// Arrange
		ProductModel productModelError = new ProductModel();
		productModelError.setName(productModel.getName());
		productModelError.setBrand(productModel.getBrand());
		productModelError.setCategory("CATEGORIA_FINTA");

		// Act and Assert
		assertThrows(ErrorInField.class, () -> productService.createNewModel(productModelError));
	}



	// ================================================================================================================
	// =============== TEST CASE 2 =====================================================================================
	// ================================================================================================================

	/**
	 * <li>Deve essere creata una ProductVariation con i dati inseriti durante la creazione</li>
	 * <li>Deve essere associata al ProductModel specificato</li>
	 */
	@Test
	void ProductTC2_createNewVariation_WhenVariationIsValid_ShouldCreateVariation() throws ErrorInField, MissingRequiredField {
		// Arrange
		when(productVariationRepository.save(any(ProductVariation.class))).thenReturn(productVariation);

		// Act
		ProductVariation createdVariation = productService.createNewVariation(productModel, productVariation);

		// Assert
		assertThat(createdVariation).isEqualTo(productVariation);
		verify(productVariationRepository, times(1)).save(productVariation);
	}


	@Test
	void ProductTC2E_createNewVariation_WhenYearIsNotValid_ShouldThrowErrorInFieldException() {
		// Arrange
		ProductVariation productVariationError = productVariation;
		productVariationError.setYear(2105);

		// Act & Assert
		assertThrows(ErrorInField.class, () -> productService.createNewVariation(productVariationError.getModel(), productVariationError));
	}

	@Test
	void ProductTC2E_createNewVariation_WhenRamIsNotValid_ShouldThrowErrorInFieldException() {
		// Arrange
		ProductVariation productVariationError = productVariation;
		productVariationError.setRam(-5);

		// Act & Assert
		assertThrows(ErrorInField.class, () -> productService.createNewVariation(productVariationError.getModel(), productVariationError));
	}

	@Test
	void ProductTC2E_createNewVariation_WhenDisplayIsNotValid_ShouldThrowErrorInFieldException() {
		// Arrange
		ProductVariation productVariationError = productVariation;
		productVariationError.setDisplaySize(-8);

		// Act & Assert
		assertThrows(ErrorInField.class, () -> productService.createNewVariation(productVariationError.getModel(), productVariationError));
	}

	@Test
	void ProductTC2E_createNewVariation_WhenStorageIsNotValid_ShouldThrowErrorInFieldException() {
		// Arrange
		ProductVariation productVariationError = productVariation;
		productVariationError.setStorageSize(-100);

		// Act & Assert
		assertThrows(ErrorInField.class, () -> productService.createNewVariation(productVariationError.getModel(), productVariationError));
	}

	@Test
	void ProductTC2E_createNewVariation_WhenPriceIsNotValid_ShouldThrowErrorInFieldException() {
		// Arrange
		ProductVariation productVariationError = productVariation;
		productVariationError.setPrice(-4);

		// Act & Assert
		assertThrows(ErrorInField.class, () -> productService.createNewVariation(productVariationError.getModel(), productVariationError));
	}

	@Test
	void ProductTC2E_createNewVariation_WhenStockIsNotValid_ShouldThrowErrorInFieldException() {
		// Arrange
		ProductVariation productVariationError = productVariation;
		productVariationError.setQuantityInStock(-1);

		// Act & Assert
		assertThrows(ErrorInField.class, () -> productService.createNewVariation(productVariationError.getModel(), productVariationError));
	}

	@Test
	void ProductTC2E_createNewVariation_WhenColorIsNotValid_ShouldThrowErrorInFieldException() {
		// Arrange
		ProductVariation productVariationError = productVariation;
		productVariationError.setColor("B");

		// Act & Assert
		assertThrows(ErrorInField.class, () -> productService.createNewVariation(productVariationError.getModel(), productVariationError));
	}

	@Test
	void ProductTC2E_createNewVariation_WhenStateIsNotValid_ShouldThrowErrorInFieldException() {
		// Arrange
		ProductVariation productVariationError = productVariation;
		productVariationError.setState("STATO_FINTO");

		// Act & Assert
		assertThrows(ErrorInField.class, () -> productService.createNewVariation(productVariationError.getModel(), productVariationError));
	}

	@Test
	void ProductTC2E_createNewVariation_WhenModelIsNotValid_ShouldThrowIllegalArgumentException() {
		// Arrange
		ProductVariation productVariationError = productVariation;
		productVariationError.setModel(null);

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> productService.createNewVariation(productVariationError.getModel(), productVariationError));
	}


	// ================================================================================================================
	// =============== TEST CASE 3 =====================================================================================
	// ================================================================================================================

	/**
	 * Il ProductModel deve essere aggiornato con i nuovi dati
 	 */
	@Test
	void ProductTC3_updateModel_WhenModelIsValid_ShouldUpdateModel() throws ErrorInField, MissingRequiredField, IOException {
		// Arrange
		when(productModelRepository.findById(any(UUID.class))).thenReturn(Optional.of(productModel));
		when(productModelRepository.save(any(ProductModel.class))).thenReturn(productModel);

		// Act
		ProductModel updatedModel = productService.updateModel(productModel, null);

		// Assert
		assertThat(updatedModel).isEqualTo(productModel);
		verify(productModelRepository, times(1)).save(productModel);
	}

	@Test
	void ProductTC3E_updateModel_WhenModelIsNull_ShouldThrowIllegalArgumentException() {
		// Arrange
		ProductModel nullModel = null;

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> productService.updateModel(nullModel, null));
	}



	// ================================================================================================================
	// =============== TEST CASE 4 =====================================================================================
	// ================================================================================================================

	/**
	 * La ProductVariation deve essere aggiornata con i nuovi dati
	 */
	@Test
	void ProductTC4_updateVariation_WhenVariationIsValid_ShouldUpdateVariation() {
		// Arrange
		when(productVariationRepository.save(any(ProductVariation.class))).thenReturn(productVariation);

		// Act
		ProductVariation updatedVariation = productService.updateVariation(productVariation);

		// Assert
		assertThat(updatedVariation).isEqualTo(productVariation);
		verify(productVariationRepository, times(1)).save(productVariation);
	}

	@Test
	void ProductTC4E_updateVariation_WhenVariationIsNull_ShouldThrowIllegalArgumentException() {
		// Arrange
		ProductVariation nullVariation = null;

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> productService.updateVariation(nullVariation));
	}



	// ================================================================================================================
	// =============== TEST CASE 5 =====================================================================================
	// ================================================================================================================

	/**
	 * <li>Il ProductModel deve essere eliminato dal sistema</li>
	 * <li>Tutte le ProductVariation associate devono essere eliminate dal sistema e dai carrelli</li>
	 */
	@Test
	void ProductTC5_deleteModel_WhenModelIsValid_ShouldDeleteModelAndItsVariations() {
		// Arrange
		productModel.addVariation(productVariation);

		Cart cart = new Cart();
		CartItem cartItem = new CartItem();
		cartItem.setProductVariation(productVariation);
		cartItem.setQuantity(1);
		cart.addItem(cartItem);

		when(cartItemRepository.findByProductVariationId(any(UUID.class))).thenReturn(List.of(cartItem));
		when(cartRepository.save(any(Cart.class))).thenReturn(cart);

		// Act
		productService.deleteModel(productModel);

		// Assert
		verify(productModelRepository, times(1)).delete(productModel);
		assertThat(productModel.getVariations()).isEmpty();
		assertThat(cart.getItems()).isEmpty();
	}

	@Test
	void ProductTC5E_deleteModel_WhenModelIsNull_ShouldThrowIllegalArgumentException() {
		// Arrange
		ProductModel nullModel = null;

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> productService.deleteModel(nullModel));
	}



	// ================================================================================================================
	// =============== TEST CASE 6 =====================================================================================
	// ================================================================================================================

	/**
	 * La ProductVariation deve essere eliminata dal sistema e dai carrelli
	 */
	@Test
	void ProductTC6_deleteVariation_WhenVariationIsValid_ShouldDeleteVariation() {
		// Arrange
		Cart cart = new Cart();
		CartItem cartItem = new CartItem();
		cartItem.setProductVariation(productVariation);
		cartItem.setQuantity(1);
		cart.addItem(cartItem);

		when(cartItemRepository.findByProductVariationId(any(UUID.class))).thenReturn(List.of(cartItem));
		when(cartRepository.save(any(Cart.class))).thenReturn(cart);

		// Act
		productService.deleteVariation(productVariation);

		// Assert
		verify(productVariationRepository, times(1)).delete(productVariation);
		assertThat(cart.getItems()).isEmpty();
	}

	@Test
	void ProductTC6E_deleteVariation_WhenVariationIsNull_ShouldThrowIllegalArgumentException() {
		// Arrange
		ProductVariation nullVariation = null;

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> productService.deleteVariation(nullVariation));
	}

}