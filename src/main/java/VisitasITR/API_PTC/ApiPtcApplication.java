package VisitasITR.API_PTC;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ApiPtcApplication {

	// El servidor de Heroku trabaja en UTC, seis horas adelante de aqui. Sin
	// esto un aviso enviado a las 5:00 quedaba guardado como las 11:00, y una
	// cita para hoy mas tarde se rechazaba diciendo que la fecha ya paso.
	static {
		TimeZone.setDefault(TimeZone.getTimeZone("America/El_Salvador"));
	}

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