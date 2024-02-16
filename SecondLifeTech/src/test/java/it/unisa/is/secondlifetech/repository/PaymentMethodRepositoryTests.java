package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.config.Role;
import it.unisa.is.secondlifetech.entity.PaymentMethod;
import it.unisa.is.secondlifetech.entity.ShippingAddress;
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
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentMethodRepositoryTests {
	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private UserRepository userRepository;



	@Test
	void PaymentMethodRepository_FindByUserId_ReturnCorrectList() throws ParseException {
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
			.firstName("Giovanni")
			.lastName("Verdi")
			.email("email2@emali.com")
			.password("password")
			.birthDate(dateOfBirth)
			.role(Role.CLIENTE)
			.build();

		userRepository.save(user1);
		userRepository.save(user2);

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