package it.unisa.is.secondlifetech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;

@Controller
@RequestMapping("/")
public class GeneralController {

	@GetMapping
	public String index() throws ParseException {
		return "my-custom-index";
	}

	// TODO: aggiungere la pagina di errore
}
