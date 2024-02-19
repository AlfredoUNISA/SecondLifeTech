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
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private int quantityOrdered;

	@Column(nullable = false)
	private double subTotal;

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private OrderPlaced order;

	@ManyToOne
	@JoinColumn(name = "product_variation_id")
	private ProductVariation productVariation;

	// Colonne nullable=true in caso il prodotto è stato eliminato
	private String modelName;
	private String brand;
	private String category;
	private String year;
	private String ram;
	private String displaySize;
	private String storageSize;
	private String color;
	private String state;

	public OrderItem(int quantityOrdered, double subTotal, OrderPlaced order, ProductVariation productVariation) {
		this.quantityOrdered = quantityOrdered;
		this.subTotal = subTotal;
		this.order = order;
		this.productVariation = productVariation;
	}

	public OrderItem(int quantityOrdered, double subTotal, OrderPlaced order, String modelName, String brand, String category, String year, String ram, String displaySize, String storageSize, String color, String state) {
		this.quantityOrdered = quantityOrdered;
		this.subTotal = subTotal;
		this.order = order;
		this.modelName = modelName;
		this.brand = brand;
		this.category = category;
		this.year = year;
		this.ram = ram;
		this.displaySize = displaySize;
		this.storageSize = storageSize;
		this.color = color;
		this.state = state;
	}
}
