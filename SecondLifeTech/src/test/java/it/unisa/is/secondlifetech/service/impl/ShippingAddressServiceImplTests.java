package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.PaymentMethod;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.entity.ShippingAddress;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.repository.ShippingAddressRepository;
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
class ShippingAddressServiceImplTests {

	@Mock
	private ShippingAddressRepository shippingAddressRepository;

	@InjectMocks
	private ShippingAddressServiceImpl shippingAddressService;

	private ShippingAddress shippingAddress;
	private User user;

	@BeforeEach
	void setup() {
		user = User.builder()
			.id(UUID.randomUUID())
			.shippingAddresses(new ArrayList<>())
			.role(UserRole.CLIENTE)
			.build();

		// Focus del testing
		shippingAddress = ShippingAddress.builder()
			.id(UUID.randomUUID())
			.user(user)
			.build();
		user.getShippingAddresses().add(shippingAddress);
	}

	@Test
	void ShippingAddressService_FindShippingAddressesByUser_ReturnCorrectList() throws ParseException {
		// Arrange
		UUID userId = user.getId();

		when(shippingAddressRepository.findByUserId(userId)).thenReturn(user.getShippingAddresses());

		// Act
		List<ShippingAddress> result = shippingAddressService.findShippingAddressesByUser(userId);

		// Assert
		assertThat(result).hasSize(1);
		assertThat(result).containsOnly(shippingAddress);
	}
}