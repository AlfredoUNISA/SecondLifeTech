package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
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

	@OneToOne(mappedBy = "imageFile", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private ProductModel model;

	public ImageFile(String name, String contentType, byte[] data, ProductModel model) {
		this.name = name;
		this.contentType = contentType;
		this.data = data;
		this.model = model;
	}

	public String generateBase64Image() {
		return Base64.encodeBase64String(this.data);
	}

}