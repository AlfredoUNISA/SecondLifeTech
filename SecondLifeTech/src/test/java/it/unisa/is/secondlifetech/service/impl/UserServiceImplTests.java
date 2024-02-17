package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.constants.UserRole;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void UserService_FindUserByEmail_ReturnCorrectEmail() throws ParseException {
		// Arrange
		String dateOfBirthString = "01/01/2000";
		Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirthString);
		User user = new User("Mario", "Rossi", "email@email.com", "password", dateOfBirth, UserRole.CLIENTE, null);

		// Mock del comportamento della repository per restituire il valore quando viene chiamato findByEmail
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

		// Act
		User foundUser = userService.findUserByEmail(user.getEmail());

		// Assert
		assertThat(foundUser).isEqualTo(user);
		assertThat(foundUser.getEmail()).isNotNull();
		assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
	}

	@Test
	void UserService_FindByRole_ReturnCorrectList() throws ParseException {
		// Arrange
		String dateOfBirthString = "01/01/2000";
		Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirthString);

		User user1 = new User("Mario", "Rossi", "email@email.com", "password", dateOfBirth, UserRole.CLIENTE, null);
		User user2 = new User("Giovanni", "Verdi", "email2@email.com", "password", dateOfBirth, UserRole.CLIENTE, null);
		User gestore = new User("Antonio", "Arancioni", "emailAziendale@email.com", "password", dateOfBirth, UserRole.GESTORE_PRODOTTI, "");

		when(userRepository.findByRole(UserRole.CLIENTE)).thenReturn(List.of(user1, user2));

		// Act
		List<User> foundUsers = userService.findUsersByRole(UserRole.CLIENTE);

		// Assert
		assertThat(foundUsers).isNotNull();
		assertThat(foundUsers.size()).isEqualTo(2);
		assertThat(foundUsers).containsOnly(user1, user2);
		assertThat(foundUsers).doesNotContain(gestore);
	}
}