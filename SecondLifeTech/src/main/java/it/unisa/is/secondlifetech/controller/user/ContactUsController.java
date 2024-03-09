package it.unisa.is.secondlifetech.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactUsController {
    @GetMapping("/contact-us")
    public String getContactUs() {
        return "contact-us";
    }
}
