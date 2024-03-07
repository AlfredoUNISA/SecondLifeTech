package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.entity.PaymentMethod;
import it.unisa.is.secondlifetech.entity.ShippingAddress;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.exception.ErrorInField;
import it.unisa.is.secondlifetech.exception.MissingRequiredField;
import it.unisa.is.secondlifetech.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collections;
import java.util.UUID;

@Controller
public class ManageProfileController {
	UserService userService;

	@Autowired
	public ManageProfileController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/my-profile")
	public String getProfile(HttpServletRequest request, Model model) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			User user = userService.findUserByEmail(principal.getName());
			model.addAttribute("user", user);
			return "my-profile";
		}
		return "redirect:/login";
	}

	@GetMapping("/my-profile/delete-my-account")
	public String getDeleteProfile() {
		return "redirect:/my-profile";
	}

	@PostMapping("/my-profile/delete-my-account")
	public String deleteProfile(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			User user = userService.findUserByEmail(principal.getName());
			try {
				userService.deleteUser(user);
			} catch (Exception e) {
				return "redirect:/error";
			}
			return "redirect:/logout";
		}
		return "redirect:/login";

	}

	@PostMapping("/my-profile/edit-info")
	public String editProfileBasic(HttpServletRequest request,
	                               @RequestParam("name") String name,
	                               @RequestParam("surname") String surname,
	                               @RequestParam("email") String email,
	                               @RequestParam("phone") String phone) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			User user = userService.findUserByEmail(principal.getName());

			user.setFirstName(name);
			user.setLastName(surname);
			user.setEmail(email);
			user.setPhoneNumber(phone);

			try {
				userService.updateUser(user);
			} catch (Exception e) {
				return "redirect:/error";
			}

			updatePrincipal(user);

			return "redirect:/my-profile";
		}
		return "redirect:/login";
	}

	@PostMapping("/my-profile/edit-password")
	public String editProfilePassword(HttpServletRequest request,
	                               @RequestParam("password") String password) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			User user = userService.findUserByEmail(principal.getName());

			user.setPassword(password);

			try {
				userService.updateUser(user);
			} catch (Exception e) {
				return "redirect:/error";
			}

			updatePrincipal(user);

			return "redirect:/my-profile";
		}
		return "redirect:/login";
	}

	/**
	 * Aggiorna il principal dell'utente loggato.
	 *
	 * @param user l'utente loggato
	 */
	private static void updatePrincipal(User user) {
		org.springframework.security.core.userdetails.User userObj = new org.springframework.security.core.userdetails.User(
			user.getEmail(),
			user.getPassword(),
			Collections.singleton(new SimpleGrantedAuthority(user.getRole())
			));
		Authentication authentication = new PreAuthenticatedAuthenticationToken(userObj, userObj.getPassword(), userObj.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@GetMapping("/my-profile/payment-methods")
	public String getPaymentMethods(HttpServletRequest request, Model model) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			User user = userService.findUserByEmail(principal.getName());
			model.addAttribute("user", user);
			model.addAttribute("paymentMethods", user.getPaymentMethods());
			model.addAttribute("newPaymentMethod", new PaymentMethod());

			Object error = request.getAttribute("error");
			if (error != null) {
				model.addAttribute("error", error);
			}

			return "my-profile-payment-methods";
		}
		return "redirect:/login";
	}

	@PostMapping("/my-profile/payment-methods/add")
	public String addPaymentMethod(HttpServletRequest request,
	                               @ModelAttribute("newPaymentMethod") PaymentMethod newPaymentMethod,
	                               RedirectAttributes redirectAttributes) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			User user = userService.findUserByEmail(principal.getName());

			try {
				userService.createNewPaymentMethod(user, newPaymentMethod);
			} catch (MissingRequiredField | ErrorInField e) {
				redirectAttributes.addFlashAttribute("error", e.getMessage());
				return "redirect:/my-profile/payment-methods";
			} catch (Exception e) {
				return "redirect:/error";
			}

			return "redirect:/my-profile/payment-methods";
		}
		return "redirect:/error";
	}

	@PostMapping("/my-profile/payment-methods/remove")
	public String removePaymentMethod(HttpServletRequest request,
	                                  @RequestParam("id") UUID id) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			User user = userService.findUserByEmail(principal.getName());

			for (PaymentMethod paymentMethod : user.getPaymentMethods()) {
				if (paymentMethod.getId().equals(id)) {
					try {
						userService.deletePaymentMethod(paymentMethod);
					} catch (Exception e) {
						return "redirect:/error";
					}

					return "redirect:/my-profile/payment-methods";
				}
			}

		}
		return "redirect:/error";
	}

	@GetMapping("/my-profile/shipping-addresses")
	public String getShippingAddresses(HttpServletRequest request, Model model){
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			User user = userService.findUserByEmail(principal.getName());
			model.addAttribute("user", user);
			model.addAttribute("shippingAddresses", user.getShippingAddresses());
			model.addAttribute("newShippingAddress", new ShippingAddress());

			Object error = request.getAttribute("error");
			if (error != null) {
				model.addAttribute("error", error);
			}

			return "my-profile-shipping-addresses";
		}
		return "redirect:/login";
	}

	@PostMapping("/my-profile/shipping-addresses/add")
	public String addShippingAddress(HttpServletRequest request,
	                                 @ModelAttribute("newShippingAddress") ShippingAddress newShippingAddress,
	                                 RedirectAttributes redirectAttributes) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			User user = userService.findUserByEmail(principal.getName());

			try {
				userService.createNewShippingAddress(user, newShippingAddress);
			} catch (MissingRequiredField | ErrorInField e) {
				redirectAttributes.addFlashAttribute("error", e.getMessage());
				return "redirect:/my-profile/shipping-addresses";
			} catch (Exception e) {
				return "redirect:/error";
			}

			return "redirect:/my-profile/shipping-addresses";
		}
		return "redirect:/error";
	}

	@PostMapping("/my-profile/shipping-addresses/edit")
	public String editShippingAddress(HttpServletRequest request,
	                                    @RequestParam("id") UUID id,
	                                    @RequestParam("street") String street,
	                                    @RequestParam("city") String city,
	                                    @RequestParam("country") String country,
	                                    @RequestParam("zipCode") String zipCode,
	                                    @RequestParam("state") String state,
	                                    RedirectAttributes redirectAttributes) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			User user = userService.findUserByEmail(principal.getName());

			try {
				userService.updateShippingAddress(new ShippingAddress(id, street, city, state, zipCode, country, user));
			} catch (MissingRequiredField | ErrorInField e) {
				redirectAttributes.addFlashAttribute("error", e.getMessage());
				return "redirect:/my-profile/shipping-addresses";
			} catch (Exception e) {
				return "redirect:/error";
			}

			return "redirect:/my-profile/shipping-addresses";
		}
		return "redirect:/error";
	}

	@PostMapping("/my-profile/shipping-addresses/remove")
	public String removeShippingAddress(HttpServletRequest request,
	                                    @RequestParam("id") UUID id) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			User user = userService.findUserByEmail(principal.getName());

			for (ShippingAddress shippingAddress : user.getShippingAddresses()) {
				if (shippingAddress.getId().equals(id)) {
					try {
						userService.deleteShippingAddress(shippingAddress);
					} catch (Exception e) {
						return "redirect:/error";
					}

					return "redirect:/my-profile/shipping-addresses";
				}
			}

		}
		return "redirect:/error";
	}

}
