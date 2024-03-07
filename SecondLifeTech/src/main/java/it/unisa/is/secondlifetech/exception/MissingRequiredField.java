package it.unisa.is.secondlifetech.exception;

public class MissingRequiredField extends Exception {
	public MissingRequiredField() {
		super("Almeno un campo richiesto non è stato inserito");
	}
}
