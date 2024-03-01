package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.exception.*;
import it.unisa.is.secondlifetech.repository.CartItemRepository;
import it.unisa.is.secondlifetech.repository.CartRepository;
import it.unisa.is.secondlifetech.repository.ProductVariationRepository;
import it.unisa.is.secondlifetech.service.OrderService;
import it.unisa.is.secondlifetech.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

	@Mock
	private CartRepository cartRepository;

	@Mock
	private CartItemRepository cartItemRepository;

	@Mock
	private OrderService orderService;

	@Mock
	private ProductService productService;

	@InjectMocks
	private CartServiceImpl cartService;

	private User user;
	private Cart cart;
	private CartItem cartItem;
	private ProductVariation productVariation;
	private ShippingAddress shippingAddress;
	private PaymentMethod paymentMethod;

	@BeforeEach
	public void setUp() {
		user = new User();
		user.setId(UUID.randomUUID());
		user.setEmail("email@email.com");

		productVariation = new ProductVariation();
		productVariation.setId(UUID.randomUUID());
		productVariation.setQuantityInStock(10);

		cart = new Cart();
		cart.setId(UUID.randomUUID());
		cart.setUser(user);

		cartItem = new CartItem();
		cartItem.setId(UUID.randomUUID());
		cartItem.setCart(cart);

		shippingAddress = new ShippingAddress();
		shippingAddress.setId(UUID.randomUUID());
		shippingAddress.setCity("City");
		shippingAddress.setCountry("Country");
		shippingAddress.setStreet("Street");
		shippingAddress.setZipCode("12345");
		shippingAddress.setUser(user);

		paymentMethod = new PaymentMethod();
		paymentMethod.setId(UUID.randomUUID());
		paymentMethod.setCardNumber("1234567890123456");
		paymentMethod.setCardHolderName("Card Holder");
		paymentMethod.setExpirationDate("12/23");
		paymentMethod.setCvv("123");
		paymentMethod.setUser(user);
	}


	// ================================================================================================================
	// =============== CREATE ==========================================================================================
	// ================================================================================================================


//	@Test
//	public void CartServiceImpl_createNewCart_WhenCartIsProvided_ShouldReturnSavedCart() {
//		// Arrange
//		Cart cart = new Cart();
//		when(cartRepository.save(any(Cart.class))).thenReturn(cart);
//
//		// Act
//		Cart savedCart = cartService.createNewCart(cart);
//
//		// Assert
//		assertThat(savedCart).isNotNull();
//		verify(cartRepository, times(1)).save(cart);
//	}

	@Test
	public void CartServiceImpl_addToCart_WhenProductVariationExists_ShouldAddProductToCart() throws NoDevicesAvailableException {
		// Arrange
		UUID productVariationId = productVariation.getId();
		int quantity = 5;

		when(productService.findVariationById(any(UUID.class))).thenReturn(productVariation);
		when(cartRepository.save(any(Cart.class))).thenReturn(cart);

		// Act
		cartService.addToCart(cart, productVariationId, quantity);

		// Assert
		verify(cartRepository, times(1)).save(cart);
		verify(cartItemRepository, times(1)).save(any(CartItem.class));
	}

	@Test
	public void CartServiceImpl_finalizeOrder_WhenCartAndShippingAddressAreProvided_ShouldFinalizeOrder() throws NoDevicesAvailableException, NoShippingAddressException, NoPaymentMethodException, NoItemsForFinalizationException, PaymentFailedException {
		// Arrange
		when(productService.findVariationById(any(UUID.class))).thenReturn(productVariation);
		when(orderService.createAndPlaceNewOrder(any(OrderPlaced.class))).thenReturn(new OrderPlaced());

		// Act
		cartService.addToCart(cart, productVariation.getId(), 5);
		cartService.finalizeOrder(cart, shippingAddress, paymentMethod, true);

		// Assert
		verify(orderService, times(1)).createAndPlaceNewOrder(any(OrderPlaced.class));
	}



	// ================================================================================================================
	// =============== READ ============================================================================================
	// ================================================================================================================

	@Test
	public void CartServiceImpl_findCartById_WhenIdIsProvided_ShouldReturnCart() {
		// Arrange
		UUID cartId = cart.getId();
		when(cartRepository.findById(any(UUID.class))).thenReturn(Optional.of(cart));

		// Act
		Cart foundCart = cartService.findCartById(cartId);

		// Assert
		assertThat(foundCart).isNotNull();
		assertThat(foundCart).isEqualTo(cart);
		verify(cartRepository, times(1)).findById(cartId);
	}

	@Test
	public void CartServiceImpl_findCartItemById_WhenIdIsProvided_ShouldReturnCartItem() {
		// Arrange
		UUID cartItemId = cartItem.getId();
		when(cartItemRepository.findById(any(UUID.class))).thenReturn(Optional.of(cartItem));

		// Act
		CartItem foundCartItem = cartService.findCartItemById(cartItemId);

		// Assert
		assertThat(foundCartItem).isNotNull();
		assertThat(foundCartItem).isEqualTo(cartItem);
		verify(cartItemRepository, times(1)).findById(cartItemId);
	}

	@Test
	public void CartServiceImpl_findCartItemByProductVariation_WhenProductVariationIsProvided_ShouldReturnCartItems() {
		// Arrange
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(cartItem);
		when(cartItemRepository.findByProductVariationId(any(UUID.class))).thenReturn(cartItems);

		// Act
		List<CartItem> foundCartItems = cartService.findCartItemByProductVariation(productVariation);

		// Assert
		assertThat(foundCartItems).isNotNull();
		assertThat(foundCartItems).hasSize(1);
		assertThat(foundCartItems.get(0)).isEqualTo(cartItem);
		verify(cartItemRepository, times(1)).findByProductVariationId(productVariation.getId());
	}



	// ================================================================================================================
	// =============== UPDATE ==========================================================================================
	// ================================================================================================================

	@Test
	public void CartServiceImpl_updateCart_WhenCartIsProvided_ShouldReturnUpdatedCart() {
		// Arrange
		Cart updatedCart = new Cart();
		updatedCart.setId(cart.getId());
		updatedCart.setUser(user);
		when(cartRepository.save(any(Cart.class))).thenReturn(updatedCart);

		// Act
		Cart returnedCart = cartService.updateCart(updatedCart);

		// Assert
		assertThat(returnedCart).isNotNull();
		assertThat(returnedCart.getId()).isEqualTo(updatedCart.getId());
		verify(cartRepository, times(1)).save(updatedCart);
	}

	@Test
	public void CartServiceImpl_editProductQuantityInCart_WhenProductVariationIdAndNewQuantityAreProvided_ShouldUpdateProductQuantityInCart() throws NoDevicesAvailableException {
		// Arrange
		UUID productVariationId = productVariation.getId();
		int newQuantity = 7;
		cartItem.setProductVariation(productVariation);
		cartItem.setQuantity(newQuantity);
		cart.getItems().add(cartItem);

		// Act
		cartService.editProductQuantityInCart(cart, productVariationId, newQuantity);

		// Assert
		assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(newQuantity);
		verify(cartRepository, times(1)).save(cart);
	}



	// ================================================================================================================
	// =============== DELETE ==========================================================================================
	// ================================================================================================================

	@Test
	public void CartServiceImpl_deleteCart_WhenCartIsProvided_ShouldDeleteCart() {
		// Arrange
		cart.addItem(cartItem);

		// Act
		cartService.deleteCart(cart);

		// Assert
		verify(cartItemRepository, times(1)).deleteAll(cart.getItems());
		verify(cartRepository, times(1)).delete(cart);
	}

	@Test
	public void CartServiceImpl_removeProductFromCart_WhenCartAndCartItemIdAreProvided_ShouldRemoveProductFromCart() {
		// Arrange
		cart.addItem(cartItem);
		UUID cartItemId = cartItem.getId();

		// Act
		cartService.removeProductFromCart(cart, cartItemId);

		// Assert
		verify(cartItemRepository, times(1)).delete(cartItem);
		verify(cartRepository, times(1)).save(cart);
	}

	@Test
	public void CartServiceImpl_clearCart_WhenCartIsProvided_ShouldClearCart() {
		// Arrange
		cart.addItem(cartItem);

		// Act
		cartService.clearCart(cart);

		// Assert
		verify(cartItemRepository, times(1)).deleteAll(cart.getItems());
		verify(cartRepository, times(1)).save(cart);
	}
}
