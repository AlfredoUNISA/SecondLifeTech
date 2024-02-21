package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Classe che rappresenta un utente.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	//@Column(nullable = false)
	private Date birthDate;

	@Column(nullable = false)
	private String role;

	private String phoneNumber;

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<ShippingAddress> shippingAddresses = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<PaymentMethod> paymentMethods = new ArrayList<>();

	@OneToOne
	private Cart cart;

	@Builder.Default
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<OrderPlaced> orders = new ArrayList<>();

	public User(String firstName, String lastName, String email, String password, Date birthDate, String role, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.role = role;
		this.phoneNumber = phoneNumber;
	}
}
