package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.entity.OrderPlaced;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
	private final UserService userService;

	@Autowired
	public OrderController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/my-profile/orders")
	public String getCurrentUserOrders(HttpServletRequest request, Model model) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			User user = userService.findUserByEmail(principal.getName());
			List<OrderPlaced> orders = user.getOrders();
			// sort by date desc
			orders.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

			model.addAttribute("user", user);
			model.addAttribute("orders", orders);
			return "my-profile-orders";
		}

		return "redirect:/login";
	}
}
