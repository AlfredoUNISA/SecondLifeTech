package it.unisa.is.secondlifetech.api;

import it.unisa.is.secondlifetech.entity.ImageFile;
import it.unisa.is.secondlifetech.repository.ImageFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
public class ImageRestController {
	private ImageFileRepository imageFileRepository;

	@Autowired
	public ImageRestController(ImageFileRepository imageFileRepository) {
		this.imageFileRepository = imageFileRepository;
	}

	@GetMapping("/images/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable UUID id) {
		Optional<ImageFile> imageFileOptional = imageFileRepository.findById(id);

		if (imageFileOptional.isPresent()) {
			ImageFile imageFile = imageFileOptional.get();
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_TYPE, imageFile.getContentType());

			return new ResponseEntity<>(imageFile.getData(), headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
	}
}
