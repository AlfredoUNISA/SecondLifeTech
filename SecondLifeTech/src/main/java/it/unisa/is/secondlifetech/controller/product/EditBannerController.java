package it.unisa.is.secondlifetech.controller.product;

import it.unisa.is.secondlifetech.entity.ImageFile;
import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.repository.ImageFileRepository;
import it.unisa.is.secondlifetech.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class EditBannerController {
	private final ImageFileRepository imageFileRepository;
	private final UserService userService;

	@Autowired
	public EditBannerController(ImageFileRepository imageFileRepository, UserService userService) {
		this.imageFileRepository = imageFileRepository;
		this.userService = userService;
	}

	@GetMapping("/dashboard-prodotti/edit-banner")
	public String getEditBanner(Model model, HttpServletRequest request) {
		if (request.getUserPrincipal() == null) {
			return "redirect:/login";
		}

		User user = userService.findUserByEmail(request.getUserPrincipal().getName());
		model.addAttribute("user", user);
		return "edit-banner";
	}

	@PostMapping("/dashboard-prodotti/edit-banner")
	public String postEditBanner(@RequestParam("image") MultipartFile image) {
		if (!image.isEmpty()) {
			try {
				ImageFile imageFile = imageFileRepository.findByName("banner.jpg").orElse(null);

				if (imageFile != null) {
					imageFile.setData(image.getBytes());
					imageFileRepository.save(imageFile);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return "redirect:/dashboard-prodotti/edit-banner";
	}
}
