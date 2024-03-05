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
public class ProductVariation {
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

	@ManyToOne
	@JoinColumn(name = "product_model_id", nullable = false)
	private ProductModel model;

	public ProductVariation(int year, int ram, double displaySize, int storageSize, double price, int quantityInStock, String color, String state, ProductModel model) {
		this.year = year;
		this.ram = ram;
		this.displaySize = displaySize;
		this.storageSize = storageSize;
		this.price = price;
		this.quantityInStock = quantityInStock;
		this.color = color;
		this.state = state;
		this.model = model;
	}

	public String getTwoDigitPrice() {
		return String.format("%.2f", price);
	}

	// ToString manuale per evitare ricorsione infinita nei log
	@Override
	public String toString() {
		return "ProductVariation{" +
			"id=" + id +
			", model=" + model.getId() +
			", year=" + year +
			", ram=" + ram +
			", displaySize=" + displaySize +
			", storageSize=" + storageSize +
			", price=" + price +
			", quantityInStock=" + quantityInStock +
			", color='" + color + '\'' +
			", state='" + state + '\'' +
			'}';
	}


}
