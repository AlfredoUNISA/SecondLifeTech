package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.config.Role;
import it.unisa.is.secondlifetech.entity.ShippingAddress;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.repository.ShippingAddressRepository;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShippingAddressServiceImplTests {

	@Mock
	private ShippingAddressRepository shippingAddressRepository;

	@InjectMocks
	private ShippingAddressServiceImpl shippingAddressService;

	@Test
	void ShippingAddressService_FindShippingAddressesByUser_ReturnCorrectList() throws ParseException {
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

		ShippingAddress shippingAddress1 = ShippingAddress.builder()
			.user(user1)
			.street("Via Roma 1")
			.city("Salerno")
			.zipCode("84100")
			.country("Salerno")
			.state("Italia")
			.build();

		ShippingAddress shippingAddress2 = ShippingAddress.builder()
			.user(user1)
			.street("Via Roma 2")
			.city("Salerno")
			.zipCode("84100")
			.country("Salerno")
			.state("Italia")
			.build();

		ShippingAddress shippingAddress3 = ShippingAddress.builder()
			.user(user2)
			.street("Via Roma 3")
			.city("Salerno")
			.zipCode("84100")
			.country("Salerno")
			.state("Italia")
			.build();

		when(shippingAddressRepository.findByUserId(user1.getId())).thenReturn(List.of(shippingAddress1, shippingAddress2));

		// Act
		List<ShippingAddress> foundAddresses = shippingAddressService.findShippingAddressesByUser(user1.getId());

		// Assert
		assertThat(foundAddresses).isNotNull();
		assertThat(foundAddresses.size()).isEqualTo(2);
		assertThat(foundAddresses).containsOnly(shippingAddress1, shippingAddress2);
		assertThat(foundAddresses).doesNotContain(shippingAddress3);
	}
}