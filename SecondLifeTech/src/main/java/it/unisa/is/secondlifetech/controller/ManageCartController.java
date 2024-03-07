package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.config.WebSecurityConfig;
import it.unisa.is.secondlifetech.entity.*;
import it.unisa.is.secondlifetech.service.CartService;
import it.unisa.is.secondlifetech.service.ProductService;
import it.unisa.is.secondlifetech.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@Slf4j
@RequestMapping("/my-cart")
public class ManageCartController {
	private final ProductService productService;
	private final CartService cartService;
	private final UserService userService;

	@Autowired
	public ManageCartController(ProductService productService, CartService cartService, UserService userService) {
		this.productService = productService;
		this.cartService = cartService;
		this.userService = userService;
	}

	@GetMapping
	public String showCart(HttpServletRequest request, Model model) {
		User user = null;

		if (request.getUserPrincipal() == null) {
			// Guest user, retrieve the cart from the cookie
			Cookie[] cookies = request.getCookies();

			boolean cartFound = false;
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("cart")) {
						String cartValue = cookie.getValue();
						// Parse the cart cookie value to retrieve product id and quantity pairs
						Map<String, Integer> cartMap = parseCartCookie(cartValue);
						// Retrieve product information from the database using product ids
						List<CartItem> cartItems = getProductDetails(cartMap);
						model.addAttribute("cartItems", cartItems);
						model.addAttribute("total", cartItems.stream().mapToDouble(item ->
							item.getProductVariation().getPrice() * item.getQuantity()).sum()
						);
						cartFound = true;
						break;
					}
				}
			}
			if (!cartFound) {
				// No cart cookie found
				model.addAttribute("cartItems", new ArrayList<CartItem>());
				model.addAttribute("total", 0);
			}
		} else {
			user = userService.findUserByEmail(request.getUserPrincipal().getName());
			List<CartItem> cartItems = user.getCart().getItems();

			// Check if the product is still available in the database
			// Copia la lista per evitare ConcurrentModificationException
			for (CartItem cartItem : new ArrayList<>(cartItems)) {
				int stock = cartItem.getProductVariation().getQuantityInStock();
				int quantity = cartItem.getQuantity();

				if (stock == 0) {
					cartItems.remove(cartItem);
					cartService.removeProductFromCart(user.getCart(), cartItem.getProductVariation().getId());
				} else if (stock < quantity) {
					cartItem.setQuantity(stock);
					try {
						cartService.editProductQuantityInCart(user.getCart(), cartItem.getProductVariation().getId(), stock);
					} catch (Exception e) {
						return "redirect:/error";
					}
				}
			}

			model.addAttribute("cartItems", cartItems);
			model.addAttribute("total", user.getCart().getTotal());
		}

		model.addAttribute("user", user);
		return "my-cart";
	}


	/**
	 * Retrieve product details from the database using product ids if the user with a cart cookie is a guest
	 *
	 * @param cartMap the cart map containing product id and quantity pairs
	 * @return a list of CartItem objects containing product details and quantity
	 */
	private List<CartItem> getProductDetails(Map<String, Integer> cartMap) {
		List<CartItem> cartItems = new ArrayList<>();
		// Iterate over the product id and quantity pairs
		for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
			String productId = entry.getKey();
			Integer quantity = entry.getValue();
			// Retrieve product details from the database using product id
			ProductVariation product = productService.findVariationById(UUID.fromString(productId));
			if (product != null) {
				// Create a CartItem object with product details and quantity
				CartItem cartItem = new CartItem();
				cartItem.setProductVariation(product);
				cartItem.setQuantity(quantity);

				cartItems.add(cartItem);
			}
		}
		return cartItems;
	}

	@PostMapping("/add")
	public String addProductToCart(HttpServletRequest request,
	                               HttpServletResponse response,
	                               @RequestParam("id") String id,
	                               @RequestParam("quantity") String quantity) {

		if (request.getUserPrincipal() == null) {
			// Guest user, store the cart in a cookie
			Cookie[] cookies = request.getCookies();

			boolean cartFound = false;
			if (cookies != null) {
				// Cart cookie found, update it with new product and quantity
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("cart")) {
						String value = cookie.getValue();
						// Parse the existing cart information
						Map<String, Integer> cartMap = parseCartCookie(value);

						// Add or update the product quantity in the cart
						cartMap.put(id, cartMap.getOrDefault(id, 0) + Integer.parseInt(quantity));

						// Convert the updated cart map back to a string
						String updatedCartValue = convertCartToString(cartMap);

						// Update the cart cookie value
						cookie.setValue(updatedCartValue);
						cookie.setMaxAge(WebSecurityConfig.COOKIE_MAX_AGE);
						cookie.setPath("/");
						response.addCookie(cookie);
						cartFound = true;
						break;
					}
				}
			}
			if (!cartFound) {
				// Cart cookie not found, create a new one
				Map<String, Integer> cartMap = new HashMap<>();
				cartMap.put(id, Integer.parseInt(quantity));

				String cartValue = convertCartToString(cartMap);
				Cookie newCookie = new Cookie("cart", cartValue);

				// Add the cookie to the response
				newCookie.setMaxAge(WebSecurityConfig.COOKIE_MAX_AGE);
				newCookie.setPath("/");
				response.addCookie(newCookie);
			}
		} else {
			User user = userService.findUserByEmail(request.getUserPrincipal().getName());
			try {
				cartService.addToCart(user.getCart(), UUID.fromString(id), Integer.parseInt(quantity));
			} catch (Exception e) {
				return "redirect:/error";
			}
		}

		return "redirect:/my-cart";
	}

	@PostMapping("/edit")
	public String editQuantity(HttpServletRequest request,
	                           HttpServletResponse response,
	                           @RequestParam("productVariationId") String productVariationId,
	                           @RequestParam("quantity") String quantity) {

		if (request.getUserPrincipal() == null) {
			// Guest user, store the cart in a cookie
			Cookie[] cookies = request.getCookies();

			boolean cartFound = false;
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("cart")) {
						// Cart cookie found, update it with new quantity
						String cartValue = cookie.getValue();
						// Parse the cart cookie value to retrieve product id and quantity pairs
						Map<String, Integer> cartMap = parseCartCookie(cartValue);
						// Update the quantity for the specified product variation id
						cartMap.put(productVariationId, Integer.parseInt(quantity));
						// Convert the updated cart map back to a string
						String updatedCartValue = convertCartToString(cartMap);
						// Update the cart cookie value
						cookie.setValue(updatedCartValue);
						// Set the cookie to expire in 1 day
						cookie.setMaxAge(WebSecurityConfig.COOKIE_MAX_AGE);
						cookie.setPath("/");
						// Add the cookie back to the response
						response.addCookie(cookie);
						cartFound = true;
						break;
					}
				}
			}
			if (!cartFound) {
				// Cart cookie not found, create a new one
				Map<String, Integer> cartMap = new HashMap<>();
				cartMap.put(productVariationId, Integer.parseInt(quantity));
				String cartValue = convertCartToString(cartMap);
				Cookie newCookie = new Cookie("cart", cartValue);
				// Set the cookie to expire in 1 day
				newCookie.setMaxAge(WebSecurityConfig.COOKIE_MAX_AGE);
				newCookie.setPath("/");
				// Add the cookie to the response
				response.addCookie(newCookie);
			}
		} else {
			User user = userService.findUserByEmail(request.getUserPrincipal().getName());
			try {
				cartService.editProductQuantityInCart(user.getCart(), UUID.fromString(productVariationId), Integer.parseInt(quantity));
			} catch (Exception e) {
				return "redirect:/error";
			}
		}

		return "redirect:/my-cart";
	}

	@PostMapping("/remove")
	public String removeProductFromCart(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    @RequestParam(value = "cartItemId", required = false) String cartItemId,
	                                    @RequestParam(value = "productVariationId", required = false) String productVariationId) {

		if (request.getUserPrincipal() == null) {
			// Guest user, store the cart in a cookie
			Cookie[] cookies = request.getCookies();

			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("cart")) {
						// Cart cookie found, update it by removing the specified cart item
						String cartValue = cookie.getValue();
						// Parse the cart cookie value to retrieve product id and quantity pairs
						Map<String, Integer> cartMap = parseCartCookie(cartValue);
						// Remove the specified cart item
						cartMap.remove(productVariationId);
						// Convert the updated cart map back to a string
						String updatedCartValue = convertCartToString(cartMap);
						// Update the cart cookie value
						cookie.setValue(updatedCartValue);
						// Set the cookie to expire in 1 day
						cookie.setMaxAge(WebSecurityConfig.COOKIE_MAX_AGE);
						cookie.setPath("/");
						// Add the cookie back to the response
						response.addCookie(cookie);
						break;
					}
				}
			}
		} else {
			User user = userService.findUserByEmail(request.getUserPrincipal().getName());
			try {
				cartService.removeProductFromCart(user.getCart(), UUID.fromString(cartItemId));
			} catch (Exception e) {
				return "redirect:/error";
			}
		}

		return "redirect:/my-cart";
	}

	@GetMapping("/place-order")
	public String placeOrder(HttpServletRequest request, Model model) {
		if (request.getUserPrincipal() == null) {
			return "redirect:/login";
		}
		User user = userService.findUserByEmail(request.getUserPrincipal().getName());

		if (user.getPaymentMethods().isEmpty())
			return "redirect:/my-profile/payment-methods?error_finalizing";

		if (user.getShippingAddresses().isEmpty())
			return "redirect:/my-profile/shipping-addresses?error_finalizing";

		model.addAttribute("user", user);
		model.addAttribute("cartItems", user.getCart().getItems());
		model.addAttribute("total", user.getCart().getTotal());
		model.addAttribute("addresses", user.getShippingAddresses());
		model.addAttribute("paymentMethods", user.getPaymentMethods());
		return "place-order";
	}

	@GetMapping("/place-order/payment-in-progress")
	public String payment(Model model, HttpServletRequest request,
	                      @RequestParam("shippingAddressId") UUID shippingAddressId,
	                      @RequestParam("paymentMethodId") UUID paymentMethodId) {
		if (request.getUserPrincipal() == null) {
			return "redirect:/login";
		}

		model.addAttribute("shippingAddressId", shippingAddressId);
		model.addAttribute("paymentMethodId", paymentMethodId);
		return "payment-in-progress";
	}

	@GetMapping("/place-order/payment-in-progress/proceed")
	public String paymentSuccessful(Model model, HttpServletRequest request,
	                                @RequestParam("shippingAddressId") UUID shippingAddressId,
	                                @RequestParam("paymentMethodId") UUID paymentMethodId) {
		if (request.getUserPrincipal() == null) {
			return "redirect:/login";
		}

		User user = userService.findUserByEmail(request.getUserPrincipal().getName());
		ShippingAddress shippingAddress = userService.findShippingAddressById(shippingAddressId);
		PaymentMethod paymentMethod = userService.findPaymentMethodById(paymentMethodId);

		try {
			cartService.finalizeOrder(user.getCart(), shippingAddress, paymentMethod, true);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "redirect:/error";
		}

		return "redirect:/my-profile/orders";
	}

	// TODO: non permettere al guest di selezionare una quantità < 0 e > stock

	/**
	 * Parse the cart cookie value into a map
	 *
	 * @param value the cart cookie value
	 * @return the cart map parsed from the cookie value
	 */
	private Map<String, Integer> parseCartCookie(String value) {
		Map<String, Integer> cartMap = new HashMap<>();
		// Split the cookie value by '&' to separate product id and quantity pairs
		String[] pairs = value.split("&");
		for (String pair : pairs) {
			// Split each pair by '=' to separate product id and quantity
			String[] keyValue = pair.split("=");
			if (keyValue.length == 2) {
				// Ensure that the keyValue array contains both product id and quantity
				cartMap.put(keyValue[0], Integer.parseInt(keyValue[1]));
			}
		}
		return cartMap;
	}


	/**
	 * Convert the cart map to a string that can be stored in a cookie
	 *
	 * @param cartMap the cart map to convert
	 * @return the string representation of the cart map
	 */
	private String convertCartToString(Map<String, Integer> cartMap) {
		StringBuilder cartValue = new StringBuilder();
		for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
			// Append product id and quantity pairs separated by '='
			cartValue.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		// Check if the cart is not empty before removing the last '&'
		if (!cartValue.isEmpty()) {
			// Remove the last '&' character
			cartValue.setLength(cartValue.length() - 1);
		}
		return cartValue.toString();
	}


}
