package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.repository.OrderItemRepository;
import it.unisa.is.secondlifetech.repository.OrderPlacedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTests {
	@Mock
	private OrderPlacedRepository orderPlacedRepository;

	@Mock
	private OrderItemRepository orderItemRepository;

	@InjectMocks
	private OrderServiceImpl orderService;

	private OrderPlaced order;
	private OrderItem orderItem;
	private ProductVariation productVariation;

	@BeforeEach
	void setup() {
		productVariation = ProductVariation.builder()
			.id(UUID.randomUUID())
			.price(10.0)
			.quantityInStock(5)
			.build();

		orderItem = new OrderItem();
		orderItem.setProductVariation(productVariation);
		orderItem.setQuantityOrdered(2);

		order = new OrderPlaced();
		order.setId(UUID.randomUUID());
		order.setItems(Collections.singletonList(orderItem));
	}


	// ================================================================================================================
	// =============== CREATE ==========================================================================================
	// ================================================================================================================

	@Test
	void OrderServiceImpl_createAndPlaceNewOrder_WhenOrderIsGiven_ShouldCreateAndPlaceNewOrder() {
		// Arrange
		when(orderPlacedRepository.save(order)).thenReturn(order);
		when(orderItemRepository.saveAll(order.getItems())).thenReturn(order.getItems());

		// Act
		OrderPlaced result = orderService.createAndPlaceNewOrder(order);

		// Assert
		assertThat(result).isEqualTo(order);
		verify(orderPlacedRepository).save(order);
		verify(orderItemRepository).saveAll(order.getItems());
	}



	// ================================================================================================================
	// =============== READ ============================================================================================
	// ================================================================================================================

	@Test
	void OrderServiceImpl_findOrderById_WhenIdIsGiven_ShouldReturnOrder() {
		// Arrange
		when(orderPlacedRepository.findById(order.getId())).thenReturn(java.util.Optional.ofNullable(order));

		// Act
		OrderPlaced result = orderService.findOrderById(order.getId());

		// Assert
		assertThat(result).isEqualTo(order);
		verify(orderPlacedRepository).findById(order.getId());
	}

	@Test
	void OrderServiceImpl_findOrderItemById_WhenIdIsGiven_ShouldReturnOrderItem() {
		// Arrange
		when(orderItemRepository.findById(orderItem.getId())).thenReturn(java.util.Optional.ofNullable(orderItem));

		// Act
		OrderItem result = orderService.findOrderItemById(orderItem.getId());

		// Assert
		assertThat(result).isEqualTo(orderItem);
		verify(orderItemRepository).findById(orderItem.getId());
	}

	@Test
	void OrderServiceImpl_findOrderByEmail_WhenEmailIsGiven_ShouldReturnOrders() {
		// Arrange
		String email = "test@example.com";
		when(orderPlacedRepository.findByEmail(email)).thenReturn(Collections.singletonList(order));

		// Act
		List<OrderPlaced> result = orderService.findOrderByEmail(email);

		// Assert
		assertThat(result).contains(order);
		verify(orderPlacedRepository).findByEmail(email);
	}

	@Test
	void OrderServiceImpl_findOrderByShipped_WhenShippedIsGiven_ShouldReturnOrders() {
		// Arrange
		boolean shipped = true;
		when(orderPlacedRepository.findByShipped(shipped)).thenReturn(Collections.singletonList(order));

		// Act
		List<OrderPlaced> result = orderService.findOrderByShipped(shipped);

		// Assert
		assertThat(result).contains(order);
		verify(orderPlacedRepository).findByShipped(shipped);
	}

	@Test
	void OrderServiceImpl_findOrderByDate_WhenDateIsGiven_ShouldReturnOrders() {
		// Arrange
		Date date = new Date();
		when(orderPlacedRepository.findByDate(date)).thenReturn(Collections.singletonList(order));

		// Act
		List<OrderPlaced> result = orderService.findOrderByDate(date);

		// Assert
		assertThat(result).contains(order);
		verify(orderPlacedRepository).findByDate(date);
	}

	@Test
	void OrderServiceImpl_findOrderItemsByProductVariation_WhenProductVariationIsGiven_ShouldReturnOrderItems() {
		// Arrange
		when(orderItemRepository.findByProductVariationId(productVariation.getId())).thenReturn(Collections.singletonList(orderItem));

		// Act
		List<OrderItem> result = orderService.findOrderItemsByProductVariation(productVariation);

		// Assert
		assertThat(result).contains(orderItem);
		verify(orderItemRepository).findByProductVariationId(productVariation.getId());
	}

	@Test
	void OrderServiceImpl_findAllOrders_WhenCalled_ShouldReturnAllOrders() {
		// Arrange
		when(orderPlacedRepository.findAll()).thenReturn(Collections.singletonList(order));

		// Act
		List<OrderPlaced> result = orderService.findAllOrders();

		// Assert
		assertThat(result).contains(order);
		verify(orderPlacedRepository).findAll();
	}

	@Test
	void OrderServiceImpl_findAllOrderItems_WhenCalled_ShouldReturnAllOrderItems() {
		// Arrange
		when(orderItemRepository.findAll()).thenReturn(Collections.singletonList(orderItem));

		// Act
		List<OrderItem> result = orderService.findAllOrderItems();

		// Assert
		assertThat(result).contains(orderItem);
		verify(orderItemRepository).findAll();
	}

	// ================================================================================================================
	// =============== UPDATE ==========================================================================================
	// ================================================================================================================

	@Test
	void OrderServiceImpl_UpdateOrder_WhenOrderIsGiven_ShouldUpdateOrder() {
		// Arrange
		when(orderPlacedRepository.save(any(OrderPlaced.class))).thenReturn(order);

		// Act
		OrderPlaced updatedOrder = orderService.updateOrder(order);

		// Assert
		assertThat(updatedOrder).isEqualTo(order);
		verify(orderPlacedRepository).save(order);
	}

	@Test
	void OrderServiceImpl_UpdateOrderItem_WhenOrderItemIsGiven_ShouldUpdateOrderItem() {
		// Arrange
		when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);

		// Act
		OrderItem updatedOrderItem = orderService.updateOrderItem(orderItem);

		// Assert
		assertThat(updatedOrderItem).isEqualTo(orderItem);
		verify(orderItemRepository).save(orderItem);
	}



	// ================================================================================================================
	// =============== DELETE ==========================================================================================
	// ================================================================================================================

	@Test
	void OrderServiceImpl_deleteOrder_WhenCalled_ShouldDeleteOrderAndItsItems() {
		// Arrange
		// Act
		orderService.deleteOrder(order);

		// Assert
		verify(orderItemRepository).deleteAll(order.getItems());
		verify(orderPlacedRepository).delete(order);
	}

	@Test
	void OrderServiceImpl_deleteOrderItem_WhenCalled_ShouldDeleteOrderItem() {
		// Arrange
		// Act
		orderService.deleteOrderItem(orderItem);

		// Assert
		verify(orderItemRepository).delete(orderItem);
	}

}