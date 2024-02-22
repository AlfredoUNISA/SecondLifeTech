package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
	List<CartItem> findByCartId(UUID cartId);

	List<CartItem> findByProductVariationId(UUID productVariationId);
}
