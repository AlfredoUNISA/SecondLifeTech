package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.config.Role;
import it.unisa.is.secondlifetech.entity.PaymentMethod;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.repository.PaymentMethodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentMethodServiceImplTest {
	@Mock
	private PaymentMethodRepository paymentMethodRepository;

	@InjectMocks
	private PaymentMethodServiceImpl paymentMethodService;

	@Test
	void PaymentMethodService_FindPaymentMethodsByUser_ReturnCorrectList() throws ParseException {
		// Arrange
		String dateOfBirthString = "01/01/2000";
		Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirthString);

		User user1 = new User("Mario", "Rossi", "email@email.com", "password", dateOfBirth, Role.CLIENTE, null);

		User user2 = new User("Giovanni", "Verdi", "email2@email.com", "password", dateOfBirth, Role.CLIENTE, null);

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

		when(paymentMethodRepository.findByUserId(user1.getId())).thenReturn(List.of(paymentMethod1, paymentMethod2));

		// Act
		List<PaymentMethod> foundMethods = paymentMethodService.findPaymentMethodsByUser(user1.getId());

		// Assert
		assertThat(foundMethods).isNotNull();
		assertThat(foundMethods.size()).isEqualTo(2);
		assertThat(foundMethods).containsOnly(paymentMethod1, paymentMethod2);
		assertThat(foundMethods).doesNotContain(paymentMethod3);
	}
}