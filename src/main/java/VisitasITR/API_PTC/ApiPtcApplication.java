package VisitasITR.API_PTC;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiPtcApplication {
	public static void main(String[] args) {
		// Las variables pueden llegar por dos caminos: el archivo .env cuando
		// se trabaja en local, o las variables del servidor cuando ya esta
		// desplegada. Si no hay .env, arranca igual sin fallar.
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

		dotenv.entries().forEach(entry -> {
			// El .env solo llena lo que el servidor no haya definido. Asi en
			// produccion mandan las variables del servidor y el .env que va
			// en el repositorio nunca las pisa.
			boolean yaDefinida = System.getProperty(entry.getKey()) != null
					|| System.getenv(entry.getKey()) != null;

			if (!yaDefinida) {
				System.setProperty(entry.getKey(), entry.getValue());
			}
		});

		SpringApplication.run(ApiPtcApplication.class, args);
	}
}