package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.entity.PaymentMethod;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.repository.PaymentMethodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentMethodServiceImplTest {
	@Mock
	private PaymentMethodRepository paymentMethodRepository;

	@InjectMocks
	private PaymentMethodServiceImpl paymentMethodService;

	private PaymentMethod paymentMethod;
	private User user;

	@BeforeEach
	void setup() {
		user = User.builder()
			.id(UUID.randomUUID())
			.paymentMethods(new ArrayList<>())
			.role(UserRole.CLIENTE)
			.build();

		// Focus del testing
		paymentMethod = PaymentMethod.builder()
			.id(UUID.randomUUID())
			.user(user)
			.build();
		user.getPaymentMethods().add(paymentMethod);
	}

	@Test
	void PaymentMethodService_FindPaymentMethodsByUser_ShouldReturnCorrectList() {
		// Arrange
		UUID userId = user.getId();

		when(paymentMethodRepository.findByUserId(userId)).thenReturn(user.getPaymentMethods());

		// Act
		List<PaymentMethod> result = paymentMethodService.findPaymentMethodsByUser(userId);

		// Assert
		assertThat(result).hasSize(1);
		assertThat(result).containsOnly(paymentMethod);
	}
}