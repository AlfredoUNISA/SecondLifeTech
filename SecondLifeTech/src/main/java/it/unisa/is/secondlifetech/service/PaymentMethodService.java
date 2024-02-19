package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.PaymentMethod;

import java.util.List;
import java.util.UUID;

public interface PaymentMethodService {
	/**
	 * Salva un metodo di pagamento nel database.
	 *
	 * @param paymentMethod l'oggetto PaymentMethod da salvare
	 * @return l'oggetto PaymentMethod salvato
	 */
	PaymentMethod createNewPaymentMethod(PaymentMethod paymentMethod);

	/**
	 * Ottiene un metodo di pagamento dal database tramite l'ID.
	 *
	 * @param id l'ID del metodo di pagamento da cercare
	 * @return l'oggetto PaymentMethod corrispondente all'ID specificato, o null se non trovato
	 */
	PaymentMethod findPaymentMethodById(UUID id);

	/**
	 * Ottiene tutti i metodi di pagamento di un utente dal database tramite l'ID dell'utente.
	 * @param userId l'ID dell'utente di cui cercare i metodi di pagamento
	 * @return una lista di oggetti PaymentMethod corrispondenti all'ID specificato
	 */
	List<PaymentMethod> findPaymentMethodsByUser(UUID userId);

	/**
	 * Aggiorna le informazioni di un metodo di pagamento nel database.
	 *
	 * @param id            l'ID del metodo di pagamento da aggiornare
	 * @param paymentMethod l'oggetto PaymentMethod con le nuove informazioni da salvare
	 * @return l'oggetto PaymentMethod aggiornato
	 */
	PaymentMethod updatePaymentMethod(UUID id, PaymentMethod paymentMethod);

	/**
	 * Elimina un metodo di pagamento dal database tramite l'ID.
	 *
	 * @param id l'ID del metodo di pagamento da eliminare
	 */
	void deletePaymentMethod(UUID id);
}
