package m2.miage.miagesousleau.controllers;

import m2.miage.miagesousleau.entities.dto.Cours;
import m2.miage.miagesousleau.entities.dto.CoursWithPiscine;
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

@Controller
@RequestMapping("/cours")
public class CoursController {
    private static final Logger logger = LoggerFactory.getLogger(CoursController.class);

    @Autowired
    IMSLService mslService;

    @GetMapping(value = "/{idCours}")
    ResponseEntity<CoursWithPiscine> getCoursById(@PathVariable("idCours") int idCours) throws GeneralErreurException, NotFoundException {
        try {
            return ResponseEntity.ok(mslService.getCoursWithPiscineById(idCours));
        } catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        } catch (GeneralErreurException e) {
            throw new GeneralErreurException();
        } catch (HttpStatusCodeException ex){
            return null;
        }
    }

    @GetMapping(value = "/allCours")
    ResponseEntity<List<CoursWithPiscine>> getAllCours() throws GeneralErreurException, NotFoundException {
        try {
            return ResponseEntity.ok(mslService.getAllCours());
        } catch (GeneralErreurException e) {
            throw new GeneralErreurException();
        }  catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (HttpStatusCodeException ex){
            return null;
        }
    }

    @PostMapping(value = "/creerCours")
    ResponseEntity<Cours> creerCours(@RequestBody Cours cours, @RequestParam String emailEnseignant) throws ForbiddenException, NotFoundException {
        try {
            return new ResponseEntity<>(mslService.creerCours(cours,emailEnseignant), HttpStatus.CREATED);
        } catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (ForbiddenException e){
            throw new ForbiddenException(e.getMessage());
        }
        catch (HttpStatusCodeException ex){
            return null;
        }
    }

    @PostMapping(value = "/inscrit/{idCours}")
    ResponseEntity<Cours> creerCours(@PathVariable("idCours") int idCours, @RequestParam String emailEtudiant) throws ForbiddenException, NotFoundException {
        try {
            return new ResponseEntity<>(mslService.inscrit(idCours, emailEtudiant), HttpStatus.OK);
        } catch (ForbiddenException e){
            throw new ForbiddenException(e.getMessage());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (HttpStatusCodeException ex){
            return null;
        }
    }
}
