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
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/")
public class GeneralController {
	private final UserService userService;
	private final ProductService productService;
	private final CartService cartService;

	@Autowired
	public GeneralController(UserService userService, ProductService productService, CartService cartService) {
		this.userService = userService;
		this.productService = productService;
		this.cartService = cartService;
	}

	@GetMapping
	public String index(Model model) {
		List<User> users = userService.findUsersByRole(UserRole.CLIENTE);
		List<ProductModel> productModels = productService.findAllModels();
		model.addAttribute("users", users);
		model.addAttribute("productModels", productModels);
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
		productService.createNewModel(productModel);
		productService.changeImageModel(productModel, image);
		return "redirect:/";
	}

	@GetMapping("/create-product-variation-test")
	public String createProductVariation(Model model) {
		ProductVariation productVariation = new ProductVariation();
		model.addAttribute("productVariation", productVariation);
		model.addAttribute("states", ProductState.ALL_STATES);
		model.addAttribute("productModels", productService.findAllModels());
		return "create-product-variation-test";
	}

	@PostMapping("/create-product-variation-test")
	public String createProductVariationPOST(@ModelAttribute("productVariation") ProductVariation productVariation) {
		ProductModel productModel = productService.findModelById(productVariation.getModel().getId());
		productService.createNewVariation(productModel, productVariation);
		return "redirect:/";
	}

	@PostMapping("/add-to-cart-test")
	public String addToCartPOST(@RequestParam("userId") UUID userId, @RequestParam("productVariationId") UUID productVariationId, @RequestParam("quantity") int quantity) {
		User user = userService.findUserById(userId);
		cartService.addToCart(user.getCart(), productVariationId, quantity);
		return "redirect:/view-cart-test?userId=" + userId;
	}

	@PostMapping("/remove-from-cart-test")
	public String removeFromCartPOST(@RequestParam("userId") UUID userId, @RequestParam("cartItemId") UUID cartItemId) {
		User user = userService.findUserById(userId);
		cartService.removeProductFromCart(user.getCart(), cartItemId);
		return "redirect:/view-cart-test?userId=" + userId;
	}

	@PostMapping("/update-cart-test")
	public String updateCartPOST(@RequestParam("userId") UUID userId, @RequestParam("productVariationId") UUID productVariationId, @RequestParam("quantity") int quantity) {
		User user = userService.findUserById(userId);
		cartService.editProductQuantityInCart(user.getCart(), productVariationId, quantity);
		return "redirect:/view-cart-test?userId=" + userId;
	}

	@GetMapping("/view-cart-test")
	public String viewCart(@RequestParam("userId") UUID userId, Model model) {
		User user = userService.findUserById(userId);
		model.addAttribute("user", user);
		return "view-cart-test";
	}

	@GetMapping("/view-product-variations")
	public String viewProductVariations(@RequestParam("productModelId") UUID productModelId, Model model) {
		ProductModel productModel = productService.findModelById(productModelId);

		model.addAttribute("users", userService.findUsersByRole(UserRole.CLIENTE));
		model.addAttribute("model", productModel);
		model.addAttribute("variations", productModel.getVariations());
		return "view-product-variations-test";
	}

	@PostMapping("/finalize-order-test")
	public String createOrder(@RequestParam("cartId") UUID cartId, @RequestParam("shippingAddressId") UUID shippingAddressId) {
		Cart cart = cartService.findCartById(cartId);
		ShippingAddress shippingAddress = userService.findShippingAddressById(shippingAddressId);

		cartService.finalizeOrder(cart, shippingAddress);

		return "redirect:/view-orders-test?userId=" + cart.getUser().getId();
	}

	@GetMapping("/view-orders-test")
	public String viewOrders(@RequestParam("userId") UUID userId, Model model) {
		User user = userService.findUserById(userId);
		model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("orders", user.getOrders());
		return "view-orders-test";
	}

	@GetMapping("/view-user-test")
	public String viewUser(@RequestParam("userId") UUID userId, Model model) {
		User user = userService.findUserById(userId);

		model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("user", user);
		model.addAttribute("shippingAddresses", user.getShippingAddresses());
		model.addAttribute("newShippingAddress", new ShippingAddress());
		model.addAttribute("paymentMethods", user.getPaymentMethods());
		model.addAttribute("newPaymentMethod", new PaymentMethod());
		return "view-user-test";
	}

	@PostMapping("/add-shipping-address-test")
	public String addShippingAddress(@RequestParam("userId") UUID userId, @ModelAttribute("newShippingAddress") ShippingAddress newShippingAddress) {
		User user = userService.findUserById(userId);
		userService.createNewShippingAddress(user, newShippingAddress);
		return "redirect:/view-user-test?userId=" + userId;
	}

	@PostMapping("/delete-shipping-address-test")
	public String deleteShippingAddress(@RequestParam("userId") UUID userId, @RequestParam("shippingAddressId") UUID shippingAddressId) {
		User user = userService.findUserById(userId);
		ShippingAddress target = null;

		for (ShippingAddress shippingAddress : user.getShippingAddresses()) {
			if (shippingAddress.getId().equals(shippingAddressId)) {
				target = shippingAddress;
				break;
			}
		}

		if(target != null)
			userService.deleteShippingAddress(target);

		return "redirect:/view-user-test?userId=" + userId;
	}

	@PostMapping("/add-payment-method-test")
	public String addPaymentMethod(@RequestParam("userId") UUID userId, @ModelAttribute("newPaymentMethod") PaymentMethod newPaymentMethod) {
		User user = userService.findUserById(userId);
		userService.createNewPaymentMethod(user, newPaymentMethod);
		return "redirect:/view-user-test?userId=" + userId;
	}

	@PostMapping("/delete-payment-method-test")
	public String deletePaymentMethod(@RequestParam("userId") UUID userId, @RequestParam("paymentMethodId") UUID paymentMethodId) {
		User user = userService.findUserById(userId);
		PaymentMethod target = null;

		for (PaymentMethod paymentMethod : user.getPaymentMethods()) {
			if (paymentMethod.getId().equals(paymentMethodId)) {
				target = paymentMethod;
				break;
			}
		}
		userService.deletePaymentMethod(target);
		return "redirect:/view-user-test?userId=" + userId;
	}

	@PostMapping("/delete-variation-test")
	public String deleteVariation(@RequestParam("productVariationId") UUID productVariationId) {
		ProductVariation variation = productService.findVariationById(productVariationId);
		UUID modelId = variation.getModel().getId();

		productService.deleteVariation(variation);
		return "redirect:/view-product-variations?productModelId=" + modelId;
	}

	@PostMapping("/delete-model-test")
	public String deleteModel(@RequestParam("productModelId") UUID productModelId) {
		ProductModel model = productService.findModelById(productModelId);
		productService.deleteModel(model);
		return "redirect:/";
	}

	@GetMapping("/update-variation-test")
	public String updateVariation(@RequestParam("productVariationId") UUID productVariationId, Model model) {
		ProductVariation variation = productService.findVariationById(productVariationId);
		model.addAttribute("states", ProductState.ALL_STATES);
		model.addAttribute("variation", variation);
		return "update-variation-test";
	}

	@PostMapping("/update-variation-test")
	public String updateVariationPOST(@ModelAttribute("variation") ProductVariation variation) {
		productService.updateVariation(variation);
		return "redirect:/view-product-variations?productModelId=" + variation.getModel().getId();
	}

	@GetMapping("/update-model-test")
	public String updateModel(@RequestParam("productModelId") UUID productModelId, Model model) {
		ProductModel modelToUpdate = productService.findModelById(productModelId);
		model.addAttribute("categories", ProductCategory.ALL_CATEGORIES);
		model.addAttribute("model", modelToUpdate);
		return "update-model-test";
	}

	@PostMapping("/update-model-test")
	public String updateModelPOST(@ModelAttribute("model") ProductModel model, @RequestAttribute("image") MultipartFile image) throws IOException {
		ProductModel original = productService.findModelById(model.getId());
		model.setImageFile(original.getImageFile());

		if(!image.isEmpty())
			model.setImageFile(productService.changeImageModel(model, image));

		productService.updateModel(model);
		return "redirect:/view-product-variations?productModelId=" + model.getId();
	}


	// TODO: aggiungere la pagina di errore
}
