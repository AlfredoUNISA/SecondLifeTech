package it.unisa.is.secondlifetech.exception;

public class NoPaymentMethodException extends Exception {
	public NoPaymentMethodException() {
		super("Nessun metodo di pagamento disponibile per un ordine");
	}
}
