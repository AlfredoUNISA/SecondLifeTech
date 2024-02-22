package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.CartItem;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.repository.CartItemRepository;
import it.unisa.is.secondlifetech.repository.CartRepository;
import it.unisa.is.secondlifetech.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

	@Mock
	private CartRepository cartRepository;

	@Mock
	private ProductService productService;

	@Mock
	private CartItemRepository cartItemRepository;

	@InjectMocks
	private CartServiceImpl cartService;

	private Cart cart;
	private ProductVariation productVariation;

	@BeforeEach
	void setup() {
		// Focus del testing
		cart = Cart.builder()
			.id(UUID.randomUUID())
			.items(new ArrayList<>())
			.build();

		productVariation = ProductVariation.builder()
			.id(UUID.randomUUID())
			.price(10.0)
			.quantityInStock(5)
			.build();
	}

	@Test
	void CartServiceImpl_AddToCart_WhenProductIsNotAlreadyInCart_ShouldAddNewCartItem() {
		// Arrange
		UUID newProductVariationId = productVariation.getId();
		int quantity = 2;

		when(productService.findVariationById(newProductVariationId)).thenReturn(productVariation);
		when(cartItemRepository.save(any(CartItem.class))).thenReturn(new CartItem());

		// Act
		cartService.addToCart(cart, newProductVariationId, quantity);

		// Assert
		assertThat(cart.getItems()).hasSize(1);
		assertThat(cart.getTotal()).isEqualTo(20.0);
		verify(cartRepository).save(cart);
	}

	@Test
	void CartServiceImpl_AddToCart_WhenProductIsAlreadyInCart_ShouldUpdateQuantity() {
		// Arrange
		UUID cartId = cart.getId();
		UUID productVariationId = productVariation.getId();
		int initialQuantity = 2;
		int additionalQuantity = 3;

		cart.getItems().add(new CartItem(cart, productVariation, initialQuantity, 20.0));
		cart.setTotal(20.0);
		
		// Act
		cartService.addToCart(cart, productVariationId, additionalQuantity);

		// Assert
		assertThat(cart.getItems()).hasSize(1);
		assertThat(cart.getTotal()).isEqualTo(50.0);
		verify(cartRepository).save(cart);
	}

	@Test
	void CartServiceImpl_EditProductQuantityInCart_ShouldUpdateQuantity() {
		// Arrange
		UUID productVariationId = productVariation.getId();
		int initialQuantity = 2;
		int newQuantity = 5;

		cart.getItems().add(new CartItem(cart, productVariation, initialQuantity, 20.0));
		cart.setTotal(20.0);

		// Act
		cartService.editProductQuantityInCart(cart, productVariationId, newQuantity);

		// Assert
		assertThat(cart.getItems()).hasSize(1);
		assertThat(cart.getTotal()).isEqualTo(50.0);
		verify(cartRepository).save(cart);
	}

	@Test
	void CartServiceImpl_removeProductFromCart_ShouldRemoveProduct() {
		// Arrange
		UUID productVariationId = productVariation.getId();

		cart.getItems().add(new CartItem(cart, productVariation, 2, 20.0));
		cart.setTotal(20.0);

		// Act
		cartService.removeProductFromCart(cart, productVariationId);

		// Assert
		assertThat(cart.getItems()).hasSize(0);
		assertThat(cart.getTotal()).isEqualTo(0.0);
		verify(cartRepository).save(cart);
	}

	@Test
	void CartServiceImpl_clearCart_ShouldClearCart() {
		// Arrange
		cart.getItems().add(new CartItem(cart, productVariation, 2, 20.0));
		cart.setTotal(20.0);

		// Act
		cartService.clearCart(cart);

		// Assert
		assertThat(cart.getItems()).hasSize(0);
		assertThat(cart.getTotal()).isEqualTo(0.0);
		verify(cartRepository).save(cart);
	}
}
