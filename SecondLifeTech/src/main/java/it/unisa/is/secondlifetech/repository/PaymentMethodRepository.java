package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Interfaccia che rappresenta la repository per la gestione dei metodi di pagamento.
 */
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {
	List<PaymentMethod> findByUserId(UUID userId);
}
