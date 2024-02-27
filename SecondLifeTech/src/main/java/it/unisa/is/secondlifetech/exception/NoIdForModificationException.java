package it.unisa.is.secondlifetech.exception;

public class NoIdForModificationException extends Exception {
	public NoIdForModificationException(Class<?> clazz) {
		super("Impossibile modificare un oggetto senza id per la classe " + clazz.getName());
	}
}
