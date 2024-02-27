package it.unisa.is.secondlifetech.exception;

public class NoShippingAddressException extends Exception {
	public NoShippingAddressException() {
		super("Nessun indirizzo di spedizione disponibile per un ordine");
	}
}
