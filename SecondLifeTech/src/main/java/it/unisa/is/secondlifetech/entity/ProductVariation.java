package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ProductVariation{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private int year;

	@Column(nullable = false)
	private int ram;

	@Column(nullable = false)
	private double displaySize;

	@Column(nullable = false)
	private int storageSize;

	@Column(nullable = false)
	private double price;

	@Column(nullable = false)
	private int quantityInStock;

	@Column(nullable = false)
	private String color;

	@Column(nullable = false)
	private String state;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_model_id", nullable = false)
	private ProductModel productModel;



}
