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
@ToString
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
	// Salvato come UUID nel database, ma è necessario passare un oggetto User per la creazione
	private User user;

}
