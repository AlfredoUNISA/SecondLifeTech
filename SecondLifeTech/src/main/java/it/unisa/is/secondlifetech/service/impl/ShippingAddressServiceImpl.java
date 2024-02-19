package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.ShippingAddress;
import it.unisa.is.secondlifetech.repository.ShippingAddressRepository;
import it.unisa.is.secondlifetech.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementazione del servizio per la gestione degli indirizzi di spedizione.
 */
@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {
	private final ShippingAddressRepository shippingAddressRepository;

	@Autowired
	public ShippingAddressServiceImpl(ShippingAddressRepository shippingAddressRepository) {
		this.shippingAddressRepository = shippingAddressRepository;
	}

	/**
	 * Salva un indirizzo di spedizione nel database.
	 *
	 * @param shippingAddress l'oggetto ShippingAddress da salvare
	 * @return l'oggetto ShippingAddress salvato
	 */
	@Override
	public ShippingAddress createNewShippingAddress(ShippingAddress shippingAddress) {
		return shippingAddressRepository.save(shippingAddress);
	}

	/**
	 * Ottiene un indirizzo di spedizione dal database tramite l'ID.
	 *
	 * @param id l'ID dell'indirizzo di spedizione da cercare
	 * @return l'oggetto ShippingAddress corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public ShippingAddress findShippingAddressById(UUID id) {
		return shippingAddressRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene tutti gli indirizzi di spedizione di un utente dal database tramite l'ID dell'utente.
	 *
	 * @param userId l'ID dell'utente di cui cercare gli indirizzi di spedizione
	 * @return una lista di oggetti ShippingAddress corrispondenti all'ID specificato
	 */
	@Override
	public List<ShippingAddress> findShippingAddressesByUser(UUID userId) {
		return shippingAddressRepository.findByUserId(userId);
	}

	/**
	 * Aggiorna le informazioni di un indirizzo di spedizione nel database.
	 *
	 * @param id              l'ID dell'indirizzo di spedizione da aggiornare
	 * @param shippingAddress l'oggetto ShippingAddress con le nuove informazioni da salvare
	 * @return l'oggetto ShippingAddress aggiornato
	 */
	@Override
	public ShippingAddress updateShippingAddress(UUID id, ShippingAddress shippingAddress) {
		shippingAddress.setId(id);
		return shippingAddressRepository.save(shippingAddress);
	}

	/**
	 * Elimina un indirizzo di spedizione dal database tramite l'ID.
	 *
	 * @param id l'ID dell'indirizzo di spedizione da eliminare
	 */
	@Override
	public void deleteShippingAddress(UUID id) {
		shippingAddressRepository.deleteById(id);
	}
}
