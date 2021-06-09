package m2.miage.miagesousleau;

import m2.miage.miagesousleau.exception.ErrorHandler;
import m2.miage.miagesousleau.services.MSLServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MiageSousLEauApplication {
    // URL micro service gestion Cours
    public static final String GESTION_COURS_URL = "http://localhost:9293/gestionCours/";

    // URL micro service gestion Membre
    public static final String GESTION_MEMBRE_URL = "http://localhost:8182/gestionMembres/";

    // URL micro service gestion Piscine
    public static final String GESTION_PISCINE_URL = "https://data.toulouse-metropole.fr/api/records/1.0/search/?dataset=piscines&";

    public static void main(String[] args) {
        SpringApplication.run(MiageSousLEauApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ErrorHandler());
        return restTemplate;
    }

    @Bean
    public MSLServiceImpl mslService() {
        return new MSLServiceImpl(GESTION_COURS_URL, GESTION_MEMBRE_URL, GESTION_PISCINE_URL);
    }
}
