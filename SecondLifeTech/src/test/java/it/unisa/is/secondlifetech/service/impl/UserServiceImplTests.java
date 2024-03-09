package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.exception.EmailAlreadyInUseException;
import it.unisa.is.secondlifetech.exception.ErrorInFieldException;
import it.unisa.is.secondlifetech.exception.MissingRequiredFieldException;
import it.unisa.is.secondlifetech.repository.PaymentMethodRepository;
import it.unisa.is.secondlifetech.repository.ShippingAddressRepository;
import it.unisa.is.secondlifetech.repository.UserRepository;
import it.unisa.is.secondlifetech.service.CartService;
import it.unisa.is.secondlifetech.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {
	@Mock
	private UserRepository userRepository;

	@Mock
	private CartService cartService;

	@Mock
	private ShippingAddressRepository shippingAddressRepository;

	@Mock
	private PaymentMethodRepository paymentMethodRepository;

	@Mock
	private OrderService orderService;
	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserServiceImpl userService;

	private User user;
	private Cart cart;
	private PaymentMethod paymentMethod;
	private ShippingAddress shippingAddress;
	private OrderPlaced order;

	@BeforeEach
	void setup() {
		user = new User();
		user.setId(UUID.randomUUID());
		user.setFirstName("Mario");
		user.setLastName("Rossi");
		user.setEmail("email@email.com");
		user.setPassword("passMario");
		user.setRole(UserRole.CLIENTE);
		user.setPhoneNumber("3231235479");

		shippingAddress = new ShippingAddress();
		shippingAddress.setId(UUID.randomUUID());
		shippingAddress.setStreet("Via Roma, 1");
		shippingAddress.setCity("Vietri sul Mare");
		shippingAddress.setCountry("Salerno");
		shippingAddress.setZipCode("84019");
		shippingAddress.setState("Italia");

		paymentMethod = new PaymentMethod();
		paymentMethod.setId(UUID.randomUUID());
		paymentMethod.setCardNumber("1234567890123456");
		paymentMethod.setCvv("123");
		paymentMethod.setExpirationDate("12/24");
		paymentMethod.setCardHolderName("Mario Rossi");

		order = new OrderPlaced();

		cart = new Cart();

		user.setCart(cart);
		user.addPaymentMethod(paymentMethod);
		user.addShippingAddress(shippingAddress);
		user.getOrders().add(order);
		order.setUser(user);
	}

	// ================================================================================================================
	// ============ TEST CASE 1 ========================================================================================
	// ================================================================================================================

	/**
	 * Deve essere creato un User con i dati inseriti durante la creazione
	 */
	@Test
	void UserTC1_createUser_WhenUserIsValid_ShouldCreateNewUser() throws MissingRequiredFieldException, ErrorInFieldException, EmailAlreadyInUseException {
		// Arrange
		user.setId(null);
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPass");

		// Act
		User newUser = userService.createNewUser(user);

		// Assert
		assertThat(newUser).isNotNull();
		assertThat(newUser.getId()).isEqualTo(user.getId());
		assertThat(newUser.getFirstName()).isEqualTo(user.getFirstName());
		assertThat(newUser.getLastName()).isEqualTo(user.getLastName());
		assertThat(newUser.getEmail()).isEqualTo(user.getEmail());
		assertThat(newUser.getPassword()).isEqualTo("encodedPass");
		assertThat(newUser.getRole()).isEqualTo(user.getRole());
		assertThat(newUser.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
		assertThat(newUser.getCart()).isEqualTo(user.getCart());
		assertThat(newUser.getShippingAddresses()).isEqualTo(user.getShippingAddresses());
		assertThat(newUser.getPaymentMethods()).isEqualTo(user.getPaymentMethods());
		assertThat(newUser.getOrders()).isEqualTo(user.getOrders());
	}

	@Test
	void UserTC1E_createUser_WhenNameIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		user.setId(null);
		user.setFirstName("M");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewUser(user));
	}

	@Test
	void UserTC1E_createUser_WhenLastNameIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		user.setId(null);
		user.setLastName("o");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewUser(user));
	}

	@Test
	void UserTC1E_createUser_WhenEmailIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		user.setId(null);
		user.setEmail("mr");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewUser(user));
	}

	@Test
	void UserTC1E_createUser_WhenEmailAlreadyExists_ShouldThrowEmailAlreadyInUseException() {
		// Arrange
		user.setId(null);
		user.setEmail("myNameIsMario@yahoo.com");
		when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

		// Act & Assert
		assertThrows(EmailAlreadyInUseException.class, () -> userService.createNewUser(user));
	}

	@Test
	void UserTC1E_createUser_WhenPasswordIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		user.setId(null);
		user.setPassword("12");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewUser(user));
	}

	@Test
	void UserTC1E_createUser_WhenRoleIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		user.setId(null);
		user.setRole("RUOLO_FINTO");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewUser(user));
	}

	@Test
	void UserTC1E_createUser_WhenPhoneNumberIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		user.setId(null);
		user.setPhoneNumber("123");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewUser(user));
	}


	// ================================================================================================================
	// ============ TEST CASE 2 ========================================================================================
	// ================================================================================================================

	/**
	 * L’User deve essere aggiornato con nuovi dati
	 */
	@Test
	void UserTC2_updateUser_WhenUserIsValid_ShouldUpdateUser() {
		// Arrange
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPass");
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

		// Act
		User updatedUser = userService.updateUser(user);

		// Assert
		assertThat(updatedUser).isNotNull();
		assertThat(updatedUser.getId()).isEqualTo(user.getId());
		assertThat(updatedUser.getFirstName()).isEqualTo(user.getFirstName());
		assertThat(updatedUser.getLastName()).isEqualTo(user.getLastName());
		assertThat(updatedUser.getEmail()).isEqualTo(user.getEmail());
		assertThat(updatedUser.getPassword()).isEqualTo("encodedPass");
		assertThat(updatedUser.getRole()).isEqualTo(user.getRole());
		assertThat(updatedUser.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
		assertThat(updatedUser.getCart()).isEqualTo(user.getCart());
		assertThat(updatedUser.getShippingAddresses()).isEqualTo(user.getShippingAddresses());
		assertThat(updatedUser.getPaymentMethods()).isEqualTo(user.getPaymentMethods());
		assertThat(updatedUser.getOrders()).isEqualTo(user.getOrders());
	}

	@Test
	void UserTC2E_updateUser_WhenUserDoesNotExist_ShouldThrowIllegalArgumentException() {
		// Arrange
		user.setId(null);

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user));
	}



	// ================================================================================================================
	// ============ TEST CASE 3 ========================================================================================
	// ================================================================================================================

	/**
	 * <li>L’User deve essere eliminato dal sistema</li>
	 * <li>Gli ordini devono essere modificati per non perderli</li>
	 */
	@Test
	void UserTC3_deleteUser_WhenUserExists_ShouldDeleteUser() {
		// When
		userService.deleteUser(user);

		// Then
		verify(userRepository, times(1)).delete(user);
		verify(cartService, times(1)).deleteCart(cart);
		verify(paymentMethodRepository, times(1)).deleteAll(user.getPaymentMethods());
		verify(shippingAddressRepository, times(1)).deleteAll(user.getShippingAddresses());
	}


	@Test
	void UserTC3E_deleteUser_WhenUserDoesNotExist_ShouldNotInteractWithRepositories() {
		// Given
		User nonExistentUser = null;

		// When
		userService.deleteUser(nonExistentUser);

		// Then
		verify(userRepository, times(0)).delete(any(User.class));
		verify(cartService, times(0)).deleteCart(any(Cart.class));
		verify(paymentMethodRepository, times(0)).deleteAll(anyList());
		verify(shippingAddressRepository, times(0)).deleteAll(anyList());
	}

	@Test
	void UserTC3E_deleteUser_WhenUserHasOrders_ShouldNotDeleteUser() {
		// Given
		when(orderService.updateOrder(any(OrderPlaced.class))).thenReturn(order);

		// When
		userService.deleteUser(user);

		// Then
		verify(userRepository, times(1)).delete(user);
		verify(cartService, times(1)).deleteCart(cart);
		verify(paymentMethodRepository, times(1)).deleteAll(user.getPaymentMethods());
		verify(shippingAddressRepository, times(1)).deleteAll(user.getShippingAddresses());
		verify(orderService, times(1)).updateOrder(order);
	}


	// ================================================================================================================
	// ============ TEST CASE 4 ========================================================================================
	// ================================================================================================================

	/**
	 * Deve essere creato lo ShippingAddress con i dati inseriti durante la creazione
	 */
	@Test
	void UserTC4_createShippingAddress_WhenShippingAddressIsValid_ShouldCreateNewShippingAddress() throws MissingRequiredFieldException, ErrorInFieldException {
		// Arrange
		shippingAddress.setId(null);
		when(shippingAddressRepository.save(any(ShippingAddress.class))).thenReturn(shippingAddress);

		// Act
		ShippingAddress newShippingAddress = userService.createNewShippingAddress(user, shippingAddress);

		// Assert
		assertThat(newShippingAddress).isNotNull();
		assertThat(newShippingAddress.getId()).isEqualTo(shippingAddress.getId());
		assertThat(newShippingAddress.getCity()).isEqualTo(shippingAddress.getCity());
		assertThat(newShippingAddress.getCountry()).isEqualTo(shippingAddress.getCountry());
		assertThat(newShippingAddress.getStreet()).isEqualTo(shippingAddress.getStreet());
		assertThat(newShippingAddress.getZipCode()).isEqualTo(shippingAddress.getZipCode());
		assertThat(newShippingAddress.getUser()).isEqualTo(user);
	}

	@Test
	void UserTC4E_createShippingAddress_WhenStreetIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		shippingAddress.setId(null);
		shippingAddress.setStreet("R");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewShippingAddress(user, shippingAddress));
	}

	@Test
	void UserTC4E_createShippingAddress_WhenCityIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		shippingAddress.setId(null);
		shippingAddress.setCity("V");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewShippingAddress(user, shippingAddress));
	}

	@Test
	void UserTC4E_createShippingAddress_WhenCountryIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		shippingAddress.setId(null);
		shippingAddress.setCountry("S");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewShippingAddress(user, shippingAddress));
	}

	@Test
	void UserTC4E_createShippingAddress_WhenZipCodeIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		shippingAddress.setId(null);
		shippingAddress.setZipCode("123");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewShippingAddress(user, shippingAddress));
	}

	@Test
	void UserTC4E_createShippingAddress_WhenStateIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		shippingAddress.setId(null);
		shippingAddress.setState("I");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewShippingAddress(user, shippingAddress));
	}



	// ================================================================================================================
	// ============ TEST CASE 5 ========================================================================================
	// ================================================================================================================

	/**
	 * Deve essere creato il PaymentMethod con i dati inseriti durante la creazione
	 */
	@Test
	void UserTC5_createPaymentMethod_WhenPaymentMethodIsValid_ShouldCreateNewPaymentMethod() throws MissingRequiredFieldException, ErrorInFieldException {
		// Arrange
		paymentMethod.setId(null);
		when(paymentMethodRepository.save(any(PaymentMethod.class))).thenReturn(paymentMethod);

		// Act
		PaymentMethod newPaymentMethod = userService.createNewPaymentMethod(user, paymentMethod);

		// Assert
		assertThat(newPaymentMethod).isNotNull();
		assertThat(newPaymentMethod.getId()).isEqualTo(paymentMethod.getId());
		assertThat(newPaymentMethod.getCardNumber()).isEqualTo(paymentMethod.getCardNumber());
		assertThat(newPaymentMethod.getCardHolderName()).isEqualTo(paymentMethod.getCardHolderName());
		assertThat(newPaymentMethod.getCvv()).isEqualTo(paymentMethod.getCvv());
		assertThat(newPaymentMethod.getExpirationDate()).isEqualTo(paymentMethod.getExpirationDate());
		assertThat(newPaymentMethod.getUser()).isEqualTo(user);
	}

	@Test
	void UserTC5E_createPaymentMethod_WhenCardNumberIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		paymentMethod.setId(null);
		paymentMethod.setCardNumber("123");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewPaymentMethod(user, paymentMethod));
	}

	@Test
	void UserTC5E_createPaymentMethod_WhenCardHolderNameIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		paymentMethod.setId(null);
		paymentMethod.setCardHolderName("MR");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewPaymentMethod(user, paymentMethod));
	}

	@Test
	void UserTC5E_createPaymentMethod_WhenCvvIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		paymentMethod.setId(null);
		paymentMethod.setCvv("0");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewPaymentMethod(user, paymentMethod));
	}

	@Test
	void UserTC5E_createPaymentMethod_WhenExpirationDateIsNotValid_ShouldThrowErrorInField() {
		// Arrange
		paymentMethod.setId(null);
		paymentMethod.setExpirationDate("0");

		// Act & Assert
		assertThrows(ErrorInFieldException.class, () -> userService.createNewPaymentMethod(user, paymentMethod));
	}



}