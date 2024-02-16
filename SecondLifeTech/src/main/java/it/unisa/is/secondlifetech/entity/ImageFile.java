package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
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

	public ImageFile(String name, String contentType, byte[] data) {
		this.name = name;
		this.contentType = contentType;
		this.data = data;
	}

	public String generateBase64Image() {
		return Base64.encodeBase64String(this.data);
	}

}