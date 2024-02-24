package it.unisa.is.secondlifetech.dto;

import lombok.*;

import java.time.Year;

@NoArgsConstructor
@Setter
@Getter
public class ProductFilters {
	public static int MIN_YEAR = 2000;
	public static int MAX_YEAR = Year.now().getValue();
	public static int MIN_RAM = 1;
	public static int MAX_RAM = 64;
	public static double MIN_DISPLAY_SIZE = 0.1;
	public static double MAX_DISPLAY_SIZE = 10.0;
	public static int MIN_STORAGE_SIZE = 1;
	public static int MAX_STORAGE_SIZE = 1000;
	public static double MIN_PRICE = 0.0;
	public static double MAX_PRICE = 10000.0;


	// Model
	private String name;
	private String brand;
	private String category;

	// Variation
	private int minYear = MIN_YEAR;
	private int maxYear = MAX_YEAR;
	private int minRam = MIN_RAM;
	private int maxRam = MAX_RAM;
	private double minDisplaySize = MIN_DISPLAY_SIZE;
	private double maxDisplaySize = MAX_DISPLAY_SIZE;
	private int minStorageSize = MIN_STORAGE_SIZE;
	private int maxStorageSize = MAX_STORAGE_SIZE;
	private double minPrice = MIN_PRICE;
	private double maxPrice = MAX_PRICE;
	private String color;
	private String state;

	public String toQueryString() {
		StringBuilder queryString = new StringBuilder();

		if (name != null && !name.isEmpty())
			queryString.append("name=").append(name).append("&");
		if (brand != null && !brand.isEmpty())
			queryString.append("brand=").append(brand).append("&");
		if (category != null && !category.isEmpty())
			queryString.append("category=").append(category).append("&");
		if (minYear != MIN_YEAR)
			queryString.append("minYear=").append(minYear).append("&");
		if (maxYear != MAX_YEAR)
			queryString.append("maxYear=").append(maxYear).append("&");
		if (minRam != MIN_RAM)
			queryString.append("minRam=").append(minRam).append("&");
		if (maxRam != MAX_RAM)
			queryString.append("maxRam=").append(maxRam).append("&");
		if (minDisplaySize != MIN_DISPLAY_SIZE)
			queryString.append("minDisplaySize=").append(minDisplaySize).append("&");
		if (maxDisplaySize != MAX_DISPLAY_SIZE)
			queryString.append("maxDisplaySize=").append(maxDisplaySize).append("&");
		if (minStorageSize != MIN_STORAGE_SIZE)
			queryString.append("minStorageSize=").append(minStorageSize).append("&");
		if (maxStorageSize != MAX_STORAGE_SIZE)
			queryString.append("maxStorageSize=").append(maxStorageSize).append("&");
		if (minPrice != MIN_PRICE)
			queryString.append("minPrice=").append(minPrice).append("&");
		if (maxPrice != MAX_PRICE)
			queryString.append("maxPrice=").append(maxPrice).append("&");
		if (color != null && !color.isEmpty())
			queryString.append("color=").append(color).append("&");
		if (state != null && !state.isEmpty())
			queryString.append("state=").append(state).append("&");

		// Remove the last "&"
		if (!queryString.isEmpty()) {
			queryString.setLength(queryString.length() - 1);
		}

		return queryString.toString();
	}

	public boolean isEmpty() {
		return name == null && brand == null && category == null && minYear == MIN_YEAR && maxYear == MAX_YEAR
				&& minRam == MIN_RAM && maxRam == MAX_RAM && minDisplaySize == MIN_DISPLAY_SIZE
				&& maxDisplaySize == MAX_DISPLAY_SIZE && minStorageSize == MIN_STORAGE_SIZE
				&& maxStorageSize == MAX_STORAGE_SIZE && minPrice == MIN_PRICE && maxPrice == MAX_PRICE && color == null
				&& state == null;
	}
}
