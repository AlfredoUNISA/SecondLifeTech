package it.unisa.is.secondlifetech.controller.user;

import it.unisa.is.secondlifetech.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactUsController {

    private final UserService userService;

    @Autowired
    public ContactUsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/contact-us")
    public String getContactUs(HttpServletRequest request) {
        if (request.getUserPrincipal() != null &&
                userService.findUserByEmail(request.getUserPrincipal().getName()).getRole().contains("GESTORE")) {
            return "redirect:/";
        }

        return "contact-us";
    }

    @PostMapping("/contact-us")
    public String postContactUs(HttpServletRequest request, Model model) {
        if (request.getUserPrincipal() != null &&
                userService.findUserByEmail(request.getUserPrincipal().getName()).getRole().contains("GESTORE")) {
            return "redirect:/";
        }

        model.addAttribute("success", true);
        return "contact-us";
    }
}
