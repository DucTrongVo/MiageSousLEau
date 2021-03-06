package m2.miage.miagesousleau.services;

import m2.miage.miagesousleau.entities.dto.Apte;
import m2.miage.miagesousleau.entities.dto.Cours;
import m2.miage.miagesousleau.entities.dto.CoursWithPiscine;
import m2.miage.miagesousleau.entities.dto.Membre;
import m2.miage.miagesousleau.entities.dto.Operation;
import m2.miage.miagesousleau.entities.dto.Piscine;
import m2.miage.miagesousleau.entities.dto.Statistique;
import m2.miage.miagesousleau.entities.dto.StatistiqueCours;
import m2.miage.miagesousleau.entities.dto.StatistiqueMembre;
import m2.miage.miagesousleau.enums.Constants;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        logger.info("Membre d'email {} trouv?? est {}",email, membre);
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
    public Membre creerMembre(Membre membre) {

        return restTemplateMembre.postForObject(this.serviceUrlMembres+"membres/", membre, Membre.class);
    }

    @Override
    public Membre mettreAJourMembre(String emailRequester, String email, Membre membreAJour) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("emailRequester", emailRequester);
        return restTemplateMembre.postForObject(this.serviceUrlMembres+"membres/maj/{email}?emailRequester={emailRequester}", membreAJour, Membre.class, params);
    }

    @Override
    public Operation creerPaiement(String emailMembre, String iban, double montant) {
        Map<String, String> params = new HashMap<>();
        params.put("emailMembre",emailMembre);
        params.put("iban",iban);
        params.put("montant",String.valueOf(montant));
        return restTemplateMembre.postForObject(this.serviceUrlMembres+"membres/creerPaiement?emailMembre={emailMembre}&iban={iban}&montant={montant}",
            null, Operation.class, params);
    }

    @Override
    public Apte isMembreApte(String email) {
        return restTemplateMembre.getForObject(this.serviceUrlMembres+"membres/isApte/{email}", Apte.class, email);
    }

    @Override
    public List<Operation> getAllOperationByMembre(String emailSec, String emailMembre) {
        Map<String, String> params = new HashMap<>();
        params.put("emailSec",emailSec);
        params.put("emailMembre",emailMembre);
        Operation[] operations = restTemplateMembre.getForObject(this.serviceUrlMembres+"membres/getOperations/{emailMembre}?emailSec={emailSec}", Operation[].class, params);
        if (operations == null){
            logger.info("Aucune op??ration trouv??!");
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(operations));
    }

    @Override
    public String supprimerUnMembre(String emailRequester, String email) {
        Map<String, String> params = new HashMap<>();
        params.put("email",email);
        params.put("emailRequester",emailRequester);
        return restTemplateMembre.postForObject(this.serviceUrlMembres+"membres/delete/{email}?emailRequester={emailRequester}", null, String.class, params);
    }

    @Override
    public Piscine getPiscineByRecordID(String recordId) throws GeneralErreurException {
        //String piscineJson = restTemplatePiscine.getForObject(this.serviceUrlPiscines+"q=&refine.recordid="+recordId, String.class);
        Piscine piscine = restTemplatePiscine.getForObject(this.serviceUrlPiscines+"piscine/?recordID="+recordId, Piscine.class);
        //Piscine piscine = toolService.convertJsonToPiscine(piscineJson);
        logger.info("Piscine d'id {} trouv?? est {}",recordId, piscine);
        return piscine;
    }

    @Override
    public List<Piscine> getAllPiscines() throws GeneralErreurException{
        //String piscinesJson = restTemplatePiscine.getForObject(this.serviceUrlPiscines+"q=", String.class);
        Piscine[] piscines = restTemplatePiscine.getForObject(this.serviceUrlPiscines+"piscine/all", Piscine[].class);
        return new ArrayList<>(Arrays.asList(piscines));
        //return toolService.convertJsonToPiscines(piscinesJson);
    }

    @Override
    public List<Operation> getAllOperation(String emailSec) {
        Operation[] operations = restTemplateMembre.getForObject(this.serviceUrlMembres+"paiement/getAll?emailSec={emailSec}", Operation[].class, emailSec);
        if (operations == null){
            logger.info("Aucune op??ration trouv??!");
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(operations));
    }

    @Override
    public Operation validerPaiement(String emailSec, Integer idOperation) {
        Map<String, String> params = new HashMap<>();
        params.put("emailSec", emailSec);
        params.put("idPaiement", String.valueOf(idOperation));
        return restTemplateMembre.postForObject(this.serviceUrlMembres+"paiement/validerPaiement/{idPaiement}?emailSec={emailSec}", null, Operation.class, params);
    }

    @Override
    public Operation refuserPaiement(String emailSec, Integer idOperation) {
        Map<String, String> params = new HashMap<>();
        params.put("emailSec", emailSec);
        params.put("idPaiement", String.valueOf(idOperation));
        return restTemplateMembre.postForObject(this.serviceUrlMembres+"paiement/refuserPaiement/{idPaiement}?emailSec={emailSec}", null, Operation.class, params);
    }

    @Override
    public CoursWithPiscine getCoursWithPiscineById(int idCours) throws GeneralErreurException, NotFoundException {
        Cours cours = restTemplateCours.getForObject(this.serviceUrlCours+"cours/{idCours}", Cours.class, idCours);
        if (cours == null) {
            logger.error("Cours d'id "+idCours+" introuvable!");
            throw new NotFoundException("Cours d'id "+idCours+" introuvable!");
        }
        CoursWithPiscine coursWithPiscine = new CoursWithPiscine(cours);
        Piscine piscine = getPiscineByRecordID(cours.getIdLieu());
        coursWithPiscine.setPiscine(piscine);
        List<Membre> membres = getAllMembre(Constants.EMAIL_SECRETAIRE);
        membres.forEach(membre -> {
            if (cours.getIdEtudiants().contains(membre.getId())){
                coursWithPiscine.getListEtudiants().add(membre);
            }
        });
        return coursWithPiscine;
    }

    @Override
    public List<CoursWithPiscine> getAllCours() throws GeneralErreurException, NotFoundException {
        List<CoursWithPiscine> coursWithPiscines = new ArrayList<>();
        Cours[] cours = restTemplateCours.getForObject(this.serviceUrlCours+"cours/allCours", Cours[].class);
        if (cours == null) {
            logger.error("Aucun cours trouv??!");
            throw new NotFoundException("Aucun cours trouv??!");
        }
        for (Cours c : cours){
            coursWithPiscines.add(getCoursWithPiscineById(c.getId()));
        }
        return coursWithPiscines;
    }

    @Override
    public Cours creerCours(Cours cours, String emailEnseignant) throws ForbiddenException, NotFoundException {
        Apte apte = isMembreApte(emailEnseignant);
        Membre enseignant = getMembreByMail(emailEnseignant).getBody();
        if (enseignant == null) {
            logger.error("Enseignant d'email "+emailEnseignant+" introuvable!");
            throw new NotFoundException("Enseignant d'email "+emailEnseignant+" introuvable!");
        }
        if (enseignant.getNiveau() == null){
            logger.error("Ninveau d'enseignant est null!");
            throw new ForbiddenException("Ninveau d'enseignant est null!");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("isEnseignant", EnumTypeUtilisateur.ENSEIGNANT.name().equals(enseignant.getType()));
        params.put("enseignantApte", apte.isApte());
        params.put("niveauExpEnseignant", enseignant.getNiveau());
        return restTemplateCours.postForObject(this.serviceUrlCours+"cours/creerCours?isEnseignant={isEnseignant}&enseignantApte={enseignantApte}" +
            "&niveauExpEnseignant={niveauExpEnseignant}", cours, Cours.class, params);
    }

    @Override
    public Cours inscrit(int idCours, String emailEtudiant) throws ForbiddenException, NotFoundException {
        Apte apte = isMembreApte(emailEtudiant);
        if (!apte.isApte()){
            logger.error("Etudiant d'email "+emailEtudiant+" n'est pas APTE");
            throw new ForbiddenException("Etudiant d'email "+emailEtudiant+" n'est pas APTE");
        }
        Membre etudiant = getMembreByMail(emailEtudiant).getBody();
        if (etudiant == null) {
            logger.error("Etudiant d'email "+emailEtudiant+" introuvable!");
            throw new NotFoundException("Etudiant d'email "+emailEtudiant+" introuvable!");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("idCours", idCours);
        params.put("idEtudiant", etudiant.getId());
        params.put("niveauEtudiant", etudiant.getNiveau());
        params.put("isEtudiant", EnumTypeUtilisateur.MEMBRE.name().equals(etudiant.getType()));
        return restTemplateCours.postForObject(this.serviceUrlCours+"cours/inscrit/{idCours}?idEtudiant={idEtudiant}&niveauEtudiant={niveauEtudiant}" +
            "&isEtudiant={isEtudiant}", null, Cours.class, params);
    }

    @Override
    public Cours desinscrit(int idCours, String emailEtudiant) throws ForbiddenException, NotFoundException {
        Membre etudiant = getMembreByMail(emailEtudiant).getBody();
        if (etudiant == null) {
            logger.error("Etudiant d'email "+emailEtudiant+" introuvable!");
            throw new NotFoundException("Etudiant d'email "+emailEtudiant+" introuvable!");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("idCours", idCours);
        params.put("idEtudiant", etudiant.getId());
        return restTemplateCours.postForObject(this.serviceUrlCours+"cours/desinscrit/{idCours}?idEtudiant={idEtudiant}", null, Cours.class, params);
    }

    @Override
    public Statistique getStat(String emailPresident) {
        StatistiqueMembre statMembre = restTemplateMembre.getForObject(this.serviceUrlMembres+"membres/stat/{emailPresident}", StatistiqueMembre.class, emailPresident);
        logger.info("stat membre {}",statMembre);
        StatistiqueCours statCours = restTemplateCours.getForObject(this.serviceUrlCours+"cours/stat", StatistiqueCours.class);
        logger.info("stat cours {}",statCours);
        Statistique statistique = new Statistique();
        statistique.setNombreCours(statCours.getNombreCours());
        statistique.setNombreEnseignants(statMembre.getNombreEnseignants());
        statistique.setNombreMembres(statMembre.getNombreMembres());
        statistique.setTotalCotisationPrevues(statMembre.getTotalCotisationPrevues());
        statistique.setTotalCotisationReglees(statMembre.getTotalCotisationReglees());
        return statistique;
    }

}
