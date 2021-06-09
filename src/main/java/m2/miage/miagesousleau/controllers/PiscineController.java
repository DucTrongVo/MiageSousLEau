package m2.miage.miagesousleau.controllers;

import m2.miage.miagesousleau.entities.dto.Piscine;
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

@Controller
@RequestMapping("/piscine")
public class PiscineController {

    private static final Logger logger = LoggerFactory.getLogger(MembreController.class);

    @Autowired
    IMSLService mslService;

    @GetMapping(value = "/")
    private ResponseEntity<Piscine> getPiscineById(@RequestParam String recordID) throws GeneralErreurException, NotFoundException {
        try{
            return  ResponseEntity.ok(mslService.getPiscineByRecordID(recordID));
        } catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        } catch (HttpStatusCodeException e){
            return null;
        }
    }

    @GetMapping(value = "/all")
    private ResponseEntity<List<Piscine>> getPiscines() throws GeneralErreurException {
        try{
            return  ResponseEntity.ok(mslService.getAllPiscines());
        } catch (HttpStatusCodeException e){
            return null;
        }
    }
}
