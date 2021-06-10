package m2.miage.miagesousleau.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import m2.miage.miagesousleau.entities.dto.Apte;
import m2.miage.miagesousleau.entities.dto.Membre;
import m2.miage.miagesousleau.entities.dto.Operation;
import m2.miage.miagesousleau.exception.ForbiddenException;
import m2.miage.miagesousleau.exception.GeneralErreurException;
import m2.miage.miagesousleau.exception.NotFoundException;
import m2.miage.miagesousleau.services.IMSLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/membre")
public class MembreController {

    private static final Logger logger = LoggerFactory.getLogger(MembreController.class);

    @Autowired
    IMSLService mslService;

    @GetMapping(value = "/")
    private ResponseEntity<?> getMembreByEmail(@RequestParam String email) throws NotFoundException {
        try{
            logger.info("Get membre by email={}", email);
            return  ResponseEntity.ok(Objects.requireNonNull(mslService.getMembreByMail(email).getBody()));
        } catch (HttpStatusCodeException ex){
            return null;
        }
    }
    @GetMapping(value = "/allMembre")
    private ResponseEntity<List<Membre>> getAllMembres(@RequestParam("emailRequester") String emailRequester) throws ForbiddenException, NotFoundException {
        try{
            logger.info("Get all membre ");
            return  ResponseEntity.ok(mslService.getAllMembre(emailRequester));
        } catch (HttpStatusCodeException e) {
            return null;
        }
    }

    @PostMapping(value = "/")
    private ResponseEntity<Membre> creerMembre(@RequestBody Membre membre){
        try{
            logger.info("Creer membre ");
            return new ResponseEntity<>(mslService.creerMembre(membre), HttpStatus.CREATED);
        } catch (HttpStatusCodeException e){
            return null;
        }
    }

    @PostMapping(value = "/maj/{email}")
    private ResponseEntity<Membre> mettreAJourMembre(@RequestParam("emailRequester") String emailRequester,
                                                     @PathVariable("email")  String email, @RequestBody Membre membre) {
        try{
            logger.info("MAJ membre email={}",email);
            return new ResponseEntity<>(mslService.mettreAJourMembre(emailRequester, email, membre), HttpStatus.CREATED);
        } catch (HttpStatusCodeException e){
            return null;
        }
    }

    @PostMapping(value = "/creerPaiement")
    private ResponseEntity<Operation> creerPaiement(@RequestParam("emailMembre")String emailMembre, @RequestParam("iban") String iban, @RequestParam("montant") String montant){
        try{
            logger.info("Creer paiement pour membre email={}",emailMembre);
            return new ResponseEntity<>(mslService.creerPaiement(emailMembre, iban, Double.parseDouble(montant)), HttpStatus.CREATED);
        } catch (HttpStatusCodeException e){
            return null;
        }
    }

    @GetMapping(value = "/isApte/{email}")
    private ResponseEntity<Apte> isMembreApte(@PathVariable("email") String email) {
        try{
            logger.info("Get membre apte email={}",email);
            return new ResponseEntity<>(mslService.isMembreApte(email), HttpStatus.OK);
        } catch (HttpStatusCodeException e){
            return null;
        }
    }

    @GetMapping(value = "/getAllOperation/{emailMembre}")
    ResponseEntity<List<Operation>> getAllOperationByMembre(@RequestParam("emailSec") String emailSec, @PathVariable("emailMembre") String emailMembre){
        try{
            logger.info("Get opération by membre  email={}",emailMembre);
            return ResponseEntity.ok(mslService.getAllOperationByMembre(emailSec, emailMembre));
        } catch (HttpStatusCodeException e){
            return null;
        }
    }
}
