package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.ShippingAddress;

import java.util.List;
import java.util.UUID;

/**
 * Interfaccia per il servizio di gestione degli indirizzi di spedizione.
 */
public interface ShippingAddressService {
	/**
	 * Salva un indirizzo di spedizione nel database.
	 *
	 * @param shippingAddress l'oggetto ShippingAddress da salvare
	 * @return l'oggetto ShippingAddress salvato
	 */
	ShippingAddress createNewShippingAddress(ShippingAddress shippingAddress);

	/**
	 * Ottiene un indirizzo di spedizione dal database tramite l'ID.
	 *
	 * @param id l'ID dell'indirizzo di spedizione da cercare
	 * @return l'oggetto ShippingAddress corrispondente all'ID specificato, o null se non trovato
	 */
	ShippingAddress findShippingAddressById(UUID id);

	/**
	 * Ottiene tutti gli indirizzi di spedizione di un utente dal database tramite l'ID dell'utente.
	 *
	 * @param userId l'ID dell'utente di cui cercare gli indirizzi di spedizione
	 * @return una lista di oggetti ShippingAddress corrispondenti all'ID specificato
	 */
	List<ShippingAddress> findShippingAddressesByUser(UUID userId);

	/**
	 * Aggiorna le informazioni di un indirizzo di spedizione nel database.
	 *
	 * @param id              l'ID dell'indirizzo di spedizione da aggiornare
	 * @param shippingAddress l'oggetto ShippingAddress con le nuove informazioni da salvare
	 * @return l'oggetto ShippingAddress aggiornato
	 */
	ShippingAddress updateShippingAddress(UUID id, ShippingAddress shippingAddress);

	/**
	 * Elimina un indirizzo di spedizione dal database tramite l'ID.
	 *
	 * @param id l'ID dell'indirizzo di spedizione da eliminare
	 */
	void deleteShippingAddress(UUID id);
}
