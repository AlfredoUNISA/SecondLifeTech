package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.ImageFile;
import it.unisa.is.secondlifetech.repository.ImageFileRepository;
import it.unisa.is.secondlifetech.service.ImageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageFileServiceImpl implements ImageFileService {
	private final ImageFileRepository imageFileRepository;

	@Autowired
	public ImageFileServiceImpl(ImageFileRepository imageFileRepository) {
		this.imageFileRepository = imageFileRepository;
	}

	/**
	 * Crea un nuovo file immagine
	 * @param file file da creare
	 * @return file immagine creato
	 * @throws IOException se si verifica un errore durante la creazione del file
	 */
	@Override
	public ImageFile createNewImage(MultipartFile file) throws IOException {
		ImageFile imageFile = new ImageFile();
		imageFile.setName(file.getOriginalFilename());
		imageFile.setContentType(file.getContentType());
		imageFile.setData(file.getBytes());
		return imageFileRepository.save(imageFile);
	}

	/**
	 * Trova un file immagine per id
	 * @param id id del file immagine
	 * @return file immagine, null se non esiste
	 */
	@Override
	public ImageFile findById(UUID id) {
		return imageFileRepository.findById(id).orElse(null);
	}

	/**
	 * Aggiorna un file immagine
	 * @param id id del file immagine
	 * @param imageFile file immagine
	 * @return file immagine aggiornato
	 */
	@Override
	public ImageFile updateImage(UUID id, ImageFile imageFile) {
		imageFile.setId(id);
		return imageFileRepository.save(imageFile);
	}

	/**
	 * Elimina un file immagine per id
	 * @param id id del file immagine
	 */
	@Override
	public void deleteById(UUID id) {
		imageFileRepository.deleteById(id);
	}

}
