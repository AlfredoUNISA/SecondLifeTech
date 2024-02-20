package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.repository.UserRepository;
import it.unisa.is.secondlifetech.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	private User user;

	@BeforeEach
	void setup() {
		user = User.builder()
			.id(UUID.randomUUID())
			.email("email@email.com")
			.role(UserRole.CLIENTE)
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
	void UserService_FindByRole_ShouldReturnCorrectList() throws ParseException {
		// Arrange
		String role = user.getRole();

		when(userRepository.findByRole(role)).thenReturn(List.of(user));

		// Act
		List<User> results = userService.findUsersByRole(role);

		// Assert
		assertThat(results).hasSize(1);
		assertThat(results).containsOnly(user);
	}

}