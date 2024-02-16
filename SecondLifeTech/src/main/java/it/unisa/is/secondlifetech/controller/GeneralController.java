package it.unisa.is.secondlifetech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class GeneralController {
	@GetMapping
	public String index() {
		return "my-custom-index";
	}

	// TODO: aggiungere la pagina di errore
}
