package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
// Per evitare una ricorsione infinita nei log, non aggiungere @ToString!
@Entity
public class OrderPlaced {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private Date date;
	@Column(nullable = false)
	private double total;
	@Column(nullable = false)
	private boolean shipped;

	@OneToMany(mappedBy = "orderPlaced", fetch = FetchType.LAZY)
	private List<OrderItem> items = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public OrderPlaced(String address, String email, Date date, double total, boolean isShipped, User user) {
		this.address = address;
		this.email = email;
		this.date = date;
		this.total = total;
		this.shipped = isShipped;
		this.user = user;
	}

	/**
	 * Aggiunge un oggetto OrderItem alla lista di oggetti.
	 */
	public void addItem(OrderItem item) {
		this.items.add(item);
		item.setOrderPlaced(this);
	}

	@Override
	public String toString() {
		return "OrderPlaced{" +
			"id=" + id +
			", userId=" + user.getId() +
			", address='" + address + '\'' +
			", email='" + email + '\'' +
			", date=" + date +
			", total=" + total +
			", shipped=" + shipped +
			", items=" + items +
			'}';
	}
}
