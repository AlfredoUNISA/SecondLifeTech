package it.unisa.is.secondlifetech.repository;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.CartProduct;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartProductRepositoryTests {
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartProductRepository cartProductRepository;
	@Autowired
	private ProductModelRepository productModelRepository;
	@Autowired
	private ProductVariationRepository productVariationRepository;

	@Test
	void CartProductRepository_FindByCartId_ReturnCorrectList() {
		// Assert
		Cart cart1 = new Cart();
		cartRepository.save(cart1);

		Cart cart2 = new Cart();
		cartRepository.save(cart2);

		ProductModel productModel = new ProductModel(
			"iPhone 11",
			"Apple",
			ProductCategory.SMARTPHONE
		);
		productModelRepository.save(productModel);

		ProductVariation productVariation = new ProductVariation(
			2020,
			4,
			6.0,
			128,
			250.0,
			3,
			"Green",
			ProductState.ACCETTABILE,
			productModel
		);
		productVariationRepository.save(productVariation);

		CartProduct cartProduct1 = new CartProduct(cart1, productVariation, 1, productVariation.getPrice());
		CartProduct cartProduct2 = new CartProduct(cart2, productVariation, 2, productVariation.getPrice()*2);

		cartProductRepository.save(cartProduct1);
		cartProductRepository.save(cartProduct2);

		// Act
		List<CartProduct> foundCartProducts = cartProductRepository.findByCartId(cart1.getId());

		// Assert
		assertThat(foundCartProducts).isNotNull();
		assertThat(foundCartProducts.get(0).getCart()).isEqualTo(cart1);
		assertThat(foundCartProducts.get(0).getProductVariation()).isEqualTo(productVariation);
		assertThat(foundCartProducts.get(0).getProductVariation().getProductModel()).isEqualTo(productVariation.getProductModel());

	}
}