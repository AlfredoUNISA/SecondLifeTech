package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductVariationRepository extends JpaRepository<ProductVariation, UUID> {
	List<ProductVariation> findByState(String state);
	List<ProductVariation> findByProductModelId(UUID productModelId);
}
