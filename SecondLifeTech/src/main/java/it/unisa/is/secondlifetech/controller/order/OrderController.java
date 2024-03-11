package it.unisa.is.secondlifetech.controller.order;

import it.unisa.is.secondlifetech.dto.OrderFilters;
import it.unisa.is.secondlifetech.entity.OrderPlaced;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.exception.ErrorInFieldException;
import it.unisa.is.secondlifetech.service.OrderService;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public OrderController(UserService userService, OrderService orderService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/my-profile/orders")
    public String getCurrentUserOrders(HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            User user = userService.findUserByEmail(principal.getName());
            List<OrderPlaced> orders = user.getOrders();
            // sort by date desc
            orders.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

            model.addAttribute("user", user);
            model.addAttribute("orders", orders);
            return "my-profile-orders";
        }

        return "redirect:/login";
    }

    @GetMapping("/dashboard-ordini")
    public String userList(Model model,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "state", required = false) String ruolo,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           HttpServletRequest request
    ) throws ErrorInFieldException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Principal principal = request.getUserPrincipal();
        OrderFilters filters = new OrderFilters(email, ruolo);

        Page<OrderPlaced> orderPage;

        if (filters.isDefault()) {
            // Nessun filtro
            orderPage = orderService.findAllOrdersPaginated(
                    PageRequest.of(currentPage - 1, pageSize)
            );
            model.addAttribute("orderPage", orderPage);
            model.addAttribute("productList", orderPage.getContent());

        } else {
            // Applicare i filtri
            orderPage = orderService.findAllOrdersPaginatedWithFilters(
                    filters,
                    PageRequest.of(currentPage - 1, pageSize)
            );
            model.addAttribute("orderPage", orderPage);
            model.addAttribute("productList", orderPage.getContent());
        }

        int totalPages = orderPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= totalPages; i++) {
                pageNumbers.add(i);
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("filters", filters);
        model.addAttribute("states", UserRole.ALL_ROLES);

        User user = null;
        if (principal != null) {
            user = userService.findUserByEmail(principal.getName());
        }
        model.addAttribute("user", user);
        return "dashboard-gestore-ordini";
    }

    //Gestione dei filtri
    @PostMapping("/dashboard-ordini")
    public String viewProductModelsWithFilters(@ModelAttribute("filters") OrderFilters filters) {
        String queryString = filters.toQueryString();
        return "redirect:/dashboard-ordini?" + queryString;
    }

    @GetMapping("/dashboard-ordini/details")
    public String orderDetails(@RequestParam("id") String id, Model model, Principal principal) {
        User user = null;
        OrderPlaced order = orderService.findOrderById(UUID.fromString(id));

        model.addAttribute("ordine", order);
        if (principal != null) {
            user = userService.findUserByEmail(principal.getName());
        }
        return "order-details";

    }

    @PostMapping("/dashboard-ordini/editState")
    public String updateOrderState(@ModelAttribute("id") String id) {
        OrderPlaced order = orderService.findOrderById(UUID.fromString(id));

        if (!order.isShipped())
            order.setShipped(true);

        orderService.updateOrder(order);

        return "redirect:/dashboard-ordini/details?id=" + id;
    }
}
