package it.unisa.is.secondlifetech.controller;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.service.CartService;
import it.unisa.is.secondlifetech.service.OrderService;
import it.unisa.is.secondlifetech.service.ProductService;
import it.unisa.is.secondlifetech.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
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
    public String showRegistrationForm(HttpServletRequest request, Model model) {
        if (request.getUserPrincipal() != null)
            return "redirect:/";

        User user = new User();
        model.addAttribute("newUser", user);
        model.addAttribute("user", null);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("newUser") User user, Model model) {
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

}
