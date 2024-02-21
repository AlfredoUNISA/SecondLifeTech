package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;

	@ManyToOne
	@JoinColumn(name = "product_variation_id")
	private ProductVariation productVariation;

	private int quantity;

	private double subTotal;

	public CartItem(ProductVariation productVariation, int quantity, double subTotal) {
		this.productVariation = productVariation;
		this.quantity = quantity;
		this.subTotal = subTotal;
	}

	public CartItem(Cart cart, ProductVariation productVariation, int quantity, double subTotal) {
		this.cart = cart;
		this.productVariation = productVariation;
		this.quantity = quantity;
		this.subTotal = subTotal;
	}
}
