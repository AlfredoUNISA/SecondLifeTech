package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.repository.OrderItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceImplTests {
	@Mock
	private OrderItemRepository orderItemRepository;

	@InjectMocks
	private OrderItemServiceImpl orderItemService;

	private OrderPlaced order;
	private OrderItem orderItem;

	@BeforeEach
	void setup() {
		order = OrderPlaced.builder()
			.id(UUID.randomUUID())
			.items(new ArrayList<>())
			.build();

		// Focus del testing
		orderItem = OrderItem.builder()
			.id(UUID.randomUUID())
			.orderPlaced(order)
			.build();
		order.getItems().add(orderItem);
	}

	@Test
	void OrderItemService_FindOrderItemsByOrder_ShouldReturnCorrectList() {
		// Arrange
		UUID orderId = order.getId();

		when(orderItemRepository.findByOrderPlacedId(orderId)).thenReturn(order.getItems());

		// Act
		List<OrderItem> result = orderItemService.findOrderItemsByOrderPlaced(orderId);

		// Assert
		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isEqualTo(orderItem);
	}
}