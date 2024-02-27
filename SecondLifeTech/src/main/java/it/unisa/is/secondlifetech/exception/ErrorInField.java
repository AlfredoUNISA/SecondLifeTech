package it.unisa.is.secondlifetech.exception;

public class ErrorInField extends Exception {
	public ErrorInField(String message) {
		super("Errore in un campo di un form inserito dall'utente: " + message);
	}
}
