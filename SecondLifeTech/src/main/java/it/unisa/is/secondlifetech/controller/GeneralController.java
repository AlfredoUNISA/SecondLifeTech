package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.entity.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/")
public class GeneralController {
	private final UserService userService;
	private final ImageFileService imageFileService;
	private final ProductModelService productModelService;
	private final ProductVariationService productVariationService;
	private final OrderService orderService;
	private final CartService cartService;

	@Autowired
	public GeneralController(UserService userService, ImageFileService imageFileService, ProductModelService productModelService, ProductVariationService productVariationService, OrderService orderService, CartService cartService) {
		this.userService = userService;
		this.imageFileService = imageFileService;
		this.productModelService = productModelService;
		this.productVariationService = productVariationService;
		this.orderService = orderService;
		this.cartService = cartService;
	}

	@GetMapping
	public String index(Model model) {
		model.addAttribute("users", userService.findUsersByRole(UserRole.CLIENTE));
		return "index";
	}

	@GetMapping("/create-user-test")
	public String createUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("roles", UserRole.ALL_ROLES);
		return "create-user-test";
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
		return "create-product-model-test";
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
		return "create-product-variation-test";
	}

	@PostMapping("/create-product-variation-test")
	public String createProductVariationPOST(@ModelAttribute("productVariation") ProductVariation productVariation) {
		ProductModel productModel = productModelService.findProductModelById(productVariation.getModel().getId());
		productVariation.setModel(productModel);
		productVariationService.createNewProductVariation(productVariation);
		return "redirect:/";
	}

	@PostMapping("/add-to-cart-test")
	public String addToCartPOST(@RequestParam("userId") UUID userId, @RequestParam("productVariationId") UUID productVariationId, @RequestParam("quantity") int quantity, Model model) {
		User user = userService.findUserById(userId);
		cartService.addToCart(user.getCart(), productVariationId, quantity);
		return "redirect:/view-cart-test?userId=" + userId;
	}

	@GetMapping("/view-cart-test")
	public String viewCart(@RequestParam("userId") UUID userId, Model model) {
		User user = userService.findUserById(userId);
		log.info("Cart: " + user.getCart());
		model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("cart", user.getCart());
		return "view-cart-test";
	}

	@GetMapping("/view-product-variations")
	public String viewProductVariations(Model model) {
		model.addAttribute("users", userService.findUsersByRole(UserRole.CLIENTE));
		model.addAttribute("variations", productVariationService.findAllProductVariations());
		return "view-product-variations-test";
	}


	// TODO: aggiungere la pagina di errore
}
