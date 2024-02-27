package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.OrderPlaced;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderPlacedRepository extends JpaRepository<OrderPlaced, UUID> {
	List<OrderPlaced> findByEmail(String email);
	Page<OrderPlaced> findByEmail(String email, Pageable page);
	List<OrderPlaced> findByShipped(boolean shipped);
	Page<OrderPlaced> findByShipped(boolean shipped, Pageable page);
	List<OrderPlaced> findByDate(Date date);
	Page<OrderPlaced> findByDate(Date date, Pageable page);

}
