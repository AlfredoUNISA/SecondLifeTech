package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.ShippingAddress;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.repository.ShippingAddressRepository;
import it.unisa.is.secondlifetech.repository.UserRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {
	@Mock
	private UserRepository userRepository;

	@Mock
	private ShippingAddressRepository shippingAddressRepository;

	@InjectMocks
	private UserServiceImpl userService;

	private User user;

	@BeforeEach
	void setup() {
		user = User.builder()
			.id(UUID.randomUUID())
			.email("email@email.com")
			.role(UserRole.CLIENTE)
			.shippingAddresses(new ArrayList<>())
			.build();
	}

	@Test
	void UserService_FindUserByEmail_ShouldReturnCorrectEmail() {
		// Arrange
		String email = user.getEmail();

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		// Act
		User result = userService.findUserByEmail(email);

		// Assert
		assertThat(result).isEqualTo(user);
	}

	@Test
	void UserService_FindByRole_ShouldReturnCorrectList() {
		// Arrange
		String role = user.getRole();

		when(userRepository.findByRole(role)).thenReturn(List.of(user));

		// Act
		List<User> results = userService.findUsersByRole(role);

		// Assert
		assertThat(results).hasSize(1);
		assertThat(results).containsOnly(user);
	}

	@Test
	void UserService_UpdateShippingAddress_ShouldUpdateAddress() {
		// Arrange
		ShippingAddress shippingAddress = new ShippingAddress(
			"Via Roma, 1",
			"Salerno",
			"SA",
			"84100",
			"ITALY"
		);
		userService.createNewShippingAddress(user, shippingAddress);

		ShippingAddress found = user.getShippingAddresses().get(0);
		found.setState("Campania");

		when(userRepository.findAll()).thenReturn(List.of(user));
		when(shippingAddressRepository.save(shippingAddress)).thenReturn(shippingAddress);

		// Act
		userService.updateShippingAddress(found);
		User updatedUser = userService.findAllUsers().get(0);

		// Assert
		assertThat(updatedUser.getShippingAddresses().get(0).getState()).isEqualTo("Campania");
	}

}