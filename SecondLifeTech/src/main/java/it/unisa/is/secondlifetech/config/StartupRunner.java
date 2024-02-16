package it.unisa.is.secondlifetech.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Classe che viene eseguita all'avvio dell'applicazione.
 */
@Slf4j
@Component
public class StartupRunner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments arg0) {
		log.info("http://localhost:8080 <- Application started");
	}
}