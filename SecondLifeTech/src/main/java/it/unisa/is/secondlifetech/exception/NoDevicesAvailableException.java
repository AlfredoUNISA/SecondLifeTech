package it.unisa.is.secondlifetech.exception;

import it.unisa.is.secondlifetech.entity.ProductVariation;

public class NoDevicesAvailableException extends Exception {
	public NoDevicesAvailableException(int requestedQuantity, ProductVariation productVariation) {
		super("Quantità non disponibile nell'inventario per il prodotto "
			+ productVariation.getModel().getName()
			+ " (disponibili: " + productVariation.getQuantityInStock()
			+ ", richiesti: " + requestedQuantity + ")");
	}
}
