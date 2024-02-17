package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductModelRepository extends JpaRepository<ProductModel, UUID> {
	ProductModel findByName(String name);
	List<ProductModel> findByBrand(String brand);
	List<ProductModel> findByCategory(String category);

}
