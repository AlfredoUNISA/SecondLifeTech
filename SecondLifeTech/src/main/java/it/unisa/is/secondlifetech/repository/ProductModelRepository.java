package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductModelRepository extends JpaRepository<ProductModel, UUID> {
}
