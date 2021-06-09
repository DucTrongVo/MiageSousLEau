package m2.miage.miagesousleau.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import m2.miage.miagesousleau.entities.dto.Membre;
import m2.miage.miagesousleau.exception.ForbiddenException;
import m2.miage.miagesousleau.exception.GeneralErreurException;
import m2.miage.miagesousleau.exception.NotFoundException;
import m2.miage.miagesousleau.services.IMSLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
            return  ResponseEntity.ok(Objects.requireNonNull(mslService.getMembreByMail(email).getBody()));
        } catch (HttpStatusCodeException ex){
            return null;
        }
    }
    @GetMapping(value = "/allMembre")
    private ResponseEntity<List<Membre>> getMembres(@RequestParam("emailRequester") String emailRequester) throws ForbiddenException, NotFoundException {
        try{
            return  ResponseEntity.ok(mslService.getAllMembre(emailRequester));
        } catch (HttpStatusCodeException e) {
            return null;
        }
    }
}
