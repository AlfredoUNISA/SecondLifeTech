package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
	List<OrderItem> findByOrderPlacedId(UUID orderId);
}
