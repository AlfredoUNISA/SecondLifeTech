package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.config.Role;
import it.unisa.is.secondlifetech.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {
	@Autowired
	private UserRepository userRepository;

	@Test
	public void UserRepository_FindByEmail_ReturnCorrectEmail() {
		// Arrange
		User user = User.builder()
			.firstName("Mario")
			.lastName("Rossi")
			.email("email@email.com")
			.build();
		User savedUser = userRepository.save(user);

		// Act
		User foundUser = userRepository.findByEmail(savedUser.getEmail()).get();

		// Assert
		assertThat(foundUser.getEmail()).isNotNull();
		assertThat(foundUser).isEqualTo(savedUser);
	}

	@Test
	public void UserRepository_Save_ReturnDateOfBirth() throws ParseException {
		// Arrange
		String dateOfBirthString = "01/01/2000";
		Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirthString);

		User user = User.builder()
			.firstName("Mario")
			.lastName("Rossi")
			.email("email@email.com")
			.password("password")
			.birthDate(dateOfBirth)
			.build();

		User savedUser = userRepository.save(user);
		User foundUser = userRepository.findById(savedUser.getId()).get();

		// Act
		Date foundDateOfBirth = foundUser.getBirthDate();

		// Assert
		assertThat(foundDateOfBirth).isNotNull();
		assertThat(foundDateOfBirth).isEqualTo(dateOfBirth);
	}

	@Test
	public void UserRepository_FindByRole_ReturnCorrectList() throws ParseException {
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

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(gestore);

		// Act
		List<User> foundUsers = userRepository.findByRole(Role.CLIENTE);

		// Assert
		assertThat(foundUsers).isNotNull();
		assertThat(foundUsers.size()).isEqualTo(2);
		assertThat(foundUsers).containsOnly(user1, user2);
		assertThat(foundUsers).doesNotContain(gestore);

	}

}
