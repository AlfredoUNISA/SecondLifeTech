package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.dto.ProductFilters;
import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/")
public class TestingController {
	private final UserService userService;
	private final ProductService productService;
	private final CartService cartService;
	private final OrderService orderService;

	@Autowired
	public TestingController(UserService userService, ProductService productService, CartService cartService, OrderService orderService) {
		this.userService = userService;
		this.productService = productService;
		this.cartService = cartService;
		this.orderService = orderService;
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

	@GetMapping("/update-user-test")
	public String updateUser(@RequestParam("userId") UUID userId, Model model) {
		User user = userService.findUserById(userId);
		model.addAttribute("user", user);
		model.addAttribute("roles", UserRole.ALL_ROLES);
		return "update-user-test";
	}

	@PostMapping("/update-user-test")
	public String updateUserPOST(@ModelAttribute("user") User user) {
		user.setRole(UserRole.getRole(user.getRole()));
		userService.updateUser(user);
		return "redirect:/";
	}

	@GetMapping("/view-product-models")
	public String viewProductModels(Model model,
                                    @RequestParam(value = "name", required = false) String name,
									@RequestParam(value = "brand", required = false) String brand,
                                    @RequestParam(value = "category", required = false) String category,
									@RequestParam(value = "minYear", required = false) Integer minYear,
									@RequestParam(value = "maxYear", required = false) Integer maxYear,
									@RequestParam(value = "minRam", required = false) Integer minRam,
									@RequestParam(value = "maxRam", required = false) Integer maxRam,
									@RequestParam(value = "minDisplaySize", required = false) Double minDisplaySize,
									@RequestParam(value = "maxDisplaySize", required = false) Double maxDisplaySize,
									@RequestParam(value = "minStorageSize", required = false) Integer minStorageSize,
									@RequestParam(value = "maxStorageSize", required = false) Integer maxStorageSize,
                                    @RequestParam(value = "minPrice", required = false) Double minPrice,
                                    @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                    @RequestParam(value = "color", required = false) String color,
                                    @RequestParam(value = "state", required = false) String state,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size
									) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(1);

		ProductFilters filters = new ProductFilters(
			name, brand, category, minYear, maxYear, minRam, maxRam, minDisplaySize, maxDisplaySize,
			minStorageSize, maxStorageSize, minPrice, maxPrice, color, state
		);

		Page<ProductModel> productPage;

		if (filters.isDefault()) {
			// Nessun filtro
			productPage = productService.findAllModels(
				PageRequest.of(currentPage - 1, pageSize)
			);
			model.addAttribute("productPage", productPage);
		} else {
			// Applicare i filtri
			productPage = productService.findAllModelsWithFilters(
				filters,
				PageRequest.of(currentPage - 1, pageSize)
			);
			model.addAttribute("productPage", productPage);
		}

		int totalPages = productPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = new ArrayList<>();
			for (int i = 1; i <= totalPages; i++) {
				pageNumbers.add(i);
			}
			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("filters", filters);
		model.addAttribute("categories", ProductCategory.ALL_CATEGORIES);
		model.addAttribute("states", ProductState.ALL_STATES);
		return "view-product-models-test";
	}

	@PostMapping("/view-product-models")
	public String viewProductModelsWithFilters(@ModelAttribute("filters") ProductFilters filters) {
		String queryString = filters.toQueryString();
		return "redirect:/view-product-models?" + queryString;
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

	@PostMapping("/delete-user-test")
	public String deleteUser(@RequestParam("userId") UUID userId) {
		User user = userService.findUserById(userId);
		userService.deleteUser(user);
		return "redirect:/";
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
		productService.updateModel(model, image);
		return "redirect:/view-product-variations?productModelId=" + model.getId();
	}

	@PostMapping("/ship-order-test")
	public String shipOrder(@RequestParam("orderId") UUID orderId) {
		OrderPlaced order = orderService.findOrderById(orderId);
		orderService.setOrderAsShipped(order);
		return "redirect:/view-orders-test?userId=" + order.getUser().getId();
	}

	@GetMapping("/cliente")
	public String cliente() {
		return "cliente-test";
	}

	@GetMapping("/gestore")
	public String gestore() {
		return "gestore-test";
	}




	// TODO: aggiungere la pagina di errore
}
