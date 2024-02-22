package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
// Per evitare una ricorsione infinita nei log, non aggiungere @ToString!
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
	@JoinColumn(name = "order_placed_id", nullable = false)
	private OrderPlaced orderPlaced;

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

	public OrderItem(int quantityOrdered, double subTotal, OrderPlaced orderPlaced, ProductVariation productVariation) {
		this.quantityOrdered = quantityOrdered;
		this.subTotal = subTotal;
		this.orderPlaced = orderPlaced;
		this.productVariation = productVariation;
	}

	public OrderItem(int quantityOrdered, double subTotal, OrderPlaced orderPlaced, String modelName, String brand, String category, String year, String ram, String displaySize, String storageSize, String color, String state) {
		this.quantityOrdered = quantityOrdered;
		this.subTotal = subTotal;
		this.orderPlaced = orderPlaced;
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

	// ToString manuale per evitare ricorsione infinita nei log
	@Override
	public String toString() {
		String toReturn = "OrderItem{" +
			"id=" + id +
			", orderPlacedId=" + orderPlaced.getId() +
			", quantityOrdered=" + quantityOrdered +
			", subTotal=" + subTotal;

		if (productVariation != null) {
			toReturn += ", productVariation=" + productVariation;
		} else {
			toReturn += ", modelName='" + modelName + '\'' +
				", brand='" + brand + '\'' +
				", category='" + category + '\'' +
				", year='" + year + '\'' +
				", ram='" + ram + '\'' +
				", displaySize='" + displaySize + '\'' +
				", storageSize='" + storageSize + '\'' +
				", color='" + color + '\'' +
				", state='" + state + '\'';
		}

		return toReturn + '}';
	}
}
