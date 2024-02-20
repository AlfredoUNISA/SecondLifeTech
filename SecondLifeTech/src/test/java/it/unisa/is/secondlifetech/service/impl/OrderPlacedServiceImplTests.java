package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.repository.OrderPlacedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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

	private OrderPlaced orderPlaced;
	private User user;

	@BeforeEach
	void setup() {
		user = User.builder()
			.id(UUID.randomUUID())
			.email("email@email.com")
			.orders(new ArrayList<>())
			.build();

		// Focus del testing
		orderPlaced = OrderPlaced.builder()
			.id(UUID.randomUUID())
			.items(new ArrayList<>())
			.user(user)
			.shipped(true)
			.date(new Date())
			.build();
		user.getOrders().add(orderPlaced);

		OrderItem orderItem = OrderItem.builder()
			.id(UUID.randomUUID())
			.quantityOrdered(1)
			.subTotal(10.0)
			.orderPlaced(orderPlaced)
			.build();
		orderPlaced.getItems().add(orderItem);
	}

	@Test
	void OrderPlacedService_FindOrderByEmail_ShouldReturnCorrectList() {
		// Arrange
		String email = user.getEmail();

		when(orderPlacedRepository.findByEmail(email)).thenReturn(user.getOrders());

		// Act
		List<OrderPlaced> result = orderPlacedService.findOrderByEmail(email);

		// Assert
		assertThat(result).hasSize(1);
		assertThat(result).containsOnly(orderPlaced);
	}

	@Test
	void OrderPlacedService_FindOrderByShipped_ShouldReturnCorrectList() {
		// Arrange
		boolean shipped = orderPlaced.isShipped();

		when(orderPlacedRepository.findByShipped(shipped)).thenReturn(List.of(orderPlaced));

		// Act
		List<OrderPlaced> result = orderPlacedService.findOrderByShipped(shipped);

		// Assert
		assertThat(result).hasSize(1);
		assertThat(result).containsOnly(orderPlaced);
	}

	@Test
	void OrderPlacedService_FindOrderByDate_ShouldReturnCorrectList() {
		// Arrange
		Date date = orderPlaced.getDate();

		when(orderPlacedRepository.findByDate(date)).thenReturn(List.of(orderPlaced));

		// Act
		List<OrderPlaced> result = orderPlacedService.findOrderByDate(date);

		// Assert
		assertThat(result).hasSize(1);
		assertThat(result).containsOnly(orderPlaced);
	}
}