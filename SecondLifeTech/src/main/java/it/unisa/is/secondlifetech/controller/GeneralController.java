package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.service.*;
import jakarta.servlet.http.HttpServletRequest;
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
	private final ProductService productService;
	private final CartService cartService;

	@Autowired
	public GeneralController(UserService userService, ImageFileService imageFileService, ProductService productService, CartService cartService) {
		this.userService = userService;
		this.imageFileService = imageFileService;
		this.productService = productService;
		this.cartService = cartService;
	}

	@GetMapping
	public String index(Model model) {
		model.addAttribute("users", userService.findUsersByRole(UserRole.CLIENTE));
		return "homepage";
	}

	@GetMapping("/register")
	public String showRegistrationForm(HttpServletRequest request, Model model){
		if (request.getUserPrincipal() != null)
			return "redirect:/";

		User user = new User();
		model.addAttribute("user", user);
		return "register";
	}
	@PostMapping("/register/save")
	public String registration(@ModelAttribute("user") User user, Model model){
		User existing = userService.findUserByEmail(user.getEmail());

		if (existing != null) {
			model.addAttribute("error", "Email already exists");
			model.addAttribute("user", user);
			return "register";
		}

		user.setRole(UserRole.CLIENTE);
		userService.createNewUser(user);
		return "register-success";
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
		ImageFile imageFile = imageFileService.createNewImage(image);
		productModel.setImageFile(imageFile);
		productService.createNewModel(productModel);
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
		productVariation.setModel(productModel);
		productService.createNewVariation(productVariation);
		return "redirect:/";
	}

	@PostMapping("/add-to-cart-test")
	public String addToCartPOST(@RequestParam("userId") UUID userId, @RequestParam("productVariationId") UUID productVariationId, @RequestParam("quantity") int quantity, Model model) {
		User user = userService.findUserById(userId);
		cartService.addToCart(user.getCart(), productVariationId, quantity);
		return "redirect:/view-cart-test?userId=" + userId;
	}

	@PostMapping("/remove-from-cart-test")
	public String removeFromCartPOST(@RequestParam("userId") UUID userId, @RequestParam("productVariationId") UUID productVariationId, Model model) {
		User user = userService.findUserById(userId);
		cartService.removeProductFromCart(user.getCart(), productVariationId);
		return "redirect:/view-cart-test?userId=" + userId;
	}

	@PostMapping("/update-cart-test")
	public String updateCartPOST(@RequestParam("userId") UUID userId, @RequestParam("productVariationId") UUID productVariationId, @RequestParam("quantity") int quantity, Model model) {
		User user = userService.findUserById(userId);
		cartService.editProductQuantityInCart(user.getCart(), productVariationId, quantity);
		return "redirect:/view-cart-test?userId=" + userId;
	}

	@GetMapping("/view-cart-test")
	public String viewCart(@RequestParam("userId") UUID userId, Model model) {
		User user = userService.findUserById(userId);
		model.addAttribute("userId", userId);
		model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("cart", user.getCart());
		return "view-cart-test";
	}

	@GetMapping("/view-product-variations")
	public String viewProductVariations(Model model) {
		model.addAttribute("users", userService.findUsersByRole(UserRole.CLIENTE));
		model.addAttribute("variations", productService.findAllVariations());
		return "view-product-variations-test";
	}

	@PostMapping("/finalize-order-test")
	public String createOrder(@RequestParam("cartId") UUID cartId) {
		Cart cart = cartService.findCartById(cartId);
		cartService.finalizeOrder(cart);
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
		log.info("Cart: " + user.getCart());
		log.info("Orders: " + user.getOrders());
		log.info("Shipping addresses: " + user.getShippingAddresses());
		log.info("Payment methods: " + user.getPaymentMethods());

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
		userService.addShippingAddress(user, newShippingAddress);
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
		userService.removeShippingAddress(user, target);
		return "redirect:/view-user-test?userId=" + userId;
	}

	@PostMapping("/add-payment-method-test")
	public String addPaymentMethod(@RequestParam("userId") UUID userId, @ModelAttribute("newPaymentMethod") PaymentMethod newPaymentMethod) {
		User user = userService.findUserById(userId);
		userService.addPaymentMethod(user, newPaymentMethod);
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
		userService.removePaymentMethod(user, target);
		return "redirect:/view-user-test?userId=" + userId;
	}


	// TODO: aggiungere la pagina di errore
}
