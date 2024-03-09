package it.unisa.is.secondlifetech.exception;

public class MissingRequiredFieldException extends Exception {
	public MissingRequiredFieldException() {
		super("Almeno un campo richiesto non è stato inserito");
	}
}
