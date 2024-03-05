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

	@Column(nullable = false)
	private double total = 0;

	@OneToOne(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, optional = false)
	private User user;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<CartItem> items = new ArrayList<>();

	/**
	 * Aggiunge un nuovo oggetto CartItem al carrello, aggiorna il totale del carrello
	 * e aggancia il carrello al CartItem.
	 *
	 * @param item l'oggetto CartItem da aggiungere
	 */
	public void addItem(CartItem item) {
		items.add(item);
		item.setCart(this);
		this.total += item.getSubTotal();
	}

	/**
	 * Rimuove un oggetto CartItem dal carrello, aggiorna il totale del carrello
	 * e sgancia il carrello dal CartItem.
	 *
	 * @param item l'oggetto CartItem da rimuovere
	 */
	public void removeItem(CartItem item) {
		items.remove(item);
		item.setCart(null);
		this.total -= item.getSubTotal();
	}

	/**
	 * Svuota il carrello, rimuovendo tutti gli oggetti CartItem e azzerando il totale.
	 */
	public void clear() {
		items.clear();
		this.total = 0;
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
