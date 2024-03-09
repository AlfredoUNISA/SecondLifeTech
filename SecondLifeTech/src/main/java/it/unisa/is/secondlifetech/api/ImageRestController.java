package it.unisa.is.secondlifetech.api;

import it.unisa.is.secondlifetech.entity.ImageFile;
import it.unisa.is.secondlifetech.repository.ImageFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ImageRestController {
    private ImageFileRepository imageFileRepository;

    @Autowired
    public ImageRestController(ImageFileRepository imageFileRepository) {
        this.imageFileRepository = imageFileRepository;
    }


    /**
     * Metodo per ottenere l'immagine di un prodotto specifico.
     * @param id L'UUID dell'immagine del prodotto da recuperare.
     * @return ResponseEntity contenente i dati dell'immagine se trovata, altrimenti ritorna HttpStatus.NOT_FOUND.
     */
    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable UUID id) {
        Optional<ImageFile> imageFileOptional = imageFileRepository.findById(id);

        return getResponseEntity(imageFileOptional);
    }

    /**
     * Metodo per ottenere l'immagine del banner.
     * Cerca un'immagine con il nome "banner.jpg" nel repository.
     * @return ResponseEntity contenente i dati dell'immagine se trovata, altrimenti ritorna HttpStatus.NOT_FOUND.
     */
    @GetMapping("/images/banner")
    public ResponseEntity<byte[]> getBanner() {
        Optional<ImageFile> imageFileOptional = imageFileRepository.findByName("banner.jpg");

        return getResponseEntity(imageFileOptional);
    }

    private ResponseEntity<byte[]> getResponseEntity(Optional<ImageFile> imageFileOptional) {
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
