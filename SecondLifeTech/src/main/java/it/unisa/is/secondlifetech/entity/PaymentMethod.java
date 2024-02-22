package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Classe che rappresenta un metodo di pagamento.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
// Per evitare una ricorsione infinita nei log, non aggiungere @ToString!
@Entity
public class PaymentMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String cardNumber;

	@Column(nullable = false)
	private String cardHolderName;

	@Column(nullable = false)
	private String expirationDate;

	@Column(nullable = false)
	private String cvv;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Override
	public String toString() {
		return "PaymentMethod{" +
			"id=" + id +
			", userId=" + user.getId() +
			", cardNumber='" + cardNumber + '\'' +
			", cardHolderName='" + cardHolderName + '\'' +
			", expirationDate='" + expirationDate + '\'' +
			", cvv='" + cvv + '\'' +
			'}';
	}
}
