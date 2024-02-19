package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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
	private Date orderDate;
	@Column(nullable = false)
	private double total;
	@Column(nullable = false)
	private boolean isShipped;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderItem> orderItems;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public OrderPlaced(String address, String email, Date orderDate, double total, boolean isShipped) {
		this.address = address;
		this.email = email;
		this.orderDate = orderDate;
		this.total = total;
		this.isShipped = isShipped;
	}
}
