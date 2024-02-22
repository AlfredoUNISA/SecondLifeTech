package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
		user.setRole(UserRole.CLIENTE);

		shippingAddress = new ShippingAddress();
		shippingAddress.setId(UUID.randomUUID());

		paymentMethod = new PaymentMethod();
		paymentMethod.setId(UUID.randomUUID());

		order = new OrderPlaced();

		cart = new Cart();

		user.setCart(cart);
		user.addPaymentMethod(paymentMethod);
		user.addShippingAddress(shippingAddress);
		user.getOrders().add(order);
		order.setUser(user);
	}

	// ================================================================================================================
	// ============ CREATE ============================================================================================
	// ================================================================================================================

	@Test
	void UserServiceImpl_createNewUser_ShouldCreateNewUser() {
		// Arrange
		user.setId(null);
		when(userRepository.save(any(User.class))).thenReturn(user);

		// Act
		User result = userService.createNewUser(user);

		// Assert
		assertThat(result).isEqualTo(user);
		verify(userRepository).save(user);
	}

	@Test
	void UserServiceImpl_createNewShippingAddress_ShouldCreateNewShippingAddress() {
		// Arrange
		when(shippingAddressRepository.save(any(ShippingAddress.class))).thenReturn(shippingAddress);
		when(userRepository.save(any(User.class))).thenReturn(user);

		// Act
		ShippingAddress result = userService.createNewShippingAddress(user, shippingAddress);

		// Assert
		assertThat(result).isEqualTo(shippingAddress);
		verify(shippingAddressRepository).save(shippingAddress);
		verify(userRepository).save(user);
	}

	@Test
	void UserServiceImpl_createNewPaymentMethod_ShouldCreateNewPaymentMethod() {
		// Arrange
		when(paymentMethodRepository.save(any(PaymentMethod.class))).thenReturn(paymentMethod);
		when(userRepository.save(any(User.class))).thenReturn(user);

		// Act
		PaymentMethod result = userService.createNewPaymentMethod(user, paymentMethod);

		// Assert
		assertThat(result).isEqualTo(paymentMethod);
		verify(paymentMethodRepository).save(paymentMethod);
		verify(userRepository).save(user);
	}

	// ================================================================================================================
	// ============ READ ==============================================================================================
	// ================================================================================================================

	@Test
	void UserServiceImpl_findUserById_ShouldReturnUser() {
		// Arrange
		when(userRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.ofNullable(user));

		// Act
		User result = userService.findUserById(user.getId());

		// Assert
		assertThat(result).isEqualTo(user);
		verify(userRepository).findById(user.getId());
	}

	@Test
	void UserServiceImpl_findShippingAddressById_ShouldReturnShippingAddress() {
		// Arrange
		when(shippingAddressRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.ofNullable(shippingAddress));

		// Act
		ShippingAddress result = userService.findShippingAddressById(shippingAddress.getId());

		// Assert
		assertThat(result).isEqualTo(shippingAddress);
		verify(shippingAddressRepository).findById(shippingAddress.getId());
	}

	@Test
	void UserServiceImpl_findPaymentMethodById_ShouldReturnPaymentMethod() {
		// Arrange
		when(paymentMethodRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.ofNullable(paymentMethod));

		// Act
		PaymentMethod result = userService.findPaymentMethodById(paymentMethod.getId());

		// Assert
		assertThat(result).isEqualTo(paymentMethod);
		verify(paymentMethodRepository).findById(paymentMethod.getId());
	}

	// ================================================================================================================
	// ============ UPDATE ============================================================================================
	// ================================================================================================================

	@Test
	void UserServiceImpl_updateUser_ShouldUpdateUser() {
		// Arrange
		when(userRepository.save(any(User.class))).thenReturn(user);

		// Act
		User updatedUser = userService.updateUser(user);

		// Assert
		assertThat(updatedUser).isEqualTo(user);
		verify(userRepository).save(user);
	}

	@Test
	void UserServiceImpl_updateShippingAddress_ShouldUpdateShippingAddress() {
		// Arrange
		when(shippingAddressRepository.save(any(ShippingAddress.class))).thenReturn(shippingAddress);

		// Act
		ShippingAddress updatedShippingAddress = userService.updateShippingAddress(shippingAddress);

		// Assert
		assertThat(updatedShippingAddress).isEqualTo(shippingAddress);
		verify(shippingAddressRepository).save(shippingAddress);
	}

	@Test
	void UserServiceImpl_updatePaymentMethod_ShouldUpdatePaymentMethod() {
		// Arrange
		when(paymentMethodRepository.save(any(PaymentMethod.class))).thenReturn(paymentMethod);

		// Act
		PaymentMethod updatedPaymentMethod = userService.updatePaymentMethod(paymentMethod);

		// Assert
		assertThat(updatedPaymentMethod).isEqualTo(paymentMethod);
		verify(paymentMethodRepository).save(paymentMethod);
	}

	// ================================================================================================================
	// ============ DELETE ============================================================================================
	// ================================================================================================================

	@Test
	void UserServiceImpl_deleteUser_ShouldDeleteUserAndRelatedEntities() {
		// Act
		userService.deleteUser(user);

		// Assert
		verify(cartService).deleteCart(cart.getId());
		verify(paymentMethodRepository).deleteAll(user.getPaymentMethods());
		verify(shippingAddressRepository).deleteAll(user.getShippingAddresses());
		verify(orderService).updateOrder(order.getId(), order);
		verify(userRepository).delete(user);
	}

	@Test
	void UserServiceImpl_deleteShippingAddress_ShouldRemoveShippingAddressFromUser() {
		// Act
		userService.deleteShippingAddress(shippingAddress);

		// Assert
		verify(shippingAddressRepository).delete(shippingAddress);
		verify(userRepository).save(user);
	}

	@Test
	void UserServiceImpl_deletePaymentMethod_ShouldRemovePaymentMethodFromUser() {
		// Act
		userService.deletePaymentMethod(paymentMethod);

		// Assert
		verify(paymentMethodRepository).delete(paymentMethod);
		verify(userRepository).save(user);
	}

}