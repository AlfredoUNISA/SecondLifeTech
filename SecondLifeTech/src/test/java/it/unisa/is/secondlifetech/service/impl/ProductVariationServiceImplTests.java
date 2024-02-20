package it.unisa.is.secondlifetech.service.impl;

import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.repository.ProductVariationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductVariationServiceImplTests {

	@Mock
	private ProductVariationRepository productVariationRepository;

	@InjectMocks
	private ProductVariationServiceImpl productVariationService;

	private ProductVariation productVariation;

	@BeforeEach
	void setup() {
		ProductModel productModel = ProductModel.builder()
			.id(UUID.randomUUID())
			.variations(new ArrayList<>())
			.build();


		productVariation = ProductVariation.builder()
			.id(UUID.randomUUID())
			.model(productModel)
			.state(ProductState.OTTIMO)
			.build();
		productModel.getVariations().add(productVariation);
	}

	@Test
	void ProductVariationService_FindProductVariationsByState_ReturnCorrectList() {
		// Arrange
		String state = productVariation.getState();

		when(productVariationRepository.findByState(state)).thenReturn(List.of(productVariation));

		// Act
		List<ProductVariation> results = productVariationService.findProductVariationsByState(state);

		// Assert
		assertThat(results).hasSize(1);
		assertThat(results).containsOnly(productVariation);
	}

}