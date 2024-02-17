package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.CartProduct;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.repository.CartProductRepository;
import it.unisa.is.secondlifetech.repository.CartRepository;
import it.unisa.is.secondlifetech.repository.ProductModelRepository;
import it.unisa.is.secondlifetech.repository.ProductVariationRepository;
import it.unisa.is.secondlifetech.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/")
public class GeneralController {
	private final CartRepository cartRepository;
	private final CartProductRepository cartProductRepository;
	private final ProductModelRepository productModelRepository;
	private final ProductVariationRepository productVariationRepository;
	private final CartService cartService;

	@Autowired
	public GeneralController(CartRepository cartRepository, CartProductRepository cartProductRepository, ProductModelRepository productModelRepository, ProductVariationRepository productVariationRepository, CartService cartService) {
		this.cartRepository = cartRepository;
		this.cartProductRepository = cartProductRepository;
		this.productModelRepository = productModelRepository;
		this.productVariationRepository = productVariationRepository;
		this.cartService = cartService;
	}

	@GetMapping
	public String index() {
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

		cartService.addToCart(cart1.getId(), productVariation.getId(), 2);

		return "my-custom-index";
	}

	// TODO: aggiungere la pagina di errore
}
