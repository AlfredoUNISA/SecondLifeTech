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
// Per evitare una ricorsione infinita nei log, non aggiungere @ToString!
@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Builder.Default
	@Column(nullable = false)
	private double total = 0;

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

	public Cart(double total) {
		this.total = total;
	}


	// ToString manuale per evitare ricorsione infinita nei log
	@Override
	public String toString() {
		return "Cart{" +
			"id=" + id +
			", userId=" + user.getId() +
			", total=" + total +
			", items=" + items +
			'}';
	}
}
