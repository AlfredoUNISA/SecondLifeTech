package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Interfaccia per la gestione dei dati relativi al carrello degli utenti.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
	Cart findByUserId(UUID userId);
}
