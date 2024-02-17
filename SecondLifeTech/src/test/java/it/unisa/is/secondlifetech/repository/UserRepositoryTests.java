package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.constants.UserRole;
import it.unisa.is.secondlifetech.entity.Cart;
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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {
	@Autowired
	private UserRepository userRepository;

	@Test
	public void UserRepository_FindByEmail_ReturnCorrectEmail() throws ParseException {
		// Arrange
		String dateOfBirthString = "01/01/2000";
		Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirthString);
		User user = new User("Mario", "Rossi", "email@email.com", "password", dateOfBirth, "CLIENTE", null);
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

		User user = new User("Mario", "Rossi", "email@email.com", "password", dateOfBirth, UserRole.CLIENTE, null);

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

		User user1 = new User("Mario", "Rossi", "email@email.com", "password", dateOfBirth, UserRole.CLIENTE, null);
		User user2 = new User("Giovanni", "Verdi", "email2@email.com", "password", dateOfBirth, UserRole.CLIENTE, null);
		User gestore = new User("Antonio", "Arancioni", "emailAziendale@email.com", "password", dateOfBirth, UserRole.GESTORE_PRODOTTI, "");

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(gestore);

		// Act
		List<User> foundUsers = userRepository.findByRole(UserRole.CLIENTE);

		// Assert
		assertThat(foundUsers).isNotNull();
		assertThat(foundUsers.size()).isEqualTo(2);
		assertThat(foundUsers).containsOnly(user1, user2);
		assertThat(foundUsers).doesNotContain(gestore);

	}

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;
	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	@Autowired
	private CartRepository cartRepository;

	@Test
	public void UserRepository_Save_SetCorrectDependencies() throws ParseException {
		// Arrange
		String dateOfBirthString = "01/01/2000";
		Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirthString);

		User user = new User("Mario", "Rossi", "email@email.com", "password", dateOfBirth, UserRole.CLIENTE, null);
		userRepository.save(user);

		ShippingAddress shippingAddress1 = ShippingAddress.builder()
			.street("Street1")
			.city("City1")
			.country("Country1")
			.state("State1")
			.zipCode("Zip")
			.user(user)
			.build();

		ShippingAddress shippingAddress2 = ShippingAddress.builder()
			.street("Street2")
			.city("City2")
			.country("Country2")
			.state("State2")
			.zipCode("Zip2")
			.user(user) // Basta inserire solo il riferimento all'utente e verrà aggiunto alla lista
			.build();

		PaymentMethod paymentMethod = PaymentMethod.builder()
			.cardHolderName("Giorno Giovanna")
			.cardNumber("654645645646465")
			.expirationDate("01/20")
			.cvv("123")
			.user(user)
			.build();

		// Act
		shippingAddressRepository.save(shippingAddress1);
		shippingAddressRepository.save(shippingAddress2);
		List<ShippingAddress> foundAddresses = shippingAddressRepository.findByUserId(user.getId());

		paymentMethodRepository.save(paymentMethod);
		List<PaymentMethod> foundMethods = paymentMethodRepository.findByUserId(user.getId());

		user.getCart().setTotal(15);
		Cart foundCart = cartRepository.save(user.getCart());

		// Assert
		assertThat(foundAddresses).isNotNull();
		assertThat(foundAddresses.size()).isEqualTo(2);
		assertThat(foundAddresses).contains(shippingAddress1, shippingAddress2);

		assertThat(foundMethods).isNotNull();
		assertThat(foundMethods.size()).isEqualTo(1);
		assertThat(foundMethods).contains(paymentMethod);

		assertThat(foundCart).isNotNull();
		assertThat(foundCart.getTotal()).isEqualTo(15);

	}

}
