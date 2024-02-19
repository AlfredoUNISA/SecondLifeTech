package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/")
public class GeneralController {
	private final UserService userService;
	private final ImageFileService imageFileService;
	private final ProductModelService productModelService;
	private final ProductVariationService productVariationService;
	private final CartService cartService;

	@Autowired
	public GeneralController(UserService userService, ImageFileService imageFileService, ProductModelService productModelService, ProductVariationService productVariationService, CartService cartService) {
		this.userService = userService;
		this.imageFileService = imageFileService;
		this.productModelService = productModelService;
		this.productVariationService = productVariationService;
		this.cartService = cartService;
	}

	@GetMapping
	public String index() {
		return "my-custom-index";
	}

	@GetMapping("/create-user-test")
	public String createUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("roles", UserRole.ALL_ROLES);
		return "my-custom-create-user";
	}

	@PostMapping("/create-user-test")
	public String createUserPOST(@ModelAttribute("user") User user) {
		user.setRole(UserRole.getRole(user.getRole()));
		userService.createNewUser(user);
		return "redirect:/";
	}

	@GetMapping("/create-product-model-test")
	public String createProductModel(Model model) {
		ProductModel productModel = new ProductModel();
		model.addAttribute("productModel", productModel);
		model.addAttribute("categories", ProductCategory.ALL_CATEGORIES);
		return "my-custom-create-product-model";
	}

	@PostMapping("/create-product-model-test")
	public String createProductModelPOST(@ModelAttribute("productModel") ProductModel productModel,
	                                 @RequestAttribute("image") MultipartFile image) throws IOException {
		productModel.setImageFile(imageFileService.createNewImage(image));
		productModelService.createNewProductModel(productModel);
		return "redirect:/";
	}

	@GetMapping("/create-product-variation-test")
	public String createProductVariation(Model model) {
		ProductVariation productVariation = new ProductVariation();
		model.addAttribute("productVariation", productVariation);
		model.addAttribute("states", ProductState.ALL_STATES);
		model.addAttribute("productModels", productModelService.findAllProductModels());
		return "my-custom-create-product-variation";
	}

	@PostMapping("/create-product-variation-test")
	public String createProductVariationPOST(@ModelAttribute("productVariation") ProductVariation productVariation) {
		ProductModel productModel = productModelService.findProductModelById(productVariation.getProductModel().getId());
		productVariation.setProductModel(productModel);
		productVariationService.createNewProductVariation(productVariation);
		return "redirect:/";
	}

	@GetMapping("/add-to-cart-test")
	public String addToCart(Model model) {
		model.addAttribute("users", userService.findAllUsers());
		model.addAttribute("productVariations", productVariationService.findAllProductVariations());
		return "my-custom-add-to-cart";
	}

	@PostMapping("/add-to-cart-test")
	public String addToCartPOST(@RequestParam("userId") UUID userId, @RequestParam("productVariationId") UUID productVariationId) {
		Cart cart = cartService.findCartByUser(userId);
		cartService.addToCart(cart.getId(), productVariationId, 1);
		return "redirect:/";
	}


	// TODO: aggiungere la pagina di errore
}
