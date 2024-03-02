package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.service.ProductService;
import it.unisa.is.secondlifetech.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class LoginLogoutController {
	UserService userService;
	ProductService productService;

	@Autowired
	public LoginLogoutController(UserService userService, ProductService productService) {
		this.userService = userService;
		this.productService = productService;
	}


	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		if (request.getUserPrincipal() != null)
			return "redirect:/";
		return "login";
	}

	@GetMapping("/register")
	public String showRegistrationForm(HttpServletRequest request, Model model,
	                                   @RequestParam(value = "error", required = false) String error,
	                                   @RequestParam(value = "name", required = false) String name,
	                                   @RequestParam(value = "surname", required = false) String surname
	) {
		User user = new User();

		if (error != null && name != null && surname != null) {
			model.addAttribute("error", error);
			user.setFirstName(name);
			user.setLastName(surname);
		}

		if (request.getUserPrincipal() != null) {
			return "redirect:/";
		}

		model.addAttribute("newUser", user);
		return "register";
	}

	@GetMapping("/register/save")
	public String registration() {
		return "error";
	}

	@PostMapping("/register/save")
	public String registration(@ModelAttribute("newUser") User user, RedirectAttributes redirectAttributes) {
		user.setRole(UserRole.CLIENTE);

		try {
			userService.createNewUser(user);
		} catch (Exception e) {
			log.error("Error in registration: " + e.getMessage());
			redirectAttributes.addAttribute("error", e.getMessage());
			redirectAttributes.addAttribute("name", user.getFirstName());
			redirectAttributes.addAttribute("surname", user.getLastName());
			return "redirect:/register";
		}
		return "redirect:/register-success";
	}

	@GetMapping("/register-success")
	public String registrationSuccess() {
		return "register-success";
	}

}
