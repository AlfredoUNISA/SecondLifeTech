package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderItemRepositoryTests {
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private ProductModelRepository productModelRepository;
	@Autowired
	private ProductVariationRepository productVariationRepository;
	@Autowired
	private OrderPlacedRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;

	@Test
	void OrderItemRepository_FindByOrderId_ReturnCorrectList() {
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
		productVariationRepository.save(productVariation);

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
		List<OrderItem> foundOrderItems = orderItemRepository.findByOrderId(order1.getId());

		// Assert
		assertThat(foundOrderItems).isNotNull();
		assertThat(foundOrderItems.size()).isEqualTo(1);
		assertThat(foundOrderItems).containsOnly(orderItem1);
		assertThat(foundOrderItems.get(0).getOrder()).isEqualTo(order1);
	}
}