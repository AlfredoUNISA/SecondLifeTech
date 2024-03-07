package it.unisa.is.secondlifetech.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecondHandDTO {
	private String brand;
	private String category;
	private int ram;
	private int storageSize;
	private double displaySize;
	private String state;
}
