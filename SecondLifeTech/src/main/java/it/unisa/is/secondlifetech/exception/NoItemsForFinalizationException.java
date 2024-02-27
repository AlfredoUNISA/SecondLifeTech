package it.unisa.is.secondlifetech.exception;

public class NoItemsForFinalizationException extends Exception {
	public NoItemsForFinalizationException() {
		super("Un ordine non deve essere vuoto per essere finalizzato");
	}
}
