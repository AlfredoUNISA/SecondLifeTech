package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Classe che rappresenta un indirizzo di spedizione.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
// Per evitare una ricorsione infinita nei log, non aggiungere @ToString!
@Entity
public class ShippingAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String street;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String state;

	@Column(nullable = false)
	private String zipCode;

	@Column(nullable = false)
	private String country;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public ShippingAddress(String street, String city, String state, String zipCode, String country) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
	}

	/**
	 * Restituisce l'indirizzo completo.
	 */
	public String fullAddress() {
		return street + ", " + city + ", " + state + ", " + zipCode + ", " + country;
	}

	// ToString manuale per evitare ricorsione infinita nei log
	@Override
	public String toString() {
		return "ShippingAddress{" +
			"id=" + id +
			", userId=" + user.getId() +
			", street='" + street + '\'' +
			", city='" + city + '\'' +
			", state='" + state + '\'' +
			", zipCode='" + zipCode + '\'' +
			", country='" + country + '\'' +
			'}';
	}
}
