package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private double total;

	@OneToOne(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, optional = false)
	private User user;

	@Builder.Default
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> items = new ArrayList<>();

	/**
	 * Aggiunge e imposta un nuovo oggetto CartItem al carrello.
	 */
	public void addItem(CartItem item) {
		items.add(item);
		item.setCart(this);
	}

	public Cart(double total, User user) {
		this.total = total;
		this.user = user;
	}
}
