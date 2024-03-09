package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
	private ProductModel productModel;
	private ProductVariation productVariation;
	private ShippingAddress shippingAddress;
	private PaymentMethod paymentMethod;

	@BeforeEach
	public void setUp() {
		user = new User();
		user.setId(UUID.randomUUID());
		user.setEmail("email@email.com");
		user.setRole(UserRole.CLIENTE);

		cart = new Cart();
		cart.setId(UUID.randomUUID());
		cart.setUser(user);
		user.setCart(cart);

		cartItem = new CartItem();
		cartItem.setId(UUID.randomUUID());

		productModel = new ProductModel();
		productModel.setId(UUID.randomUUID());
		productModel.setName("Product");
		productModel.setBrand("Brand");
		productModel.setCategory(ProductCategory.SMARTPHONE);

		productVariation = new ProductVariation();
		productVariation.setId(UUID.randomUUID());
		productVariation.setYear(2021);
		productVariation.setRam(4);
		productVariation.setDisplaySize(5.5);
		productVariation.setStorageSize(64);
		productVariation.setPrice(230);
		productVariation.setQuantityInStock(10);
		productVariation.setColor("Black");
		productVariation.setState(ProductState.BUONO);
		productVariation.setModel(productModel);

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
	// =============== TEST CASE 1 =====================================================================================
	// ================================================================================================================

	/**
	 * Il carrello deve contenere un CartItem a cui è assegnata la ProductVariation inserita attraverso la sua UUID
	 */
	@Test
	public void CartTC1_addToCart_WhenProductVariationExists_ShouldAddProductToCart() throws NoDevicesAvailableException {
		// Arrange
		UUID productVariationId = productVariation.getId();
		int quantity = 1;

		when(productService.findVariationById(productVariation.getId())).thenReturn(productVariation);
		when(cartRepository.save(cart)).thenReturn(cart);

		// Act
		cartService.addToCart(cart, productVariationId, quantity);

		// Assert
		assertThat(cart.getItems().get(0).getProductVariation()).isEqualTo(productVariation);
		assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(quantity);
		assertThat(cart.getItems().get(0).getSubTotal()).isEqualTo(productVariation.getPrice() * quantity);
		assertThat(cart.getTotal()).isEqualTo(productVariation.getPrice() * quantity);
	}

	@Test
	public void CartTC1E_addToCart_WhenCartIsNull_ShouldThrowNullPointerException() {
		// Arrange
		Cart cartError = null;
		UUID productVariationId = productVariation.getId();
		int quantity = 1;

		// Act & Assert
		assertThrows(NullPointerException.class, () -> cartService.addToCart(cartError, productVariationId, quantity));
	}

	@Test
	public void CartTC1E_addToCart_WhenProductVariationDoesNotExist_ShouldThrowIllegalArgumentException() {
		// Arrange
		UUID productVariationIdError = UUID.randomUUID();
		int quantity = 1;

		when(productService.findVariationById(productVariationIdError)).thenReturn(null);

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> cartService.addToCart(cart, productVariationIdError, quantity));
	}

	@Test
	public void CartTC1E_addToCart_WhenQuantityIsLessThanOne_ShouldThrowIllegalArgumentException() {
		// Arrange
		UUID productVariationId = productVariation.getId();
		int quantityError = -5;

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> cartService.addToCart(cart, productVariationId, quantityError));
	}

	@Test
	public void CartTC1E_addToCart_WhenQuantityIsGreaterThanStock_ShouldThrowNoDevicesAvailableException() {
		// Arrange
		UUID productVariationId = productVariation.getId();
		int quantityError = 5000;

		when(productService.findVariationById(productVariationId)).thenReturn(productVariation);

		// Act & Assert
		assertThrows(NoDevicesAvailableException.class, () -> cartService.addToCart(cart, productVariationId, quantityError));
	}


	// ================================================================================================================
	// =============== TEST CASE 2 =====================================================================================
	// ================================================================================================================


	/**
	 * Il carrello non contiene un CartItem con UUID specificato
	 */
	@Test
	public void CartTC2_removeFromCart_WhenCartItemExists_ShouldRemoveProductFromCart() {
		// Arrange
		UUID cartItemId = cartItem.getId();
		cart.addItem(cartItem);

		when(cartRepository.save(cart)).thenReturn(cart);

		// Act
		cartService.removeProductFromCart(cart, cartItemId);

		// Assert
		assertThat(cart.getItems()).doesNotContain(cartItem);
		assertThat(cart.getTotal()).isEqualTo(0);
	}

	@Test
	public void CartTC2E_removeFromCart_WhenCartIsNull_ShouldThrowNullPointerException() {
		// Arrange
		Cart cartError = null;
		UUID cartItemId = cartItem.getId();

		// Act & Assert
		assertThrows(NullPointerException.class, () -> cartService.removeProductFromCart(cartError, cartItemId));
	}

	@Test
	public void CartTC2E_removeFromCart_WhenCartItemDoesNotExist_ShouldNotChangeCart() {
		// Arrange
		UUID cartItemIdError = UUID.randomUUID();
		cart.addItem(cartItem);

		int initialCartSize = cart.getItems().size();

		// Act
		cartService.removeProductFromCart(cart, cartItemIdError);

		// Assert
		assertThat(cart.getItems().size()).isEqualTo(initialCartSize);
	}

	// ================================================================================================================
	// =============== TEST CASE 3 =====================================================================================
	// ================================================================================================================

	/**
	 * Deve essere aggiornata alla quantità inserita e il subtotale e il carrello deve avere il totale giusto
	 */
	@Test
	public void CartTC3_editProductQuantityInCart_WhenProductVariationExists_ShouldUpdateProductQuantityInCart() throws NoDevicesAvailableException {
		// Arrange
		UUID productVariationId = productVariation.getId();
		int newQuantity = 2;
		cartItem.setProductVariation(productVariation);
		cartItem.setQuantity(newQuantity);
		cart.addItem(cartItem);

		when(cartRepository.save(cart)).thenReturn(cart);

		// Act
		cartService.editProductQuantityInCart(cart, productVariationId, newQuantity);

		// Assert
		assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(newQuantity);
		assertThat(cart.getItems().get(0).getSubTotal()).isEqualTo(productVariation.getPrice() * newQuantity);
		assertThat(cart.getTotal()).isEqualTo(productVariation.getPrice() * newQuantity);
	}

	@Test
	public void CartTC3E_editProductQuantityInCart_WhenCartIsNull_ShouldThrowNullPointerException() {
		// Arrange
		Cart cartError = null;
		UUID productVariationId = productVariation.getId();
		int newQuantity = 2;

		// Act & Assert
		assertThrows(NullPointerException.class, () -> cartService.editProductQuantityInCart(cartError, productVariationId, newQuantity));
	}

	@Test
	public void CartTC3E_editProductQuantityInCart_WhenProductVariationDoesNotExist_ShouldHappenNothing() throws NoDevicesAvailableException {
		// Arrange
		UUID productVariationIdError = UUID.randomUUID();
		int newQuantity = 2;

		// Act
		cartService.editProductQuantityInCart(cart, productVariationIdError, newQuantity);

		// Assert
		assertThat(cart.getItems()).isEmpty();
		assertThat(cart.getTotal()).isEqualTo(0);
	}

	@Test
	public void CartTC3E_editProductQuantityInCart_WhenTheNewQuantityIsTooMuch_ShouldThrowNoDevicesAvailableException() {
		// Arrange
		UUID productVariationId = productVariation.getId();
		int newQuantity = 50000;
		cartItem.setProductVariation(productVariation);
		cart.addItem(cartItem);


		// Act & Assert
		assertThrows(NoDevicesAvailableException.class, () -> cartService.editProductQuantityInCart(cart, productVariationId, newQuantity));
	}


	// ================================================================================================================
	// =============== TEST CASE 4 =====================================================================================
	// ================================================================================================================

	/**
	 * <li>Deve essere creato un OrderPlaced contenente un OrderItem a cui è assegnato la ProductVariation inserita attraverso la sua UUID</li>
	 * <li>Il Cart non deve contenere alcun CartItem</li>
	 * <li>L’ordine deve essere presente tra gli ordini dell’User</li>
	 */
	@Test
	public void CartTC4_finalizeOrder_WhenCartAndShippingAddressAndPaymentMethodAreProvided_ShouldFinalizeOrder() throws NoItemsForFinalizationException, NoDevicesAvailableException, NoShippingAddressException, NoPaymentMethodException, PaymentFailedException {
		// Arrange
		cartItem.setProductVariation(productVariation);
		int quantity = 1;
		cartItem.setQuantity(quantity);
		cartItem.setSubTotal(productVariation.getPrice() * quantity);
		cart.addItem(cartItem);

		when(orderService.createAndPlaceNewOrder(any(OrderPlaced.class))).thenReturn(new OrderPlaced());

		// Act
		cartService.finalizeOrder(cart, shippingAddress, paymentMethod, true);

		// Assert
		assertThat(cart.getItems()).isEmpty();
		assertThat(user.getOrders().get(0).getItems().get(0).getProductVariation()).isEqualTo(productVariation);
		assertThat(user.getOrders().get(0).getItems().get(0).getQuantityOrdered()).isEqualTo(quantity);
	}

	@Test
	public void CartTC4E_finalizeOrder_WhenCartIsEmpty_ShouldThrowNoItemsForFinalizationException() {
		// Arrange
		Cart emptyCart = new Cart();
		emptyCart.setId(UUID.randomUUID());
		emptyCart.setUser(user);
		user.setCart(emptyCart);

		// Act & Assert
		assertThrows(NoItemsForFinalizationException.class, () -> cartService.finalizeOrder(emptyCart, shippingAddress, paymentMethod, true));
	}

	@Test
	public void CartTC4E_finalizeOrder_WhenShippingAddressIsNull_ShouldThrowNoShippingAddressException() {
		// Arrange
		cartItem.setProductVariation(productVariation);
		int quantity = 1;
		cartItem.setQuantity(quantity);
		cartItem.setSubTotal(productVariation.getPrice() * quantity);
		cart.addItem(cartItem);

		ShippingAddress nullShippingAddress = null;

		// Act & Assert
		assertThrows(NoShippingAddressException.class, () -> cartService.finalizeOrder(cart, nullShippingAddress, paymentMethod, true));
	}

	@Test
	public void CartTC4E_finalizeOrder_WhenPaymentMethodIsNull_ShouldThrowNoPaymentMethodException() {
		// Arrange
		cartItem.setProductVariation(productVariation);
		int quantity = 1;
		cartItem.setQuantity(quantity);
		cartItem.setSubTotal(productVariation.getPrice() * quantity);
		cart.addItem(cartItem);

		PaymentMethod nullPaymentMethod = null;

		// Act & Assert
		assertThrows(NoPaymentMethodException.class, () -> cartService.finalizeOrder(cart, shippingAddress, nullPaymentMethod, true));
	}

	@Test
	public void CartTC4E_finalizeOrder_WhenPaymentFails_ShouldThrowPaymentFailedException() {
		// Arrange
		cartItem.setProductVariation(productVariation);
		int quantity = 1;
		cartItem.setQuantity(quantity);
		cartItem.setSubTotal(productVariation.getPrice() * quantity);
		cart.addItem(cartItem);

		boolean paymentSuccessfulMock = false;

		// Act & Assert
		assertThrows(PaymentFailedException.class, () -> cartService.finalizeOrder(cart, shippingAddress, paymentMethod, paymentSuccessfulMock));
	}

}
