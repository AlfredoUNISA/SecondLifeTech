package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.exception.NoItemsForFinalizationException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
	private ProductModel productModel;
	private ProductVariation productVariation;
	private User user;
	private Cart cart;

	@BeforeEach
	void setup() {
		user = new User();
		user.setId(UUID.randomUUID());
		user.setEmail("email@email.com");
		user.setRole(UserRole.CLIENTE);

		cart = new Cart();
		cart.setId(UUID.randomUUID());
		cart.setUser(user);
		user.setCart(cart);

		productModel = new ProductModel();
		productModel.setId(UUID.randomUUID());
		productModel.setName("Product");
		productModel.setBrand("Brand");
		productModel.setCategory(ProductCategory.SMARTPHONE);

		productVariation = new ProductVariation();
		productVariation.setId(UUID.randomUUID());
		productVariation.setYear(2021);
		productVariation.setRam(4);
		productVariation.setDisplaySize(5.5);
		productVariation.setStorageSize(64);
		productVariation.setPrice(230);
		productVariation.setQuantityInStock(10);
		productVariation.setColor("Black");
		productVariation.setState(ProductState.BUONO);
		productVariation.setModel(productModel);

		orderItem = new OrderItem();
		orderItem.setId(UUID.randomUUID());
		orderItem.setProductVariation(productVariation);
		orderItem.setQuantityOrdered(1);
		orderItem.setOrderPlaced(order);
		orderItem.setSubTotal(230);

		order = new OrderPlaced();
		order.setId(UUID.randomUUID());

	}


	// ================================================================================================================
	// =============== TEST CASE 1 =====================================================================================
	// ================================================================================================================

	/**
	 * <li>Deve essere creato un OrderPlaced contenente un OrderItem a cui è assegnato la ProductVariation inserita attraverso la sua UUID</li>
	 * <li>Il Cart non deve contenere alcun CartItem</li>
	 */
	@Test
	void OrderTC1_createAndPlaceNewOrder_WhenOrderIsValid_ShouldReturnCreatedOrder() {
		// Arrange
		order.setId(null);
		order.setEmail(user.getEmail());
		order.setAddress("Via Giancarlo Siani 12");
		order.setDate(new Date());
		order.setTotal(230);
		order.setShipped(false);
		order.setUser(user);
		order.setItems(List.of(orderItem));

		when(orderPlacedRepository.save(any(OrderPlaced.class))).thenReturn(order);
		when(orderItemRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

		// Act
		OrderPlaced result = orderService.createAndPlaceNewOrder(order);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getItems().get(0).getProductVariation()).isEqualTo(productVariation);
	}

	@Test
	void OrderTC1E_createAndPlaceNewOrder_WhenOrderIsNull_ShouldReturnNull() {
		// Act
		OrderPlaced result = orderService.createAndPlaceNewOrder(null);

		// Assert
		assertThat(result).isNull();
	}

	@Test
	void OrderTC1E_createAndPlaceNewOrder_WhenOrderIdIsNotNull_ShouldThrowIllegalArgumentException() {
		// Arrange
		OrderPlaced order = new OrderPlaced();
		order.setId(UUID.randomUUID());

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> orderService.createAndPlaceNewOrder(order));
	}



	// ================================================================================================================
	// =============== TEST CASE 4 =====================================================================================
	// ================================================================================================================

	/**
	 * L’OrderPlaced deve avere lo stato di spedizione a true
	 */
	@Test
	void OrderTC2_setOrderAsShipped_WhenOrderExists_ShouldSetOrderAsShipped() {
		// Arrange
		order.setShipped(false);

		// Act
		orderService.setOrderAsShipped(order);

		// Assert
		assertThat(order.isShipped()).isTrue();
	}

	@Test
	void OrderTC2E_setOrderAsShipped_WhenOrderIsNull_ShouldThrowNullPointerException() {
		// Act & Assert
		assertThrows(NullPointerException.class, () -> orderService.setOrderAsShipped(null));
	}



}