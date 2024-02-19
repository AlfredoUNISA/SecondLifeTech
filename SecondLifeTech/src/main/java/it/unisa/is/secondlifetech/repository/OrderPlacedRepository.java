package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.OrderPlaced;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderPlacedRepository extends JpaRepository<OrderPlaced, UUID> {
	List<OrderPlaced> findByEmail(String email);
	List<OrderPlaced> findByShipped(boolean isShipped);
	List<OrderPlaced> findByOrderDate(Date orderDate);

}
