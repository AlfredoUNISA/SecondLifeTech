package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ManageProfileController {
    UserService userService;

    @Autowired
    public ManageProfileController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/my-profile")
    public String getProfile(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        if (principal!=null) {
            User user = userService.findUserByEmail(principal.getName());
            request.setAttribute("user", user);
            request.setAttribute("addresses", user.getShippingAddresses());
            request.setAttribute("payments", user.getPaymentMethods());
            return "my-profile";
        }
        return "redirect:/login";
    }
    @GetMapping("/my-profile/shipping-address/")
    public String addShippingAddress(){
        return "shipping-address";
    }
}
