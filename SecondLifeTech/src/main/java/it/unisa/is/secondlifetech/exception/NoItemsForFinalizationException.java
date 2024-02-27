package it.unisa.is.secondlifetech.exception;

import java.util.UUID;

public class NoItemsForFinalizationException extends Exception {
	public NoItemsForFinalizationException(UUID cartId) {
		super("Un ordine non deve essere vuoto per essere finalizzato (carrello " + cartId + ")");
	}
}
