package it.unisa.is.secondlifetech.controller.product;

import it.unisa.is.secondlifetech.dto.ProductFilters;
import it.unisa.is.secondlifetech.entity.ImageFile;
import it.unisa.is.secondlifetech.entity.ProductModel;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.entity.constant.ProductCategory;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import it.unisa.is.secondlifetech.exception.ErrorInFieldException;
import it.unisa.is.secondlifetech.exception.MissingRequiredFieldException;
import it.unisa.is.secondlifetech.repository.ImageFileRepository;
import it.unisa.is.secondlifetech.service.ProductService;
import it.unisa.is.secondlifetech.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ProductControllerGestore {
    ProductService productService;
    UserService userService;
    ImageFileRepository imageFileRepository;

    @Autowired
    public ProductControllerGestore(ProductService productService, UserService userService, ImageFileRepository imageFileRepository) {
        this.productService = productService;
        this.userService = userService;
        this.imageFileRepository = imageFileRepository;
    }

    @GetMapping("/dashboard-prodotti")
    public String dashboard(Model model,
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
                            @RequestParam("size") Optional<Integer> size,
                            HttpServletRequest request
    ) throws ErrorInFieldException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);
        Principal principal = request.getUserPrincipal();

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

        User user = null;
        if (principal != null) {
            user = userService.findUserByEmail(principal.getName());
        }
        model.addAttribute("user", user);
        return "dashboard-gestore-prodotti";
    }

    //Gestione dei filtri
    @PostMapping("/dashboard-prodotti")
    public String viewProductModelsWithFilters(@ModelAttribute("filters") ProductFilters filters) {
        String queryString = filters.toQueryString();
        return "redirect:/dashboard-prodotti?" + queryString;
    }

    //Rimozione di un prodotto
    @PostMapping("/dashboard-prodotti/delete")
    public String deleteProduct(@RequestParam("id") UUID id) {
        ProductModel model = productService.findModelById(id);
        try {
            productService.deleteModel(model);
        } catch (Exception e) {
            return "redirect:/error";
        }
        return "redirect:/dashboard-prodotti";
    }

    @GetMapping("/dashboard-prodotti/view-variations")
    public String viewVariations(Model model, Principal principal, @RequestParam(value = "modelName") String modelName) {
        User user = null;
        if (principal != null) {
            user = userService.findUserByEmail(principal.getName());
        }
        ProductModel modelObj = productService.findModelByName(modelName);
        model.addAttribute("states", ProductState.ALL_STATES);
        model.addAttribute("modelName", modelObj.getName());
        model.addAttribute("user", user);
        model.addAttribute("variations", modelObj.getVariations());
        return "view-variations";
    }

    @GetMapping("/dashboard-prodotti/add-variation")
    public String addVariation(Model model, Principal principal,
                               @RequestParam(value = "modelName", required = false) String modelName,
                               HttpServletRequest request) {
        User user = null;
        List<ProductModel> models;
        if (principal != null) {
            user = userService.findUserByEmail(principal.getName());
        }
        Object error = request.getAttribute("error");
        if (error != null) {
            model.addAttribute("error", error);
        }
        //Controllo se non è stata richiesto di aggiungere una variazione ad un modello specifico
        if (modelName != null && !modelName.isBlank()) {
            models = new ArrayList<>();
            models.add(productService.findModelByName(modelName));
        } else {
            models = productService.findAllModels();

        }
        model.addAttribute("models", models);
        model.addAttribute("newVariation", new ProductVariation());
        model.addAttribute("user", user);
        return "add-variation";
    }

    @PostMapping("/dashboard-prodotti/add-variation")
    public String addVariation(@ModelAttribute("newVariation") ProductVariation variation,
                               @RequestParam(value = "image", required = false) MultipartFile file,
                               Principal principal, RedirectAttributes redirectAttributes) {
        ProductModel model = null;
        User user = null;
        if (principal != null) {
            user = userService.findUserByEmail(principal.getName());
        }
        //Aggiunta Immagine
        if (file != null && !file.isEmpty()) {
            ImageFile fileObj = new ImageFile();
            fileObj.setName(variation.getModel().getName());
            fileObj.setContentType(file.getContentType());
            try {
                fileObj.setData(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageFile imageFile = imageFileRepository.save(fileObj);
            variation.getModel().setImageFile(imageFile);
        }
        //Aggiunta della variazione

        if (variation.getModel().getBrand().isBlank()) { //Se il modello non è presente nel database
            model = productService.findModelById(variation.getModel().getId());
        } else {
            model = variation.getModel(); //Se il modello non è presente nel database
            try {
                productService.createNewModel(model); //Aggiungo il modello al database
            } catch (MissingRequiredFieldException | ErrorInFieldException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return "redirect:/dashboard-prodotti/add-variation";
            } catch (Exception e) {
                return "redirect:/error";
            }
        }
        try {
            productService.createNewVariation(model, variation);
        } catch (MissingRequiredFieldException | ErrorInFieldException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/dashboard-prodotti/add-variation";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "redirect:/dashboard-prodotti/view-variations?modelName=" + model.getName();
    }

    @PostMapping("/dashboard-prodotti/edit-model")
    public String editModel(@ModelAttribute("editModelID") String modelID, @RequestParam("image") MultipartFile
            file, RedirectAttributes redirectAttributes,
                            @ModelAttribute("name") String modelName,
                            @ModelAttribute("brand") String modelBrand,
                            @ModelAttribute("modelCategory") String modelCategory) throws IOException, ErrorInFieldException, MissingRequiredFieldException {
        if (file.isEmpty()) {
            throw new MissingRequiredFieldException();
        }
        ProductModel model = productService.findModelById(UUID.fromString(modelID));
        ImageFile fileObj = new ImageFile();
        fileObj.setName(model.getName());
        fileObj.setContentType(file.getContentType());
        fileObj.setData(file.getBytes());
        ImageFile imageFile = imageFileRepository.save(fileObj);
        try {
            model.setName(modelName);
            model.setBrand(modelBrand);
            model.setBrand(modelCategory);
            productService.updateModel(model, file);
        } catch (ErrorInFieldException | MissingRequiredFieldException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/dashboard-prodotti";
        } catch (Exception e) {
            return "redirect:/error";
        }
        return "redirect:/dashboard-prodotti";
    }
    @PostMapping("/dashboard-prodotti/edit-variation")
    public String editVariation(@ModelAttribute("editVariationID") String variationID,
                                @ModelAttribute("color") String color,
                                @ModelAttribute("displaySize") String displaySize,
                                @ModelAttribute("price") String price,
                                @ModelAttribute("quantityInStock") String quantityInStock,
                                @ModelAttribute("ram") String ram,
                                @ModelAttribute("state") String state,
                                @ModelAttribute("storageSize") String storageSize,
                                @ModelAttribute("year") String year) {
        ProductVariation variation = productService.findVariationById(UUID.fromString(variationID));
        variation.setColor(color);
        variation.setDisplaySize(Double.parseDouble(displaySize));
        variation.setPrice(Double.parseDouble(price));
        variation.setQuantityInStock(Integer.parseInt(quantityInStock));
        variation.setRam(Integer.parseInt(ram));
        variation.setState(state);
        variation.setStorageSize(Integer.parseInt(storageSize));
        variation.setYear(Integer.parseInt(year));
        try {
            productService.updateVariation(variation);
        } catch (Exception e) {
            return "redirect:/error";
        }
        return "redirect:/dashboard-prodotti/view-variations?modelName=" + variation.getModel().getName();
    }
}