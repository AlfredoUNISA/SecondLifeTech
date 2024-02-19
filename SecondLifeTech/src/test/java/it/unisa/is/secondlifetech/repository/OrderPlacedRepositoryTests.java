package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
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
class OrderPlacedRepositoryTests {
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private ProductModelRepository productModelRepository;
	@Autowired
	private ProductVariationService productVariationService;
	@Autowired
	private OrderPlacedRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;

	@Test
	void OrderPlacedRepository_FindByEmail_ReturnCorrectList() {
		// Arrange
		ProductModel productModel = new ProductModel(
			"iPhone 11",
			"Apple",
			"Smartphone"
		);
		productModelRepository.save(productModel);
		ProductVariation productVariation = new ProductVariation(
			2020,
			4,
			6.0,
			128,
			250.0,
			3,
			"Green",
			"Accettabile",
			productModel
		);
		productVariationService.save(productVariation);

		User user = new User(
			"Mario",
			"Rossi",
			"email@email.com",
			"password",
			null,
			UserRole.CLIENTE,
			null
		);
		cartRepository.save(user.getCart());
		userRepository.save(user);

		OrderPlaced order1 = new OrderPlaced(
			"Via Roma 1",
			user.getEmail(),
			new Date(),
			250.0,
			false,
			user
		);
		OrderItem orderItem1 = new OrderItem(
			1,
			productVariation.getPrice()*1,
			order1,
			productVariation
		);
		orderRepository.save(order1);
		orderItemRepository.save(orderItem1);

		OrderPlaced order2 = new OrderPlaced(
			"Via Roma 1",
			user.getEmail(),
			new Date(),
			500.0,
			false,
			user
		);
		OrderItem orderItem2 = new OrderItem(
			2,
			productVariation.getPrice()*2,
			order2,
			productVariation
		);
		orderRepository.save(order2);
		orderItemRepository.save(orderItem2);

		// Act
		List<OrderPlaced> foundOrders = orderRepository.findByEmail(user.getEmail());

		// Assert
		assertThat(foundOrders).isNotNull();
		assertThat(foundOrders.size()).isEqualTo(2);
		assertThat(foundOrders).containsOnly(order1, order2);
		assertThat(foundOrders.get(0).getUser()).isEqualTo(user);
		assertThat(foundOrders.get(1).getUser()).isEqualTo(user);
	}

	@Test
	void OrderPlacedRepository_FindByShipped_ReturnCorrectList() {
		// Arrange
		ProductModel productModel = new ProductModel(
			"iPhone 11",
			"Apple",
			"Smartphone"
		);
		productModelRepository.save(productModel);
		ProductVariation productVariation = new ProductVariation(
			2020,
			4,
			6.0,
			128,
			250.0,
			3,
			"Green",
			"Accettabile",
			productModel
		);
		productVariationService.save(productVariation);

		User user = new User(
			"Mario",
			"Rossi",
			"email@email.com",
			"password",
			null,
			UserRole.CLIENTE,
			null
		);
		cartRepository.save(user.getCart());
		userRepository.save(user);

		OrderPlaced order1 = new OrderPlaced(
			"Via Roma 1",
			user.getEmail(),
			new Date(),
			250.0,
			true,
			user
		);
		OrderItem orderItem1 = new OrderItem(
			1,
			productVariation.getPrice()*1,
			order1,
			productVariation
		);
		orderRepository.save(order1);
		orderItemRepository.save(orderItem1);

		OrderPlaced order2 = new OrderPlaced(
			"Via Roma 1",
			user.getEmail(),
			new Date(),
			500.0,
			false,
			user
		);
		OrderItem orderItem2 = new OrderItem(
			2,
			productVariation.getPrice()*2,
			order2,
			productVariation
		);
		orderRepository.save(order2);
		orderItemRepository.save(orderItem2);

		// Act
		List<OrderPlaced> foundOrders = orderRepository.findByShipped(true);

		// Assert
		assertThat(foundOrders).isNotNull();
		assertThat(foundOrders.size()).isEqualTo(1);
		assertThat(foundOrders).containsOnly(order1);
		assertThat(foundOrders.get(0).getUser()).isEqualTo(user);

	}

	@Test
	void OrderPlacedRepository_FindByOrderDate_ReturnCorrectList() throws ParseException {
		// Arrange
		ProductModel productModel = new ProductModel(
			"iPhone 11",
			"Apple",
			"Smartphone"
		);
		productModelRepository.save(productModel);
		ProductVariation productVariation = new ProductVariation(
			2020,
			4,
			6.0,
			128,
			250.0,
			3,
			"Green",
			"Accettabile",
			productModel
		);
		productVariationService.save(productVariation);

		User user = new User(
			"Mario",
			"Rossi",
			"email@email.com",
			"password",
			null,
			UserRole.CLIENTE,
			null
		);
		cartRepository.save(user.getCart());
		userRepository.save(user);

		Date orderDate1 = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2021");
		OrderPlaced order1 = new OrderPlaced(
			"Via Roma 1",
			user.getEmail(),
			orderDate1,
			250.0,
			true,
			user
		);
		OrderItem orderItem1 = new OrderItem(
			1,
			productVariation.getPrice()*1,
			order1,
			productVariation
		);
		orderRepository.save(order1);
		orderItemRepository.save(orderItem1);

		Date orderDate2 = new SimpleDateFormat("dd/MM/yyyy").parse("02/02/2022");
		OrderPlaced order2 = new OrderPlaced(
			"Via Roma 1",
			user.getEmail(),
			orderDate2,
			500.0,
			false,
			user
		);
		OrderItem orderItem2 = new OrderItem(
			2,
			productVariation.getPrice()*2,
			order2,
			productVariation
		);
		orderRepository.save(order2);
		orderItemRepository.save(orderItem2);

		// Act
		List<OrderPlaced> foundOrders = orderRepository.findByOrderDate(orderDate1);

		// Assert
		assertThat(foundOrders).isNotNull();
		assertThat(foundOrders.size()).isEqualTo(1);
		assertThat(foundOrders).containsOnly(order1);
		assertThat(foundOrders.get(0).getUser()).isEqualTo(user);
	}
}