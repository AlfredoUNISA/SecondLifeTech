package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/my-profile/orders")
public class OrderController {
	private final UserService userService;

	@Autowired
	public OrderController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String getOrders(HttpServletRequest request, Model model) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			User user = userService.findUserByEmail(principal.getName());
			model.addAttribute("user", user);
			model.addAttribute("orders", user.getOrders());
			return "my-profile-orders";
		}

		return "redirect:/login";
	}
}
