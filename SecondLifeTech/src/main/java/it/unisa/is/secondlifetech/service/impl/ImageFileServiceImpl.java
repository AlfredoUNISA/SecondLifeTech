package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.ImageFile;
import it.unisa.is.secondlifetech.repository.ImageFileRepository;
import it.unisa.is.secondlifetech.service.ImageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ImageFileServiceImpl implements ImageFileService {
	private final ImageFileRepository imageFileRepository;

	@Autowired
	public ImageFileServiceImpl(ImageFileRepository imageFileRepository) {
		this.imageFileRepository = imageFileRepository;
	}

	@Override
	public ImageFile saveImage(MultipartFile file) throws IOException {
		ImageFile fileObj = new ImageFile();
		fileObj.setName(file.getOriginalFilename());
		fileObj.setContentType(file.getContentType());
		fileObj.setData(file.getBytes());
		return imageFileRepository.save(fileObj);
	}
	@Override
	public ImageFile findById(UUID id) throws Exception {
		return imageFileRepository.findById(id).orElse(null);
	}

	@Override
	public List<ImageFile> findAll() {
		return imageFileRepository.findAll();
	}

	@Override
	public void deleteById(UUID id) {
		imageFileRepository.deleteById(id);
	}

}
