package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.CartItem;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.repository.CartRepository;
import it.unisa.is.secondlifetech.repository.ProductVariationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {
	@Mock
	private CartRepository cartRepository;

	@Mock
	private ProductVariationRepository productVariationRepository;

	@InjectMocks
	private CartServiceImpl cartService;

	@Test
	void CartService_AddToCart_CorrectlyAddToCart() {
		// Assert
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

		when(cartRepository.findById(cart1.getId())).thenReturn(Optional.of(cart1));
		when(cartRepository.findById(cart2.getId())).thenReturn(Optional.of(cart2));

		when(cartRepository.save(cart2)).thenReturn(cart2);

		when(productVariationRepository.findById(productVariation1.getId())).thenReturn(Optional.of(productVariation1));
		when(productVariationRepository.findById(productVariation2.getId())).thenReturn(Optional.of(productVariation2));


		// Act
		cartService.addToCart(cart1.getId(), productVariation1.getId(), 1);
		cartService.addToCart(cart1.getId(), productVariation2.getId(), 1);

		Cart foundCart = cartRepository.findById(cart1.getId()).get();

		// Assert
		assertThat(foundCart).isNotNull();
		assertThat(foundCart.getProducts().size()).isEqualTo(2);

		List<CartItem> products = foundCart.getProducts();
		products.forEach(cartProduct -> {
			assertThat(cartProduct.getCart()).isEqualTo(foundCart);
			assertThat(cartProduct.getQuantity()).isEqualTo(1);
			assertThat(cartProduct.getProductVariation().getProductModel()).isEqualTo(productModel);
		});
		assertThat(foundCart.getTotal()).isEqualTo(500.0);

		assertThatThrownBy(() -> cartService.addToCart(cart1.getId(), productVariation1.getId(), 50))
			.isInstanceOf(RuntimeException.class)
			.hasMessage("Quantità non disponibile nell'inventario");
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

		when(productVariationRepository.findById(productVariation1.getId())).thenReturn(Optional.of(productVariation1));
		when(productVariationRepository.findById(productVariation2.getId())).thenReturn(Optional.of(productVariation2));

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

		when(productVariationRepository.findById(productVariation1.getId())).thenReturn(Optional.of(productVariation1));
		when(productVariationRepository.findById(productVariation2.getId())).thenReturn(Optional.of(productVariation2));

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

		when(productVariationRepository.findById(productVariation1.getId())).thenReturn(Optional.of(productVariation1));
		when(productVariationRepository.findById(productVariation2.getId())).thenReturn(Optional.of(productVariation2));

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