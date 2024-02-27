package it.unisa.is.secondlifetech.exception;

public class EmailAlreadyInUseException extends Exception {
	public EmailAlreadyInUseException(String email) {
		super("L'indirizzo email " + email + " è già in uso");
	}
}
