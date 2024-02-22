package it.unisa.is.secondlifetech.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class ProductModel {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private String brand;

	@Column(nullable = false)
	private String category;

	@OneToMany(mappedBy = "model", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductVariation> variations = new ArrayList<>();

	@OneToOne
	private ImageFile imageFile;

	/**
	 * Aggiunge una nuova variante di prodotto a questo modello.
	 *
	 * @param variation la variante da aggiungere
	 */
	public void addVariation(ProductVariation variation) {
		this.variations.add(variation);
		variation.setModel(this);
	}

	/**
	 * Rimuove una variante di prodotto da questo modello.
	 *
	 * @param variation la variante da rimuovere
	 */
	public void removeVariation(ProductVariation variation) {
		this.variations.remove(variation);
		variation.setModel(null);
	}

	public void changeImage(ImageFile imageFile) {
		this.imageFile = imageFile;
		imageFile.setModel(this);
	}

	public void removeImage() {
		this.imageFile = null;
	}

	public ProductModel(String name, String brand, String category) {
		this.name = name;
		this.brand = brand;
		this.category = category;
	}


}
