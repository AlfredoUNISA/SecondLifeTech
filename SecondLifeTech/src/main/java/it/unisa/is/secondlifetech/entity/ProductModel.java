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

	/**
	 * Restituisce una lista di tutte le ram disponibili per questo modello.
	 *
	 * @return una lista di Stringhe rappresentanti le ram disponibili
	 */
	public List<String> getVariationRamList() {
		// Crea una lista di tutte le ram disponibili per questo modello
		List<Integer> ramList = new ArrayList<>();

		for (ProductVariation variation : variations) {
			if (!ramList.contains(variation.getRam()))
				ramList.add(variation.getRam());
		}
		ramList.sort(Integer::compareTo);

		// Converti la lista di interi in una lista di stringhe
		List<String> ramListString = new ArrayList<>();
		for (Integer ram : ramList) {
			ramListString.add(ram.toString());
		}

		return ramListString;
	}

	/**
	 * Restituisce una lista di tutte le dimensioni dello schermo disponibili per questo modello.
	 *
	 * @return una lista di Stringhe rappresentanti le dimensioni dello schermo disponibili
	 */
	public List<String> getVariationDisplaySizeList() {
		// Crea una lista di tutte le dimensioni dello schermo disponibili per questo modello
		List<Double> displaySizeList = new ArrayList<>();

		for (ProductVariation variation : variations) {
			if (!displaySizeList.contains(variation.getDisplaySize()))
				displaySizeList.add(variation.getDisplaySize());
		}
		displaySizeList.sort(Double::compareTo);

		// Converti la lista di double in una lista di stringhe
		List<String> displaySizeListString = new ArrayList<>();

		for (Double display : displaySizeList) {
			String displaySizeString;

			// Convert double to string
			if (display % 1 == 0) {
				// If the double has no decimal part, use integer representation
				displaySizeString = Integer.toString((int) (double) display);
			} else {
				// Otherwise, use the full double representation
				displaySizeString = Double.toString(display);
			}

			// Check if the displaySizeString is already in the list
			if (!displaySizeListString.contains(displaySizeString)) {
				displaySizeListString.add(displaySizeString);
			}
		}

		return displaySizeListString;
	}

	/**
	 * Restituisce una lista di tutte le dimensioni del disco disponibili per questo modello.
	 *
	 * @return una lista di Stringhe rappresentanti le dimensioni del disco disponibili
	 */
	public List<String> getVariationStorageSizeList() {
		// Crea una lista di tutte le dimensioni del disco disponibili per questo modello
		List<Integer> storageSizeList = new ArrayList<>();

		for (ProductVariation variation : variations) {
			if (!storageSizeList.contains(variation.getStorageSize()))
				storageSizeList.add(variation.getStorageSize());
		}
		storageSizeList.sort(Integer::compareTo);

		// Converti la lista di interi in una lista di stringhe
		List<String> storageSizeListString = new ArrayList<>();
		for (Integer storageSize : storageSizeList) {
			storageSizeListString.add(storageSize.toString());
		}

		return storageSizeListString;
	}

	/**
	 * Restituisce una lista di tutti i colori disponibili per questo modello.
	 *
	 * @return una lista di Stringhe rappresentanti i colori disponibili
	 */
	public List<String> getVariationColorList() {
		List<String> colorList = new ArrayList<>();

		for (ProductVariation variation : variations) {
			if (!colorList.contains(variation.getColor()))
				colorList.add(variation.getColor());
		}
		return colorList;
	}

	/**
	 * Restituisce una lista di tutte le condizioni disponibili per questo modello.
	 *
	 * @return una lista di Stringhe rappresentanti le condizioni disponibili
	 */
	public List<String> getVariationStateList() {
		List<String> stateList = new ArrayList<>();

		for (ProductVariation variation : variations) {
			if (!stateList.contains(variation.getState()))
				stateList.add(variation.getState());
		}
		return stateList;
	}

	public String getTwoDigitMaxPrice() {
		return String.format("%.2f", getMaxPrice());
	}

	public String getTwoDigitMinPrice() {
		return String.format("%.2f", getMinPrice());
	}

	public Double getMinPrice() {
		double minPrice = Double.MAX_VALUE;
		for (ProductVariation variation : variations) {
			if (variation.getPrice() < minPrice) {
				minPrice = variation.getPrice();
			}
		}
		return minPrice;
	}

	public Double getMaxPrice() {
		double maxPrice = Double.MIN_VALUE;
		for (ProductVariation variation : variations) {
			if (variation.getPrice() > maxPrice) {
				maxPrice = variation.getPrice();
			}
		}
		return maxPrice;
	}

	public ProductModel(String name, String brand, String category) {
		this.name = name;
		this.brand = brand;
		this.category = category;
	}


}
