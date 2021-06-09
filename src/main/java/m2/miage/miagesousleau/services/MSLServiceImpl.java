package m2.miage.miagesousleau.services;

import m2.miage.miagesousleau.entities.dto.Membre;
import m2.miage.miagesousleau.entities.dto.Piscine;
import m2.miage.miagesousleau.enums.EnumTypeUtilisateur;
import m2.miage.miagesousleau.exception.ForbiddenException;
import m2.miage.miagesousleau.exception.GeneralErreurException;
import m2.miage.miagesousleau.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MSLServiceImpl implements IMSLService{
    private static final Logger logger = LoggerFactory.getLogger(MSLServiceImpl.class);

    @Autowired
    protected RestTemplate restTemplateCours;

    @Autowired
    protected RestTemplate restTemplateMembre;

    @Autowired
    protected RestTemplate restTemplatePiscine;

    @Autowired
    private IToolService toolService;

    protected String serviceUrlCours;
    protected String serviceUrlMembres;
    protected String serviceUrlPiscines;

    public MSLServiceImpl(String serviceUrlCours, String serviceUrlMembres, String serviceUrlPiscines){
        this.serviceUrlCours = serviceUrlCours;
        this.serviceUrlMembres = serviceUrlMembres;
        this.serviceUrlPiscines = serviceUrlPiscines;
    }

    @Override
    public ResponseEntity<Membre> getMembreByMail(String email) {
        //Membre membre = restTemplateMembre.getForObject(this.serviceUrlMembres+"membres/{email}",Membre.class, email);
        ResponseEntity<Membre> membre = restTemplateMembre.getForEntity(this.serviceUrlMembres+"membres/{email}",Membre.class, email);
        logger.info("Membre d'email {} trouvé est {}",email, membre);
        return membre;
    }

    @Override
    public List<Membre> getAllMembre(String emailRequester) {
       Membre[] membres = restTemplateMembre.getForObject(this.serviceUrlMembres+"membres/allMembre?emailRequester={emailRequester}", Membre[].class, emailRequester);
       if (membres == null){
           return new ArrayList<>();
       }
       return new ArrayList<>(Arrays.asList(membres));
    }

    @Override
    public Piscine getPiscineByRecordID(String recordId) throws GeneralErreurException {
        String piscineJson = restTemplatePiscine.getForObject(this.serviceUrlPiscines+"q=&refine.recordid="+recordId, String.class);
        Piscine piscine = toolService.convertJsonToPiscine(piscineJson);
        logger.info("Piscine d'id {} trouvé est {}",recordId, piscine);
        return piscine;
    }

    @Override
    public List<Piscine> getAllPiscines() throws GeneralErreurException{
        String piscinesJson = restTemplatePiscine.getForObject(this.serviceUrlPiscines+"q=", String.class);
        return toolService.convertJsonToPiscines(piscinesJson);
    }

}
