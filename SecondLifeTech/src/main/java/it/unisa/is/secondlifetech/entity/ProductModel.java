package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ProductModel {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private String brand;

	@Column(nullable = false)
	private String category;

	@OneToMany(mappedBy = "productModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductVariation> variations = new ArrayList<>();

	@OneToOne
	private ImageFile imageFile;

	public ProductModel(String name, String brand, String category) {
		this.name = name;
		this.brand = brand;
		this.category = category;
	}


}
