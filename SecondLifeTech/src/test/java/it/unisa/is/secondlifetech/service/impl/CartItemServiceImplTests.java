package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.CartItem;
import it.unisa.is.secondlifetech.repository.CartItemRepository;
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
class CartItemServiceImplTests {
	@Mock
	private CartItemRepository cartItemRepository;

	@InjectMocks
	private CartItemServiceImpl cartProductService;

	private Cart cart;
	private CartItem cartItem;

	@BeforeEach
	void setup() {
		cart = Cart.builder()
			.id(UUID.randomUUID())
			.items(new ArrayList<>())
			.build();

		// Focus del testing
		cartItem = CartItem.builder()
			.id(UUID.randomUUID())
			.cart(cart)
			.build();
		cart.getItems().add(cartItem);
	}

	@Test
	void CartProductService_FindCartProductsByCart_ShouldReturnCorrectList() {
		// Arrange
		UUID cartId = cart.getId();

		when(cartItemRepository.findByCartId(cartId)).thenReturn(cart.getItems());

		// Act
		List<CartItem> result = cartProductService.findCartItemByCart(cartId);

		// Assert
		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isEqualTo(cartItem);

	}

}