package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.entity.PaymentMethod;
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
class PaymentMethodRepositoryTests {
	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private UserRepository userRepository;

	@Test
	void PaymentMethodRepository_FindByUserId_ReturnCorrectList() throws ParseException {
		// Arrange
		String dateOfBirthString = "01/01/2000";
		Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirthString);

		User user1 = new User("Mario", "Rossi", "email@email.com", "password", dateOfBirth, UserRole.CLIENTE, null);
		user1.setCart(new Cart());
		User user2 = new User("Giovanni", "Verdi", "email2@email.com", "password", dateOfBirth, UserRole.CLIENTE, null);
		user2.setCart(new Cart());

		userRepository.save(user1);
		cartRepository.save(user1.getCart());
		userRepository.save(user2);
		cartRepository.save(user2.getCart());

		PaymentMethod paymentMethod1 = PaymentMethod.builder()
			.user(user1)
			.cardHolderName("Mario Rossi")
			.cardNumber("1234567890123456")
			.expirationDate("01/23")
			.cvv("123")
			.build();

		PaymentMethod paymentMethod2 = PaymentMethod.builder()
			.user(user1)
			.cardHolderName("Mario Rossi")
			.cardNumber("9876543210987654")
			.expirationDate("01/20")
			.cvv("123")
			.build();

		PaymentMethod paymentMethod3 = PaymentMethod.builder()
			.user(user2)
			.cardHolderName("Giovanni Verdi")
			.cardNumber("54454656545645645")
			.expirationDate("01/22")
			.cvv("123")
			.build();

		paymentMethodRepository.save(paymentMethod1);
		paymentMethodRepository.save(paymentMethod2);
		paymentMethodRepository.save(paymentMethod3);

		// Act
		List<PaymentMethod> foundMethods = paymentMethodRepository.findByUserId(user1.getId());

		// Assert
		assertThat(foundMethods).isNotNull();
		assertThat(foundMethods.size()).isEqualTo(2);
		assertThat(foundMethods).containsOnly(paymentMethod1, paymentMethod2);
		assertThat(foundMethods).doesNotContain(paymentMethod3);
	}
}