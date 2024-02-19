package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.repository.OrderPlacedRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderPlacedServiceImplTests {
	@Mock
	private OrderPlacedRepository orderPlacedRepository;

	@InjectMocks
	private OrderPlacedServiceImpl orderPlacedService;

	@Test
	void OrderPlacedService_FindOrderByEmail_ReturnCorrectList() {
		// Arrange
		ProductModel productModel = new ProductModel(
			"iPhone 11",
			"Apple",
			"Smartphone"
		);

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

		User user = new User(
			"Mario",
			"Rossi",
			"email@email.com",
			"password",
			null,
			UserRole.CLIENTE,
			null
		);

		OrderPlaced order1 = new OrderPlaced(
			"Via Roma 1",
			user.getEmail(),
			new Date(),
			250.0,
			false,
			user
		);
		order1.setId(UUID.randomUUID());
		OrderItem orderItem1 = new OrderItem(
			1,
			productVariation.getPrice()*1,
			order1,
			productVariation
		);

		OrderPlaced order2 = new OrderPlaced(
			"Via Roma 1",
			user.getEmail(),
			new Date(),
			500.0,
			false,
			user
		);
		order2.setId(UUID.randomUUID());
		OrderItem orderItem2 = new OrderItem(
			2,
			productVariation.getPrice()*2,
			order2,
			productVariation
		);

		when(orderPlacedRepository.findByEmail(user.getEmail())).thenReturn(List.of(order1, order2));

		// Act
		List<OrderPlaced> foundOrders = orderPlacedService.findOrderByEmail(user.getEmail());

		// Assert
		assertThat(foundOrders).isNotNull();
		assertThat(foundOrders.size()).isEqualTo(2);
		assertThat(foundOrders).containsOnly(order1, order2);
		assertThat(foundOrders.get(0).getUser()).isEqualTo(user);
		assertThat(foundOrders.get(1).getUser()).isEqualTo(user);
	}

	@Test
	void OrderPlacedService_FindOrderByShipped_ReturnCorrectList() {
		// Arrange
		ProductModel productModel = new ProductModel(
			"iPhone 11",
			"Apple",
			"Smartphone"
		);

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

		User user = new User(
			"Mario",
			"Rossi",
			"email@email.com",
			"password",
			null,
			UserRole.CLIENTE,
			null
		);

		OrderPlaced order1 = new OrderPlaced(
			"Via Roma 1",
			user.getEmail(),
			new Date(),
			250.0,
			true,
			user
		);
		order1.setId(UUID.randomUUID());
		OrderItem orderItem1 = new OrderItem(
			1,
			productVariation.getPrice()*1,
			order1,
			productVariation
		);

		OrderPlaced order2 = new OrderPlaced(
			"Via Roma 1",
			user.getEmail(),
			new Date(),
			500.0,
			false,
			user
		);
		order2.setId(UUID.randomUUID());
		OrderItem orderItem2 = new OrderItem(
			2,
			productVariation.getPrice()*2,
			order2,
			productVariation
		);

		when(orderPlacedRepository.findByShipped(true)).thenReturn(List.of(order1));

		// Act
		List<OrderPlaced> foundOrders = orderPlacedService.findOrderByShipped(true);

		// Assert
		assertThat(foundOrders).isNotNull();
		assertThat(foundOrders.size()).isEqualTo(1);
		assertThat(foundOrders).containsOnly(order1);
		assertThat(foundOrders.get(0).getUser()).isEqualTo(user);
	}

	@Test
	void OrderPlacedService_FindOrderByDate_ReturnCorrectList() {
		// Arrange
		ProductModel productModel = new ProductModel(
			"iPhone 11",
			"Apple",
			"Smartphone"
		);

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

		User user = new User(
			"Mario",
			"Rossi",
			"email@email.com",
			"password",
			null,
			UserRole.CLIENTE,
			null
		);

		OrderPlaced order1 = new OrderPlaced(
			"Via Roma 1",
			user.getEmail(),
			new Date(),
			250.0,
			true,
			user
		);
		order1.setId(UUID.randomUUID());
		OrderItem orderItem1 = new OrderItem(
			1,
			productVariation.getPrice()*1,
			order1,
			productVariation
		);

		OrderPlaced order2 = new OrderPlaced(
			"Via Roma 1",
			user.getEmail(),
			new Date(),
			500.0,
			false,
			user
		);
		order2.setId(UUID.randomUUID());
		OrderItem orderItem2 = new OrderItem(
			2,
			productVariation.getPrice()*2,
			order2,
			productVariation
		);

		when(orderPlacedRepository.findByOrderDate(order1.getOrderDate())).thenReturn(List.of(order1));

		// Act
		List<OrderPlaced> foundOrders = orderPlacedService.findOrderByDate(order1.getOrderDate());

		// Assert
		assertThat(foundOrders).isNotNull();
		assertThat(foundOrders.size()).isEqualTo(1);
		assertThat(foundOrders).containsOnly(order1);
		assertThat(foundOrders.get(0).getUser()).isEqualTo(user);
	}
}