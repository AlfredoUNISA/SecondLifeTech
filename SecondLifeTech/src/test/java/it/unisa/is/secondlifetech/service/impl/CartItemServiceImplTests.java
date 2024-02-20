package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.CartItem;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.repository.CartItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTests {
	@Mock
	private CartItemRepository cartItemRepository;

	@InjectMocks
	private CartItemServiceImpl cartProductService;

	@Test
	void CartProductService_FindCartProductsByCart_ReturnCorrectList() {
		// Arrange
		Cart cart1 = new Cart();

		Cart cart2 = new Cart();

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

		CartItem cartItem1 = new CartItem(cart1, productVariation1, 1, productVariation1.getPrice());
		CartItem cartItem2 = new CartItem(cart1, productVariation2, 2, productVariation1.getPrice()*2);
		CartItem cartItem3 = new CartItem(cart2, productVariation1, 1, productVariation2.getPrice());

		when(cartItemRepository.findByCartId(cart1.getId())).thenReturn(List.of(cartItem1, cartItem2));

		// Act
		List<CartItem> foundCartItems = cartProductService.findCartItemByCart(cart1.getId());

		// Assert
		assertThat(foundCartItems).isNotNull();
		assertThat(foundCartItems.size()).isEqualTo(2);
		assertThat(foundCartItems.get(0).getCart()).isEqualTo(cart1);
		assertThat(foundCartItems.get(1).getCart()).isEqualTo(cart1);

		List<ProductVariation> variations = new ArrayList<>();
		variations.add(foundCartItems.get(0).getProductVariation());
		variations.add(foundCartItems.get(1).getProductVariation());

		assertThat(variations).containsOnly(productVariation1, productVariation2);
	}
}