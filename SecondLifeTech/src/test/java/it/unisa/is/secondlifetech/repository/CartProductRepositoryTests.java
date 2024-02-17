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

import java.util.ArrayList;
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

		ProductVariation productVariation1 = new ProductVariation(
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
		productVariationRepository.save(productVariation1);

		ProductVariation productVariation2 = new ProductVariation(
			2020,
			4,
			6.0,
			128,
			250.0,
			3,
			"Red",
			ProductState.OTTIMO,
			productModel
		);
		productVariationRepository.save(productVariation2);

		CartProduct cartProduct1 = new CartProduct(cart1, productVariation1, 1, productVariation1.getPrice());
		CartProduct cartProduct2 = new CartProduct(cart1, productVariation2, 2, productVariation1.getPrice()*2);
		CartProduct cartProduct3 = new CartProduct(cart2, productVariation1, 1, productVariation2.getPrice());

		cartProductRepository.save(cartProduct1);
		cartProductRepository.save(cartProduct2);
		cartProductRepository.save(cartProduct3);

		// Act
		List<CartProduct> foundCartProducts = cartProductRepository.findByCartId(cart1.getId());

		// Assert
		assertThat(foundCartProducts).isNotNull();
		assertThat(foundCartProducts.size()).isEqualTo(2);
		assertThat(foundCartProducts.get(0).getCart()).isEqualTo(cart1);
		assertThat(foundCartProducts.get(1).getCart()).isEqualTo(cart1);

		List<ProductVariation> variations = new ArrayList<>();
		variations.add(foundCartProducts.get(0).getProductVariation());
		variations.add(foundCartProducts.get(1).getProductVariation());

		assertThat(variations).containsOnly(productVariation1, productVariation2);
	}
}