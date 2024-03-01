package it.unisa.is.secondlifetech.exception;

import java.util.UUID;

public class NoItemsForFinalizationException extends Exception {
	public NoItemsForFinalizationException() {
		super("Un ordine non deve essere vuoto per essere finalizzato");
	}
}
