package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.constants.UserRole;
import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartRepositoryTests {
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void CartRepository_FindByUserId_ReturnCorrectCart() throws ParseException {
		// Arrange
		String dateOfBirthString = "01/01/2000";
		Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirthString);

		User user1 = new User("Mario", "Rossi", "email@email.com", "password", dateOfBirth, UserRole.CLIENTE, null);

		User user2 = new User("Giovanni", "Verdi", "email2@email.com", "password", dateOfBirth, UserRole.CLIENTE, null);

		userRepository.save(user1);
		userRepository.save(user2);

		// Act
		Cart editedByList = user1.getCart();
		editedByList.setTotal(10);

		Cart foundCart = cartRepository.findByUserId(user1.getId());
		foundCart.setTotal(42);
		Cart newFoundCart = cartRepository.findByUserId(user1.getId());

		// Assert
		assertThat(foundCart).isNotNull();
		assertThat(foundCart.getUser()).isEqualTo(user1);
		assertThat(newFoundCart.getTotal()).isEqualTo(42);
		assertThat(newFoundCart.getTotal()).isNotEqualTo(10);

	}
}