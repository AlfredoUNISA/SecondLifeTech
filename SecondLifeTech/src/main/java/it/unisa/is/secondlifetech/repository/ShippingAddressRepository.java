package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Interfaccia che rappresenta la repository per la gestione degli indirizzi di spedizione.
 */
@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, UUID> {
	List<ShippingAddress> findByUserId(UUID userId);
}
