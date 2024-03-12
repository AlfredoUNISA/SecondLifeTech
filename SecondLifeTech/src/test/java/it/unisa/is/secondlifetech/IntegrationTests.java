package it.unisa.is.secondlifetech;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.exception.*;
import it.unisa.is.secondlifetech.service.CartService;
import it.unisa.is.secondlifetech.service.ProductService;
import it.unisa.is.secondlifetech.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test di integrazione tra i servizi. Utilizza l'intera applicazione.<br>
 * <b>Attenzione</b>: questo test <i>potrebbe</i> richiede che il database sia azzerato.
 *
 */
@SpringBootTest
public class IntegrationTests {
	@Autowired
	private CartService cartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	private User user;
	private Cart cart;
	private ProductModel productModel;
	private ProductVariation productVariation1;
	private ProductVariation productVariation2;
	private ShippingAddress shippingAddress;
	private PaymentMethod paymentMethod;

	@BeforeEach
	public void setUp() {
		user = new User();
		user.setFirstName("Mario");
		user.setLastName("Rossi");
		user.setPassword("password");
		user.setPhoneNumber("1234567890");
		user.setEmail("email@email.com");
		user.setRole(UserRole.CLIENTE);

		shippingAddress = new ShippingAddress();
		shippingAddress.setCity("City");
		shippingAddress.setCountry("Country");
		shippingAddress.setStreet("Street");
		shippingAddress.setState("State");
		shippingAddress.setZipCode("12345");

		paymentMethod = new PaymentMethod();
		paymentMethod.setCardNumber("1234567890123456");
		paymentMethod.setCardHolderName("Card Holder");
		paymentMethod.setExpirationDate("12/23");
		paymentMethod.setCvv("123");

		productModel = new ProductModel();
		productModel.setName("Iphone Nuovissimo");
		productModel.setBrand("Apple");
		productModel.setCategory(ProductCategory.SMARTPHONE);

		productVariation1 = new ProductVariation();
		productVariation1.setYear(2021);
		productVariation1.setRam(4);
		productVariation1.setDisplaySize(5.5);
		productVariation1.setStorageSize(64);
		productVariation1.setPrice(230);
		productVariation1.setQuantityInStock(10);
		productVariation1.setColor("Nero");
		productVariation1.setState(ProductState.BUONO);

		productVariation2 = new ProductVariation();
		productVariation2.setYear(2020);
		productVariation2.setRam(6);
		productVariation2.setDisplaySize(6);
		productVariation2.setStorageSize(128);
		productVariation2.setPrice(300);
		productVariation2.setQuantityInStock(3);
		productVariation2.setColor("White");
		productVariation2.setState(ProductState.OTTIMO);
	}

	@Test
	public void integrationTesting() throws NoShippingAddressException, NoPaymentMethodException, NoDevicesAvailableException, NoItemsForFinalizationException, PaymentFailedException, MissingRequiredFieldException, ErrorInFieldException, EmailAlreadyInUseException {
		// Crea l'utente (USER_SERVICE)
		user = userService.createNewUser(user);
		paymentMethod = userService.createNewPaymentMethod(user, paymentMethod);
		shippingAddress = userService.createNewShippingAddress(user, shippingAddress);
		cart = user.getCart();

		// Crea il prodotto (PRODUCT_SERVICE)
		productModel = productService.createNewModel(productModel);
		productVariation1 = productService.createNewVariation(productModel, productVariation1);
		productVariation2 = productService.createNewVariation(productModel, productVariation2);

		// Aggiungi il prodotto al carrello (CART_SERVICE)
		cartService.addToCart(cart, productVariation1.getId(), 1);
		assertThat(cart.getItems()).isNotEmpty();

		// Finalizza l'ordine (CART_SERVICE e ORDER_SERVICE)
		cartService.finalizeOrder(cart, shippingAddress, paymentMethod, true);

		// Assert
		productVariation1 = productService.findVariationById(productVariation1.getId());
		OrderPlaced order = user.getOrders().get(0);
		ProductVariation variationInOrder = order.getItems().get(0).getProductVariation();

		assertThat(cart.getItems()).isEmpty();
		assertThat(variationInOrder.getId()).isEqualTo(productVariation1.getId());
		assertThat(variationInOrder.getQuantityInStock()).isEqualTo(9);
		assertThat(user.getOrders()).isNotEmpty();
		assertThat(order.getItems()).isNotEmpty();
		assertThat(order.getAddress()).isEqualTo(shippingAddress.fullAddress());
	}
}
