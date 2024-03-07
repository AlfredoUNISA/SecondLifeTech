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

import java.security.Principal;
import java.util.List;

@Controller
public class Index {
    UserService userService;
    ProductService productService;

    @Autowired
    public Index(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }


    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = null;
        if (principal != null)
            user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        if (user != null)
            switch (user.getRole()) {
                case (UserRole.GESTORE_PRODOTTI):
                    return "redirect:/dashboard-prodotti";
                case (UserRole.GESTORE_UTENTI):
                    return "redirect:/dashboard-utenti";
                case (UserRole.GESTORE_ORDINI):
                    return "redirect:/dashboard-ordini";
            }
        return "homepage";
    }

    @GetMapping("/indexTesting")
    public String indexTesting(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        List<User> users = userService.findUsersByRole(UserRole.CLIENTE);
        List<ProductModel> productModels = productService.findAllModels();

        model.addAttribute("users", users);
        model.addAttribute("productModels", productModels);

        if (principal != null) {
            User user = userService.findUserByEmail(principal.getName());
            model.addAttribute("principal", user.getFirstName() + " " + user.getLastName() +
                    " (" + UserRole.getRoleName(user.getRole()) + ")");
        } else
            model.addAttribute("principal", "anonymous");

        return "/testing/index";
    }

}
