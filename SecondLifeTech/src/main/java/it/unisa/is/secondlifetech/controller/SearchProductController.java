package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.dto.ProductFilters;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.exception.ErrorInField;
import it.unisa.is.secondlifetech.service.ProductService;
import it.unisa.is.secondlifetech.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SearchProductController {
    ProductService productService;
    UserService userService;

    @Autowired
    public SearchProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/products")
    public String viewProductModels(Model model,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "brand", required = false) String brand,
                                    @RequestParam(value = "category", required = false) String category,
                                    @RequestParam(value = "minYear", required = false) Integer minYear,
                                    @RequestParam(value = "maxYear", required = false) Integer maxYear,
                                    @RequestParam(value = "minRam", required = false) Integer minRam,
                                    @RequestParam(value = "maxRam", required = false) Integer maxRam,
                                    @RequestParam(value = "minDisplaySize", required = false) Double minDisplaySize,
                                    @RequestParam(value = "maxDisplaySize", required = false) Double maxDisplaySize,
                                    @RequestParam(value = "minStorageSize", required = false) Integer minStorageSize,
                                    @RequestParam(value = "maxStorageSize", required = false) Integer maxStorageSize,
                                    @RequestParam(value = "minPrice", required = false) Double minPrice,
                                    @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                    @RequestParam(value = "color", required = false) String color,
                                    @RequestParam(value = "state", required = false) String state,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size
    ) throws ErrorInField {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        ProductFilters filters = new ProductFilters(
            name, brand, category, minYear, maxYear, minRam, maxRam, minDisplaySize, maxDisplaySize,
            minStorageSize, maxStorageSize, minPrice, maxPrice, color, state
        );

        Page<ProductModel> productPage;

        if (filters.isDefault()) {
            // Nessun filtro
            productPage = productService.findAllModelsPaginated(
                PageRequest.of(currentPage - 1, pageSize)
            );
            model.addAttribute("productPage", productPage);
            model.addAttribute("productList", productPage.getContent());

        } else {
            // Applicare i filtri
            productPage = productService.findAllModelsPaginatedWithFilters(
                filters,
                PageRequest.of(currentPage - 1, pageSize)
            );
            model.addAttribute("productPage", productPage);
            model.addAttribute("productList", productPage.getContent());
        }

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("filters", filters);
        model.addAttribute("categories", ProductCategory.ALL_CATEGORIES);
        model.addAttribute("states", ProductState.ALL_STATES);
        return "products";
    }

    @PostMapping("/products")
    public String viewProductModelsWithFilters(@ModelAttribute("filters") ProductFilters filters) {
        String queryString = filters.toQueryString();
        return "redirect:/products?" + queryString;
    }

    @GetMapping("/products/{name}")
    public String viewProductVariations(Model model, @PathVariable String name, Principal principal) {
        ProductModel productModel = productService.findModelByName(name);

        if (productModel.getVariations().isEmpty()) {
            return "redirect:/error";
        }

        model.addAttribute("productModel", productModel);
        model.addAttribute("productVariations", productModel.getVariations());

        List<String> ramList = productModel.getVariationRamList();
        List<String> displaySizeList = productModel.getVariationDisplaySizeList();
        List<String> storageSizeList = productModel.getVariationStorageSizeList();

        List<String> colorList = productModel.getVariationColorList();
        List<String> stateList = productModel.getVariationStateList();

        model.addAttribute("ramList", ramList);
        model.addAttribute("displaySizeList", displaySizeList);
        model.addAttribute("storageSizeList", storageSizeList);
        model.addAttribute("colorList", colorList);
        model.addAttribute("stateList", stateList);
        User user = null;
        if (principal != null) {
            user = userService.findUserByEmail(principal.getName());
        }
        model.addAttribute("user", user);
        return "productDetails";
    }

}
