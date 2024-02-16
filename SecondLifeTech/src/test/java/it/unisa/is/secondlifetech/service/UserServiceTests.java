package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.config.Role;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.repository.UserRepository;
import it.unisa.is.secondlifetech.service.impl.UserServiceImpl;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void UserService_FindUserByEmail_ReturnCorrectEmail() throws ParseException {
		// Arrange
		User user = User.builder()
			.firstName("Mario")
			.lastName("Rossi")
			.email("email@email.com")
			.password("password")
			.birthDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"))
			.role(Role.CLIENTE)
			.build();

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

		User user1 = User.builder()
			.firstName("Mario")
			.lastName("Rossi")
			.email("email@email.com")
			.password("password")
			.birthDate(dateOfBirth)
			.role(Role.CLIENTE)
			.build();
		User user2 = User.builder()
			.firstName("Luigi")
			.lastName("Verdi")
			.email("email2@email.com")
			.password("password")
			.birthDate(dateOfBirth)
			.role(Role.CLIENTE)
			.build();
		User gestore = User.builder()
			.firstName("Antonio")
			.lastName("Arancioni")
			.email("emailAziendale@email.com")
			.password("password")
			.birthDate(dateOfBirth)
			.role(Role.GESTORE_PRODOTTI)
			.build();

		when(userRepository.findByRole(Role.CLIENTE)).thenReturn(List.of(user1, user2));

		// Act
		List<User> foundUsers = userService.findUsersByRole(Role.CLIENTE);

		// Assert
		assertThat(foundUsers).isNotNull();
		assertThat(foundUsers.size()).isEqualTo(2);
		assertThat(foundUsers).containsOnly(user1, user2);
		assertThat(foundUsers).doesNotContain(gestore);
	}
}