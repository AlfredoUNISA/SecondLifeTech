package it.unisa.is.secondlifetech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EditBannerController {
    @GetMapping("/edit-banner")
    public String getEditBanner() {
        return "edit-banner";
    }
    @PostMapping("/edit-banner")
    public String postEditBanner() {
        return "edit-banner";
    }
}
