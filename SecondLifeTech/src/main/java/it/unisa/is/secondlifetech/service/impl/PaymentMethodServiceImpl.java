package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.PaymentMethod;
import it.unisa.is.secondlifetech.repository.PaymentMethodRepository;
import it.unisa.is.secondlifetech.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

	private final PaymentMethodRepository paymentMethodRepository;

	@Autowired
	public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
		this.paymentMethodRepository = paymentMethodRepository;
	}

	/**
	 * Salva un metodo di pagamento nel database.
	 *
	 * @param paymentMethod l'oggetto PaymentMethod da salvare
	 * @return l'oggetto PaymentMethod salvato
	 */
	@Override
	public PaymentMethod createNewPaymentMethod(PaymentMethod paymentMethod) {
		return paymentMethodRepository.save(paymentMethod);
	}

	/**
	 * Ottiene un metodo di pagamento dal database tramite l'ID.
	 *
	 * @param id l'ID del metodo di pagamento da cercare
	 * @return l'oggetto PaymentMethod corrispondente all'ID specificato, o null se non trovato
	 */
	@Override
	public PaymentMethod findPaymentMethodById(UUID id) {
		return paymentMethodRepository.findById(id).orElse(null);
	}

	/**
	 * Ottiene tutti i metodi di pagamento di un utente dal database tramite l'ID dell'utente.
	 *
	 * @param userId l'ID dell'utente di cui cercare i metodi di pagamento
	 * @return una lista di oggetti PaymentMethod corrispondenti all'ID specificato
	 */
	@Override
	public List<PaymentMethod> findPaymentMethodsByUser(UUID userId) {
		return paymentMethodRepository.findByUserId(userId);
	}

	/**
	 * Aggiorna le informazioni di un metodo di pagamento nel database.
	 *
	 * @param id            l'ID del metodo di pagamento da aggiornare
	 * @param paymentMethod l'oggetto PaymentMethod con le nuove informazioni da salvare
	 * @return l'oggetto PaymentMethod aggiornato
	 */
	@Override
	public PaymentMethod updatePaymentMethod(UUID id, PaymentMethod paymentMethod) {
		paymentMethod.setId(id);
		return paymentMethodRepository.save(paymentMethod);
	}

	/**
	 * Elimina un metodo di pagamento dal database tramite l'ID.
	 *
	 * @param id l'ID del metodo di pagamento da eliminare
	 */
	@Override
	public void deletePaymentMethod(UUID id) {
		paymentMethodRepository.deleteById(id);
	}
}
