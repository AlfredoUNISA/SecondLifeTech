package it.unisa.is.secondlifetech;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartupRunner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments arg0) {
		log.info("http://localhost:8080 <- Application started");
	}
}