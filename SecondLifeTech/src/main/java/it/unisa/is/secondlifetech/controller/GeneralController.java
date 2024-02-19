package it.unisa.is.secondlifetech.controller;

import it.unisa.is.secondlifetech.entity.User;
import it.unisa.is.secondlifetech.entity.constant.UserRole;
import it.unisa.is.secondlifetech.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/")
public class GeneralController {
	private final UserService userService;

	@Autowired
	public GeneralController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String index() {
		User cliente = new User("Mario", "Rossi", "email@email.com", "password", null, UserRole.CLIENTE, null);
		User gestore = new User("Antonio", "Arancioni", "emailAziendale@email.com", "password", null, UserRole.GESTORE_PRODOTTI, "");

		User savedCliente = userService.createNewUser(cliente);
		User savedGestore = userService.createNewUser(gestore);

		log.info("Cliente: " + savedCliente.getCart());
		log.info("Gestore: " + savedGestore.getCart());

		return "my-custom-index";
	}

	// TODO: aggiungere la pagina di errore
}
