package it.unisa.is.secondlifetech.controller.product;

import it.unisa.is.secondlifetech.dto.ProductFilters;
import it.unisa.is.secondlifetech.dto.SecondHandDTO;
import it.unisa.is.secondlifetech.entity.constant.ProductState;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SecondHandController {

	@GetMapping("/secondhand")
	public String secondHand(@ModelAttribute("dto") SecondHandDTO dto, Model model) {
		if (dto == null)
			dto = new SecondHandDTO();

		model.addAttribute("dto", dto);
		return "secondhand";
	}

	@PostMapping("/secondhand")
	public String secondHandPost(@ModelAttribute("dto") SecondHandDTO dto, Model model, RedirectAttributes redirectAttributes) {
		String brand = dto.getBrand();
		String category = dto.getCategory();
		int ram = dto.getRam();
		int storageSize = dto.getStorageSize();
		double displaySize = dto.getDisplaySize();
		String state = dto.getState();

		if (brand == null || brand.isBlank()
			|| category == null || category.isBlank()
			|| state == null || state.isBlank()) {
			redirectAttributes.addFlashAttribute("error", "Tutti i campi sono obbligatori");
			return "redirect:/secondhand";
		}

		if (brand.length() < ProductFilters.MIN_STRING_LENGTH || brand.length() > ProductFilters.MAX_STRING_LENGTH) {
			redirectAttributes.addFlashAttribute("error", "La marca deve essere lunga tra " + ProductFilters.MIN_STRING_LENGTH + " e " + ProductFilters.MAX_STRING_LENGTH + " caratteri");
			return "redirect:/secondhand";
		}

		if (!List.of(ProductState.ALL_STATES).contains(state)) {
			redirectAttributes.addFlashAttribute("error", "Stato non valido");
			return "redirect:/secondhand";
		}

		if (category.length() < ProductFilters.MIN_STRING_LENGTH || category.length() > ProductFilters.MAX_STRING_LENGTH) {
			redirectAttributes.addFlashAttribute("error", "La categoria deve essere lunga tra " + ProductFilters.MIN_STRING_LENGTH + " e " + ProductFilters.MAX_STRING_LENGTH + " caratteri");
			return "redirect:/secondhand";
		}

		if (ram < ProductFilters.MIN_RAM || ram > ProductFilters.MAX_RAM) {
			redirectAttributes.addFlashAttribute("error", "La RAM deve essere compresa tra " + ProductFilters.MIN_RAM + " e " + ProductFilters.MAX_RAM);
			return "redirect:/secondhand";
		}

		if (displaySize < ProductFilters.MIN_DISPLAY_SIZE || displaySize > ProductFilters.MAX_DISPLAY_SIZE) {
			redirectAttributes.addFlashAttribute("error", "La dimensione dello schermo deve essere compresa tra " + ProductFilters.MIN_DISPLAY_SIZE + " e " + ProductFilters.MAX_DISPLAY_SIZE);
			return "redirect:/secondhand";
		}

		if (storageSize < ProductFilters.MIN_STORAGE_SIZE || storageSize > ProductFilters.MAX_STORAGE_SIZE) {
			redirectAttributes.addFlashAttribute("error", "La dimensione dello storage deve essere compresa tra " + ProductFilters.MIN_STORAGE_SIZE + " e " + ProductFilters.MAX_STORAGE_SIZE);
			return "redirect:/secondhand";
		}

		double value = estimateValue(dto);

		model.addAttribute("value", value);
		model.addAttribute("dto", dto);
		return "secondhand";
	}

	public double estimateValue(SecondHandDTO dto) {
		String brand = dto.getBrand();
		String category = dto.getCategory();
		int ram = dto.getRam();
		int storageSize = dto.getStorageSize();
		double displaySize = dto.getDisplaySize();
		String state = dto.getState();

		double baseValue = 0.0;

		// Valutazione base in base al brand e alla categoria
		if (brand.equals("Apple")) {
			baseValue += 200; // Valore base per dispositivi Apple
		} else if (brand.equals("Samsung")) {
			baseValue += 100; // Valore base per dispositivi Samsung
		} else {
			baseValue += 50; // Valore base per altri brand
		}

		if (category.equals("Smartphone")) {
			baseValue += 20; // Valore aggiuntivo per smartphone
		} else if (category.equals("Tablet")) {
			baseValue += 30; // Valore aggiuntivo per tablet
		} else {
			baseValue += 50; // Valore aggiuntivo per altri dispositivi
		}

		// Aggiunta valore in base alla RAM
		baseValue += ram * 5; // Ogni GB di RAM aggiunge 5 euro

		// Aggiunta valore in base alla dimensione dello storage
		baseValue += storageSize * 0.1; // Ogni GB di storage aggiunge 0.1 euro

		// Aggiunta valore in base alla dimensione del display
		baseValue += displaySize * 10; // Ogni pollice di display aggiunge 10 euro

		// Aggiunta valore in base alle condizioni del dispositivo
		if (state.equals(ProductState.OTTIMO)) {
			baseValue *= 1.5; // Valore raddoppiato per dispositivi in ottime condizioni
		} else if (state.equals(ProductState.BUONO)) {
			baseValue *= 0.8; // Valore ridotto del 20% per dispositivi in buone condizioni
		} else if (state.equals(ProductState.ACCETTABILE)) {
			baseValue *= 0.3; // Valore ridotto del 70% per dispositivi in condizioni accettabili
		}

		// Prendi solo due decimali
		baseValue = Math.round(baseValue * 100.0) / 100.0;
		return baseValue;
	}

}
