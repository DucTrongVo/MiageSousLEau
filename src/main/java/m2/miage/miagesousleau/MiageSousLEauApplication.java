package m2.miage.miagesousleau;

import m2.miage.miagesousleau.exception.ErrorHandler;
import m2.miage.miagesousleau.services.MSLServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class MiageSousLEauApplication {
    // URL micro service gestion Cours
    //public static final String GESTION_COURS_URL = "http://localhost:9293/gestionCours/";
    public static final String GESTION_COURS_URL = "http://SPRING-MIAGE-GESTION-COURS/gestionCours/";

    // URL micro service gestion Membre
    //public static final String GESTION_MEMBRE_URL = "http://localhost:8182/gestionMembres/";
    public static final String GESTION_MEMBRE_URL = "http://SPRING-MIAGE-GESTION-MEMBRES/gestionMembres/";

    // URL micro service gestion Piscine
    //public static final String GESTION_PISCINE_URL = "http://localhost:3001/gestionPicines/gestionPicines/";
    public static final String GESTION_PISCINE_URL = "http://SPRING-GESTION-PISCINE/gestionPicines/";

    public static void main(String[] args) {
        SpringApplication.run(MiageSousLEauApplication.class, args);
    }

    @Bean
    @LoadBalanced
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
