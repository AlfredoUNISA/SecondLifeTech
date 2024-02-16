package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.config.Role;
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
class ShippingAddressRepositoryTests {
	@Autowired
	private ShippingAddressRepository shippingAddressRepository;
	@Autowired
	private UserRepository userRepository;

	@Test
	void ShippingAddressRepository_findByUserId_ReturnCorrectList() throws ParseException {
		// Arrange
		String dateOfBirthString = "01/01/2000";
		Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirthString);

		User user1 = new User("Mario", "Rossi", "email@email.com", "password", dateOfBirth, Role.CLIENTE, null);

		User user2 = new User("Giovanni", "Verdi", "email2@email.com", "password", dateOfBirth, Role.CLIENTE, null);

		userRepository.save(user1);
		userRepository.save(user2);

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

		shippingAddressRepository.save(shippingAddress1);
		shippingAddressRepository.save(shippingAddress2);
		shippingAddressRepository.save(shippingAddress3);

		// Act
		List<ShippingAddress> foundAddresses = shippingAddressRepository.findByUserId(user1.getId());

		// Assert
		assertThat(foundAddresses).isNotNull();
		assertThat(foundAddresses.size()).isEqualTo(2);
		assertThat(foundAddresses).containsOnly(shippingAddress1, shippingAddress2);
		assertThat(foundAddresses).doesNotContain(shippingAddress3);
	}
}