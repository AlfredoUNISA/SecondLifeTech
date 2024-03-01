package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchProductController {
    ProductService productService;

    @Autowired
    public SearchProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String searchProduct(HttpServletRequest request) {
        request.setAttribute("products", productService.findAllModels());
        return "products";
    }

}
