package it.unisa.is.secondlifetech.config;

import it.unisa.is.secondlifetech.entity.Cart;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.exception.NoDevicesAvailableException;
import it.unisa.is.secondlifetech.service.CartService;
import it.unisa.is.secondlifetech.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private CartService cartService;
	private UserService userService;

	@Autowired
	public CustomAuthenticationSuccessHandler(CartService cartService, UserService userService) {
		this.cartService = cartService;
		this.userService = userService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    Authentication authentication) {
		// Guest user, retrieve the cart from the cookie and add items to the database cart
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("cart")) {
					String cartValue = cookie.getValue();
					// Parse the cart cookie value to retrieve product id and quantity pairs
					Map<String, Integer> cartMap = parseCartCookie(cartValue);
					// Add each item to the user's cart in the database
					User user = userService.findUserByEmail(authentication.getName());
					Cart cart = user.getCart();
					for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
						UUID productId = UUID.fromString(entry.getKey());
						int quantity = entry.getValue();
						try {
							cartService.addToCart(cart, productId, quantity);
						} catch (NoDevicesAvailableException e) {
							log.error(e.getMessage());
						}
					}
					// Clear the cart cookie
					cookie.setMaxAge(0);
					break;
				}
			}
		}
		// Redirect to the home page
		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private Map<String, Integer> parseCartCookie(String value) {
		Map<String, Integer> cartMap = new HashMap<>();
		// Split the cookie value by '&' to separate product id and quantity pairs
		String[] pairs = value.split("&");
		for (String pair : pairs) {
			// Split each pair by '=' to separate product id and quantity
			String[] keyValue = pair.split("=");
			if (keyValue.length == 2) {
				cartMap.put(keyValue[0], Integer.parseInt(keyValue[1]));
			}
		}
		return cartMap;
	}
}
