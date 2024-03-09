package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Arrays;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
// Per evitare una ricorsione infinita nei log, non aggiungere @ToString!
@Entity
public class ImageFile {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String name;

	@Column(nullable = false)
	private String contentType;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] data;

	@OneToOne(mappedBy = "imageFile", fetch = FetchType.LAZY)
	private ProductModel model;

	public ImageFile(String name, String contentType, byte[] data, ProductModel model) {
		if (name.equalsIgnoreCase("banner.jpg")) {
			throw new IllegalArgumentException("Non è possibile creare un'immagine con nome 'banner.jpg'");
		}

		this.name = name;
		this.contentType = contentType;
		this.data = data;
		this.model = model;
	}

	public String generateBase64Image() {
		return Base64.encodeBase64String(this.data);
	}

	// ToString manuale per evitare ricorsione infinita nei log
	@Override
	public String toString() {
		return "ImageFile{" +
			"id=" + id +
			", name='" + name + '\'' +
			", contentType='" + contentType + '\'' +
			", dataSize=" + data.length +
			", modelId=" + model.getId() +
			'}';
	}
}