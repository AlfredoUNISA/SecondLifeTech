package it.unisa.is.secondlifetech.service;

import it.unisa.is.secondlifetech.entity.ImageFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ImageFileService {
	ImageFile createNewImage(MultipartFile file) throws IOException;

	ImageFile findById(UUID id) throws Exception;

	List<ImageFile> findAll();
	void deleteById(UUID id);
}

