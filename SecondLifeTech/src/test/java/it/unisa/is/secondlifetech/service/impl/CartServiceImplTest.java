package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.CartItem;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {
	@Mock
	private CartRepository cartRepository;

	@Mock
	private ProductVariationServiceImpl productVariationService;

	@Mock
	private CartItemServiceImpl cartItemService;

	@InjectMocks
	private CartServiceImpl cartService;

	@Test
	public void CartService_AddToCart_CorrectlyAddToCart() {
		// Arrange
		UUID cartId = UUID.randomUUID();
		UUID productVariationId = UUID.randomUUID();
		int quantity = 3;

		Cart cart = new Cart();
		cart.setTotal(100.0);
		ProductVariation productVariation = new ProductVariation();
		productVariation.setId(productVariationId);
		productVariation.setQuantityInStock(5);
		productVariation.setPrice(20.0);

		when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
		when(productVariationService.findProductVariationById(productVariationId)).thenReturn(productVariation);

		// Act
		cartService.addToCart(cartId, productVariationId, quantity);

		// Assert
		assertThat(cart.getProducts()).hasSize(1);
		assertThat(cart.getTotal()).isEqualTo(160.0);
		assertThat(cart.getProducts().get(0).getProductVariation()).isEqualTo(productVariation);

		verify(cartRepository, times(1)).findById(cartId);
		verify(productVariationService, times(1)).findProductVariationById(productVariationId);
		verify(cartItemService, times(1)).createNewCartItem(any(CartItem.class));
		verify(cartRepository, times(1)).save(cart);
	}

	@Test
	public void CartService_AddToCart_ProductAlreadyInCart() {
		// Arrange
		UUID cartId = UUID.randomUUID();
		UUID productVariationId = UUID.randomUUID();
		int initialQuantity = 2;
		int additionalQuantity = 3;

		Cart cart = new Cart();
		cart.setTotal(100.0);
		ProductVariation productVariation = new ProductVariation();
		productVariation.setId(productVariationId);
		productVariation.setQuantityInStock(5);
		productVariation.setPrice(20.0);

		CartItem cartItem = new CartItem(cart, productVariation, initialQuantity, 40.0);
		cart.getProducts().add(cartItem);

		when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
		when(productVariationService.findProductVariationById(productVariationId)).thenReturn(productVariation);

		// Act
		cartService.addToCart(cartId, productVariationId, additionalQuantity);

		// Assert
		assertThat(cart.getProducts()).hasSize(1);
		assertThat(cart.getTotal()).isEqualTo(160.0);

		verify(cartRepository, times(1)).findById(cartId);
		verify(productVariationService, times(1)).findProductVariationById(productVariationId);
		verify(cartRepository, times(1)).save(cart);
	}

	@Test
	public void CartService_AddToCart_InsufficientQuantity() {
		// Arrange
		UUID cartId = UUID.randomUUID();
		UUID productVariationId = UUID.randomUUID();
		int quantity = 10;

		Cart cart = new Cart();
		cart.setTotal(100.0);
		ProductVariation productVariation = new ProductVariation();
		productVariation.setId(productVariationId);
		productVariation.setQuantityInStock(5);
		productVariation.setPrice(20.0);

		when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
		when(productVariationService.findProductVariationById(productVariationId)).thenReturn(productVariation);

		// Act & Assert
		assertThatThrownBy(() -> cartService.addToCart(cartId, productVariationId, quantity))
			.isInstanceOf(RuntimeException.class)
			.hasMessage("Quantità non disponibile nell'inventario");

		verify(cartRepository, times(1)).findById(cartId);
		verify(productVariationService, times(1)).findProductVariationById(productVariationId);
	}

	@Test
	void CartService_EditToCart_CorrectlyEditQuantityInCart() {
		// Assert
		Cart cart1 = new Cart();

		Cart cart2 = new Cart();

		ProductModel productModel = new ProductModel(
			"iPhone 11",
			"Apple",
			ProductCategory.SMARTPHONE
		);
		productModel.setId(UUID.randomUUID());
		productModel.setImageFile(null);

		ProductVariation productVariation1 = new ProductVariation(
			UUID.randomUUID(),
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
			UUID.randomUUID(),
			2020,
			4,
			6.0,
			128,
			250.0,
			3,
			"Red",
			ProductState.OTTIMO,
			productModel
		);

		when(cartRepository.findById(cart1.getId())).thenReturn(Optional.of(cart1));
		when(cartRepository.findById(cart2.getId())).thenReturn(Optional.of(cart2));

		when(cartRepository.save(cart2)).thenReturn(cart2);

		when(productVariationService.findProductVariationById(productVariation1.getId())).thenReturn(productVariation1);
		when(productVariationService.findProductVariationById(productVariation2.getId())).thenReturn(productVariation2);

		cartService.addToCart(cart1.getId(), productVariation1.getId(), 1);
		cartService.addToCart(cart1.getId(), productVariation2.getId(), 1);

		// Act
		cartService.editProductQuantityInCart(cart1.getId(), productVariation1.getId(), 2);
		Cart foundCart = cartRepository.findById(cart1.getId()).get();

		// Assert
		assertThat(foundCart).isNotNull();
		assertThat(foundCart.getProducts().size()).isEqualTo(2);
		foundCart.getProducts().forEach(cartProduct -> {
			if(cartProduct.getProductVariation().getId().equals(productVariation1.getId())) {
				assertThat(cartProduct.getQuantity()).isEqualTo(2);
			}
		});
	}

	@Test
	void CartService_RemoveFromCart_CorrectlyRemoveFromCart() {
		// Assert
		Cart cart1 = new Cart();

		Cart cart2 = new Cart();

		ProductModel productModel = new ProductModel(
			"iPhone 11",
			"Apple",
			ProductCategory.SMARTPHONE
		);
		productModel.setId(UUID.randomUUID());
		productModel.setImageFile(null);

		ProductVariation productVariation1 = new ProductVariation(
			UUID.randomUUID(),
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
			UUID.randomUUID(),
			2020,
			4,
			6.0,
			128,
			250.0,
			3,
			"Red",
			ProductState.OTTIMO,
			productModel
		);

		when(cartRepository.findById(cart1.getId())).thenReturn(Optional.of(cart1));
		when(cartRepository.findById(cart2.getId())).thenReturn(Optional.of(cart2));

		when(cartRepository.save(cart2)).thenReturn(cart2);

		when(productVariationService.findProductVariationById(productVariation1.getId())).thenReturn(productVariation1);
		when(productVariationService.findProductVariationById(productVariation2.getId())).thenReturn(productVariation2);


		cartService.addToCart(cart1.getId(), productVariation1.getId(), 1);
		cartService.addToCart(cart1.getId(), productVariation2.getId(), 1);

		// Act
		cartService.removeProductFromCart(cart1.getId(), productVariation1.getId());
		Cart foundCart = cartRepository.findById(cart1.getId()).get();

		// Assert
		assertThat(foundCart).isNotNull();
		assertThat(foundCart.getProducts().size()).isEqualTo(1);
		assertThat(foundCart.getTotal()).isEqualTo(250.0);
		assertThat(foundCart.getProducts().get(0).getProductVariation().getId()).isEqualTo(productVariation2.getId());
	}

	@Test
	void CartService_ClearCart_CorrectlyClearCart() {
		// Assert
		Cart cart1 = new Cart();

		Cart cart2 = new Cart();

		ProductModel productModel = new ProductModel(
			"iPhone 11",
			"Apple",
			ProductCategory.SMARTPHONE
		);
		productModel.setId(UUID.randomUUID());
		productModel.setImageFile(null);

		ProductVariation productVariation1 = new ProductVariation(
			UUID.randomUUID(),
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
			UUID.randomUUID(),
			2020,
			4,
			6.0,
			128,
			250.0,
			3,
			"Red",
			ProductState.OTTIMO,
			productModel
		);

		when(cartRepository.findById(cart1.getId())).thenReturn(Optional.of(cart1));
		when(cartRepository.findById(cart2.getId())).thenReturn(Optional.of(cart2));

		when(cartRepository.save(cart2)).thenReturn(cart2);

		when(productVariationService.findProductVariationById(productVariation1.getId())).thenReturn(productVariation1);
		when(productVariationService.findProductVariationById(productVariation2.getId())).thenReturn(productVariation2);

		cartService.addToCart(cart1.getId(), productVariation1.getId(), 1);
		cartService.addToCart(cart1.getId(), productVariation2.getId(), 1);

		// Act
		cartService.clearCart(cart1.getId());
		Cart foundCart = cartRepository.findById(cart1.getId()).get();

		// Assert
		assertThat(foundCart).isNotNull();
		assertThat(foundCart.getProducts().size()).isEqualTo(0);
		assertThat(foundCart.getTotal()).isEqualTo(0);
	}
}