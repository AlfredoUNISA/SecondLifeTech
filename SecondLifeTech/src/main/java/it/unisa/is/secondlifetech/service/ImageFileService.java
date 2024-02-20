package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.ImageFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ImageFileService {
	/**
	 * Crea un nuovo file immagine
	 * @param file file da creare
	 * @return file immagine creato
	 * @throws IOException se si verifica un errore durante la creazione del file
	 */
	ImageFile createNewImage(MultipartFile file) throws IOException;

	/**
	 * Trova un file immagine per id
	 * @param id id del file immagine
	 * @return file immagine
	 */
	ImageFile findById(UUID id);

	/**
	 * Aggiorna un file immagine
	 * @param id id del file immagine
	 * @param imageFile file immagine
	 * @return file immagine aggiornato
	 */
	ImageFile updateImage(UUID id, ImageFile imageFile);

	/**
	 * Elimina un file immagine per id
	 * @param id id del file immagine
	 */
	void deleteById(UUID id);
}

