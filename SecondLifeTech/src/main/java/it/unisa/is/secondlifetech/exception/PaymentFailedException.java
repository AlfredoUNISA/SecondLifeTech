package it.unisa.is.secondlifetech.exception;

public class PaymentFailedException extends Exception {
	public PaymentFailedException() {
		super("Il pagamento non è andato a buon fine");
	}
}
