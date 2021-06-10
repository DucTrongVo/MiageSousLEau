package m2.miage.miagesousleau.controllers;

import m2.miage.miagesousleau.entities.dto.Operation;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@Controller
@RequestMapping("/paiement")
public class OperationController {
    private static final Logger logger = LoggerFactory.getLogger(OperationController.class);

    @Autowired
    IMSLService mslService;

    @GetMapping(value = "/getAll")
    private ResponseEntity<List<Operation>> getAllOperation(@RequestParam("emailSec") String emailSec){
        try{
            return new ResponseEntity<>(mslService.getAllOperation(emailSec), HttpStatus.OK);
        } catch (HttpStatusCodeException e){
            return null;
        }
    }

    @PostMapping(value = "/validerPaiement/{idPaiement}")
    private ResponseEntity<Operation> validerPaiement(@RequestParam("emailSec") String emailSec, @PathVariable("idPaiement") String idOperation){
        try{
            return new ResponseEntity<>(mslService.validerPaiement(emailSec, Integer.parseInt(idOperation)), HttpStatus.OK);
        } catch (HttpStatusCodeException e){
            return null;
        }
    }

    @PostMapping(value = "/refuserPaiement/{idPaiement}")
    private ResponseEntity<Operation> refuserPaiement (@RequestParam("emailSec") String emailSec, @PathVariable("idPaiement") String idOperation){
        try{
            return new ResponseEntity<>(mslService.refuserPaiement(emailSec, Integer.parseInt(idOperation)), HttpStatus.OK);
        } catch (HttpStatusCodeException e){
            return null;
        }
    }
}
