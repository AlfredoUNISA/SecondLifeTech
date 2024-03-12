package it.unisa.is.secondlifetech.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactUsController {
    @GetMapping("/contact-us")
    public String getContactUs() {
        return "contact-us";
    }

    @PostMapping("/contact-us")
    public String postContactUs(Model model) {
        model.addAttribute("success", true);
        return "contact-us";
    }
}
