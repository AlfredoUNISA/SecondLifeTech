package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.repository.OrderItemRepository;
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
class OrderItemServiceImplTests {
	@Mock
	private OrderItemRepository orderItemRepository;

	@InjectMocks
	private OrderItemServiceImpl orderItemService;

	@Test
	void OrderItemService_FindOrderItemsByOrder_ReturnCorrectList() {
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

		when(orderItemRepository.findByOrderId(order1.getId())).thenReturn(List.of(orderItem1));

		// Act
		List<OrderItem> result = orderItemService.findOrderItemsByOrder(order1.getId());

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0)).isEqualTo(orderItem1);
	}
}