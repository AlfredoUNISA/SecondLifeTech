package it.unisa.is.secondlifetech.controller.user;

import it.unisa.is.secondlifetech.dto.UserFilters;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.exception.ErrorInFieldException;
import it.unisa.is.secondlifetech.exception.MissingRequiredFieldException;
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

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserListController {
    UserService userService;

    @Autowired
    public UserListController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard-utenti")
    public String userList(Model model,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "role", required = false) String ruolo,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           HttpServletRequest request
        ) throws ErrorInFieldException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Principal principal = request.getUserPrincipal();
        if(ruolo!=null){
            ruolo = "ROLE_"+ruolo;
        }
        UserFilters filters = new UserFilters(email,ruolo);

        Page<User> userPage;

        if (filters.isDefault()) {
            // Nessun filtro
            userPage = userService.findAllUsersPaginated(
                    PageRequest.of(currentPage - 1, pageSize)
            );
            model.addAttribute("userPage", userPage);
            model.addAttribute("productList", userPage.getContent());

        } else {
            // Applicare i filtri
            userPage = userService.findAllUsersPaginatedWithFilters(
                    filters,
                    PageRequest.of(currentPage - 1, pageSize)
            );
            model.addAttribute("userPage", userPage);
            model.addAttribute("productList", userPage.getContent());
        }

        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("filters", filters);
        model.addAttribute("roles", UserRole.ALL_ROLES);

        User user = null;
        if (principal != null) {
            user = userService.findUserByEmail(principal.getName());
        }
        model.addAttribute("user", user);
        return "dashboard-gestore-utenti";
    }
    //Gestione dei filtri
    @PostMapping("/dashboard-utenti")
    public String viewProductModelsWithFilters(@ModelAttribute("filters") UserFilters filters) {
        String queryString = filters.toQueryString();
        return "redirect:/dashboard-utenti?" + queryString;
    }

    @GetMapping("/dashboard-utenti/add-user")
    public String addVariation(Model model, Principal principal) {
        User user = null;
        if (principal != null) {
            user = userService.findUserByEmail(principal.getName());
        }
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", UserRole.ALL_ROLES);
        model.addAttribute("user", user);
        return "add-user";
    }

    @PostMapping("/dashboard-utenti/add-user")
    public String otherPost(@ModelAttribute("user") User user) throws IOException, ErrorInFieldException, MissingRequiredFieldException {
        try {
            user.setRole("ROLE_"+user.getRole());
            user.setPassword("standardPassword");
            userService.createNewUser(user);
        } catch (ErrorInFieldException errorInField) {
            throw new ErrorInFieldException("Errore nei campi");
        } catch (MissingRequiredFieldException missingRequiredField) {
            throw new MissingRequiredFieldException();
        }   catch (Exception e) {
            return "redirect:/error";
        }
        return "redirect:/dashboard-utenti";
    }
    @PostMapping("/dashboard-utenti/delete")
    public String deleteUser(@RequestParam("id") UUID id) {
        User user = userService.findUserById(id);
        try {
            userService.deleteUser(user);
        } catch (Exception e) {
            return "redirect:/error";
        }
        return "redirect:/dashboard-utenti";
    }
}
